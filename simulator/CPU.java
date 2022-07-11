package simulator;


import simulator.control.Simulator;
import simulator.gates.combinational.And;
import simulator.gates.combinational.ByteMemory;
import simulator.gates.sequential.BigClock;
import simulator.wrapper.wrappers.Adder;
import simulator.wrapper.wrappers.Multiplexer;

public class CPU {
    public BigClock clock;
    public Register pc;
    public InstructionMemory instructionMemory;
    public RegFile regFile;
    public ALU alu;
    public Control control;
    public AluControl aluControl;
    public SignExtend signExtend;
    public DataMemory dataMemory;
    public ByteMemory memoryInst;
    public ByteMemory memoryData;
    public ShL2 shl2;

    public CPU() {
        // Clock:
        clock = new BigClock("clock");

        // Instruction Memory:
        // never writes
        memoryInst = new ByteMemory("memoryInst", Simulator.falseLogic);
        
        Boolean[][] mem = new Boolean[65536][8];
        for (int i = 0; i < 65536; i++) {
            for (int j = 0; j < 8; j++) {
                mem[i][j] = false;
            }
        }

        // write code to memory here ****************************************************************
        // lw $s0, 0($0)
        mem[0][0] = true;
        mem[0][4] = true;
        mem[0][5] = true;

        // lw $s1, 0($0)
        mem[4][0] = true;
        mem[4][4] = true;
        mem[4][5] = true;
        mem[5][7] = true;

        // nor $0, $0, $1
        mem[9][7] = true;
        mem[11][2] = true;
        mem[11][5] = true;
        mem[11][6] = true;
        mem[11][7] = true;
        memoryInst.setMemory(mem);

        instructionMemory = new InstructionMemory("ins_mem", "32x32", memoryInst);

        for (int i = 0; i < 32; i++) {
            instructionMemory.addInput(pc.getOutput(i));
        }

        // Control unit:
        control = new Control("control", "6x11");
        for(int i = 0 ; i < 6; i++){
            control.addInput(instructionMemory.getOutput(i));
        }

        // Register File:

        // control.getOutput(8) == regWrite
        regFile = new RegFile("regFile", "49x64", clock.getOutput(0), control.getOutput(8));

        // CHECK: regFile.setInput vs. regFile.addInput

        // Read Register 1
        for (int i = 6; i < 11; i++) {
            regFile.addInput(instructionMemory.getOutput(i));
        }

        // Read Register 2
        for (int i = 11; i < 16; i++) {
            regFile.addInput(instructionMemory.getOutput(i));
        }

        // write register
        Multiplexer[] writeRegMux = new Multiplexer[5];
        for (int i = 0; i < 5; i++) {
            writeRegMux[i] = new Multiplexer("writeRegMux"+i, "3x1",
                            control.getOutput(0),
                            instructionMemory.getOutput(i + 11),
                            instructionMemory.getOutput(i + 16));

            regFile.addInput(writeRegMux[i].getOutput(0));
        }


        //ALU Control:
        aluControl = new AluControl("aluControl", "12x4");

        // aluControl: AluOp1, AluOp0
        aluControl.addInput(control.getOutput(4), control.getOutput(5));
        
        // aluControl: function
        for (int i = 26; i < 32; i++) {
            aluControl.addInput(instructionMemory.getOutput(i));
        }

        // aluControl: Imm
        aluControl.addInput(control.getOutput(10));

        // aluControl: last 3 bits of opcode
        aluControl.addInput(instructionMemory.getOutput(3),
                            instructionMemory.getOutput(4),
                            instructionMemory.getOutput(5));

                            
        // Sign Extend:
        signExtend = new SignExtend("signExtend", "16x32");
        for(int i = 16; i < 32; i++){
            signExtend.addInput(instructionMemory.getOutput(i));
        }

        // ALU:
        alu = new ALU("alu", "68x33");

        for (int i = 0; i <= 3; i++) {
            alu.addInput(aluControl.getOutput(i));
        }

        for (int i = 0; i < 32; i++) {
            alu.addInput(regFile.getOutput(i));
        }

        Multiplexer[] ALUIn2mux = new Multiplexer[32];
        for (int i = 0; i < 32; i++) {
            ALUIn2mux[i] = new Multiplexer("ALUIn2mux"+i, "3x1",
                            control.getOutput(7),
                            regFile.getOutput(i + 32), 
                            signExtend.getOutput(i));
            alu.addInput(ALUIn2mux[i].getOutput(0));
        }

        // data memory
        memoryData = new ByteMemory("memoryData", Simulator.falseLogic);
        Boolean[][] mem2 = new Boolean[65536][8];
        for (int i = 0; i < 65536; i++) {
            for (int j = 0; j < 8; j++) {
                mem2[i][j] = false;
            }
        }

        memoryData.setMemory(mem2);

        dataMemory = new DataMemory("dataMemory", "66x32", memoryData);
        // write memory signal
        dataMemory.addInput(control.getOutput(6));
        // read memory signal
        dataMemory.addInput(control.getOutput(2));
        for(int i = 0; i< 32; i++){
            dataMemory.addInput(alu.getOutput(i));
        }
        for(int i = 32; i < 64; i++){
            dataMemory.addInput(regFile.getOutput(i));
        }

        Multiplexer muxWriteback[] = new Multiplexer[32];
        for(int i = 0; i< 32; i++){
            muxWriteback[i] = new Multiplexer("WriteBack" + i, "3x1");
            muxWriteback[i].addInput(control.getOutput(3));
            muxWriteback[i].addInput(alu.getOutput(i + 1));
            muxWriteback[i].addInput(dataMemory.getOutput(i));
        }


        // write back
        for(int i = 0; i< 32; i++){
            regFile.addInput(muxWriteback[i].getOutput(0));
        }

        // PC:
        pc = new Register("pc", "34x32", clock.getOutput(0), Simulator.trueLogic);
        Adder pc_adder = new Adder("pc_adder", "64x33");
        for(int i = 0; i< 32; i++){
            pc_adder.addInput(pc.getOutput(i));
        }

        for(int i = 0; i < 29; i++){
            pc_adder.addInput(Simulator.falseLogic);
        }
        pc_adder.addInput(Simulator.trueLogic);
        pc_adder.addInput(Simulator.falseLogic);
        pc_adder.addInput(Simulator.falseLogic);

        Adder branch_adder = new Adder("branch_adder", "64x33");
        for(int i = 1; i< 33; i++){
            branch_adder.addInput(pc_adder.getOutput(i));
        }

        shl2 = new ShL2("shiftL2", "32x32");
        for(int i = 0; i < 32; i++){
            shl2.addInput(signExtend.getOutput(i));
        }
        for(int i = 0; i < 32; i++){
            branch_adder.addInput(shl2.getOutput(i));
        }

        And branch_and = new And("and", control.getOutput(1), alu.getOutput(0));
        Multiplexer branch_mux[] = new Multiplexer[32];
        for(int i =0 ; i < 32; i++){
            branch_mux[i] = new Multiplexer("mux", "3x1");
            branch_mux[i].addInput(branch_and.getOutput(0));
            branch_mux[i].addInput(pc_adder.getOutput(i + 1));
            branch_mux[i].addInput(branch_adder.getOutput(i + 1));
        }

        Multiplexer jump_mux[] = new Multiplexer[32];
        ShL2 jump_ShL2 = new ShL2("shiftL2", "32x32");
        for(int i = 0 ; i < 6; i++){
            jump_ShL2.addInput(Simulator.falseLogic);
        }
        for(int i = 6; i < 32; i++){
            jump_ShL2.addInput(instructionMemory.getOutput(i));
        }
        for(int i = 0; i < 32; i++){
            jump_mux[i] = new Multiplexer("mux", "3x1");
            jump_mux[i].addInput(control.getOutput(9));
            jump_mux[i].addInput(branch_mux[i].getOutput(0));
            jump_mux[i].addInput(jump_ShL2.getOutput(i));
        }
        
        for(int i = 0 ;i < 32; i++){
            pc.addInput(jump_mux[i].getOutput(0));
        }

    }
}