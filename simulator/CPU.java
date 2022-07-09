package simulator;

import javax.xml.crypto.dsig.SignedInfo;

import simulator.control.Simulator;
import simulator.gates.combinational.ByteMemory;
import simulator.gates.sequential.BigClock;
import simulator.wrapper.wrappers.Multiplexer;

public class CPU {
    private BigClock clock;
    private PC pc;
    private InstructionMemory instructionMemory;
    private RegFile regFile;
    private ALU alu;
    private Control control;
    private AluControl aluControl;
    private SignExtend signExtend;
    private DataMemory dataMemory;
    private ByteMemory memoryInst;
    private ByteMemory memoryData;

    public CPU() {
        // Clock:
        clock = new BigClock("clock");
        
        // Control Unit:
        // CHECK: control's `stream`
        control = new Control("control", "6x10");

        // PC:
        pc = new PC("pc", "1x32", clock.getOutput(0));


        // Instruction Memory:
        
        // never writes
        ByteMemory memoryInst = new ByteMemory("memoryInst", Simulator.falseLogic);
        
        Boolean[][] mem = new Boolean[65536][8];
        for (int i = 0; i < 65536; i++) {
            for (int j = 0; j < 8; j++) {
                mem[i][j] = false;
            }
        }

        // write code to memory here
        memoryInst.setMemory(mem);

        InstructionMemory instructionMemory = new InstructionMemory("ins_mem", "32x32", memoryInst);

        for (int i = 0; i < 32; i++) {
            instructionMemory.addInput(pc.getOutput(i));
        }


        // Register File:

        // control.getOutput(8) == regWrite
        regFile = new RegFile("regFile", "49x64", control.getOutput(0), control.getOutput(8));
        for(int i = 0; i< 47; i++){
            regFile.addInput(Simulator.falseLogic);
        }

        // CHECK: Left-to-Right vs. Right-to-Left direction for instructionMemory
        // CHECK: regFile.setInput vs. regFile.addInput

        // Read Register 1
        for (int i = 21; i <= 25; i++) {
            regFile.addInput(instructionMemory.getOutput(i));
        }

        // Read Register 2
        for (int i = 16; i <= 20; i++) {
            regFile.addInput(instructionMemory.getOutput(i));
        }

        Multiplexer[] writeRegMux = new Multiplexer[5];
        for (int i = 0; i < 5; i++) {
            writeRegMux[i] = new Multiplexer("writeRegMux"+i, "3x1",
                            control.getOutput(0),
                            instructionMemory.getOutput(i + 16),
                            instructionMemory.getOutput(i + 11));

            regFile.addInput(writeRegMux[i].getOutput(0));
        }


        // ALU Control:
       // aluControl = new AluControl("aluControl");

        // aluControl: AluOp0, AluOp1
        // CHECK: Order of AluOps
        aluControl.addInput(control.getOutput(4), control.getOutput(5));
        
        // aluControl: function
        for (int i = 26; i <= 31; i++) {
            aluControl.addInput(instructionMemory.getOutput(i));
        }

        // aluControl: Imm
        aluControl.addInput(instructionMemory.getOutput(9));

        // aluControl: last 3 bits of opcode
        aluControl.addInput(instructionMemory.getOutput(3),
                            instructionMemory.getOutput(4),
                            instructionMemory.getOutput(5));

                            
        // Sign Extend:
        //signExtend = new SignExtend("signExtend", stream, links)

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
                            regFile.getOutput(i + 32), signExtend.getOutput(i));
        }

    }
}