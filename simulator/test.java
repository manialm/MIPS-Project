package simulator;


import simulator.control.Simulator;
import simulator.gates.combinational.ByteMemory;
import simulator.gates.combinational.Not;
import simulator.gates.combinational.Or;
import simulator.gates.sequential.BigClock;
import simulator.gates.sequential.Clock;
import simulator.wrapper.wrappers.Adder;
import simulator.wrapper.wrappers.DFlipFlop;
import simulator.wrapper.wrappers.Decoder;

public class test {
    public static void main(String[] args){
        // BigClock clock = new BigClock("clk");
        // DFlipFlop[] counter = new DFlipFlop[3];

        // for(int i = 0; i < 3; i++){
        //     counter[i] = new DFlipFlop("D" + i, "2x2", clock.getOutput(0), Simulator.falseLogic);
        // }
        // Adder adder = new Adder("adder", "6x4");
        // for(int i = 0; i < 3; i++){
        //     adder.addInput(counter[i].getOutput(0));
        // }
        
        
        // adder.addInput(Simulator.falseLogic);
        // adder.addInput(Simulator.falseLogic);
        // adder.addInput(Simulator.trueLogic);

        // for(int i = 0; i< 3; i++){
        //     counter[i].setInput(1, adder.getOutput(i+1));
        // }




        // BigClock clk = new BigClock("clk");
        // Register reg = new Register("reg","34x32", clk.getOutput(0), Simulator.trueLogic);
        // for(int i = 2; i < 34; i++){
        //     reg.addInput(Simulator.falseLogic);
        // }


        // BigClock clock = new BigClock("clk");
        // RegFile regfile = new RegFile("regfile", "49x64",clock.getOutput(0), Simulator.trueLogic);
        // for(int i = 0; i< 47; i++){
        //     regfile.addInput(Simulator.falseLogic);
        // }


        // BigClock clock = new BigClock("clk");
        // RegFile regfile = new RegFile("regfile", "49x64", clock.getOutput(0), Simulator.trueLogic);
        // for(int i= 0; i< 5; i++){
        //     regfile.addInput(Simulator.falseLogic);
        // }
        // for(int i =0; i< 4; i++){
        //     regfile.addInput(Simulator.falseLogic);
        // }
        // regfile.addInput(Simulator.trueLogic);
        // for(int i = 0; i< 37; i++){
        //      regfile.addInput(Simulator.falseLogic);
        // }

        
        // BigClock clock = new BigClock("clk");
        // PC pc = new PC("pc", "1x32", clock.getOutput(0));
        
        // SignExtend se = new SignExtend("se", "16x32");
        // se.addInput(Simulator.falseLogic);
        // for(int i= 1; i< 16; i++){
        //     se.addInput(Simulator.trueLogic);
        // }
        

        //false logic is for write memory. we don't change memory in instruction memory so it should always be false
        //InstructionMemory ins = new InstructionMemory("ins", "1x32",Simulator.falseLogic);

        
        // Subtractor sub = new Subtractor("sub", "64x32");
        // for(int i = 0; i< 29; i++){
        //     sub.addInput(Simulator.falseLogic);
        // }
        // sub.addInput(Simulator.trueLogic);
        // sub.addInput(Simulator.falseLogic);
        // sub.addInput(Simulator.trueLogic);

        // for(int i = 0; i< 30; i++){
        //     sub.addInput(Simulator.falseLogic);
        // }
        // sub.addInput(Simulator.trueLogic);
        // sub.addInput(Simulator.falseLogic);
        
        // ALU alu = new ALU("alu", "68x33");
        // //control singals
        // alu.addInput(Simulator.trueLogic);
        // alu.addInput(Simulator.falseLogic);
        // alu.addInput(Simulator.trueLogic);
        // alu.addInput(Simulator.falseLogic);
        // //a 32-bit 68
        // for(int i = 0; i < 25; i++){
        //     alu.addInput(Simulator.falseLogic);
        // }
        // alu.addInput(Simulator.trueLogic);
        // alu.addInput(Simulator.falseLogic);
        // alu.addInput(Simulator.falseLogic);
        // alu.addInput(Simulator.falseLogic);
        // alu.addInput(Simulator.trueLogic);
        // alu.addInput(Simulator.falseLogic);
        // alu.addInput(Simulator.falseLogic);
        // //a 32-bit 33
        // for(int i = 0; i< 26; i++){
        //     alu.addInput(Simulator.falseLogic);
        // }
        // alu.addInput(Simulator.trueLogic);
        // alu.addInput(Simulator.falseLogic);
        // alu.addInput(Simulator.falseLogic);
        // alu.addInput(Simulator.falseLogic);
        // alu.addInput(Simulator.falseLogic);
        // alu.addInput(Simulator.trueLogic);

        // Begin InstructionMemory
        // BigClock clock = new BigClock("clk");
        // PC pc = new PC("pc", "1x32", clock.getOutput(0));

        // // never writes
        // ByteMemory memory = new ByteMemory("memory", Simulator.falseLogic);
        
        // Boolean[][] mem = new Boolean[65536][8];
        // for (int i = 0; i < 65536; i++) {
        //     for (int j = 0; j < 8; j++) {
        //         mem[i][j] = false;
        //     }
        // }

        // mem[0][0] = true;
        // mem[0][1] = true;

        // mem[4][0] = true;
        // mem[4][2] = true;
        // memory.setMemory(mem);

        // InstructionMemory instructionMemory = new InstructionMemory("ins_mem", "32x32", memory);

        // for (int i = 0; i < 32; i++) {
        //     instructionMemory.addInput(pc.getOutput(i));
        // }
        // End InstructionMemory

        
        // Begin DataMemory
        // BigClock clock = new BigClock("clk");
        // PC pc = new PC("pc", "1x32", clock.getOutput(0));

        // // does write
        // ByteMemory memory = new ByteMemory("memory", Simulator.trueLogic);
        
        // Boolean[][] mem = new Boolean[65536][8];
        // for (int i = 0; i < 65536; i++) {
        //     for (int j = 0; j < 8; j++) {
        //         mem[i][j] = false;
        //     }
        // }

        // mem[0][0] = true;
        // mem[0][3] = true;
        // memory.setMemory(mem);

        // DataMemory dataMemory = new DataMemory("data_mem", "64x32", memory);

        // for (int i = 0; i < 32; i++) {
        //     dataMemory.addInput(Simulator.falseLogic);
        // }

        // for (int i = 0; i < 32; i += 2) {
        //     dataMemory.addInput(Simulator.trueLogic);
        //     dataMemory.addInput(Simulator.falseLogic);
        // }
        
        // clock.toggle();
        // Simulator.debugger.addTrackItem(clock, memory);
        // Simulator.debugger.setDelay(0);
        // Simulator.circuit.startCircuit(3);
        // End DataMemory

        
        // AluControl cont = new AluControl("cont", "12x4");
        // //aluop
        // cont.addInput(Simulator.trueLogic);
        // cont.addInput(Simulator.falseLogic);
        // //func
        // cont.addInput(Simulator.trueLogic);
        // for(int i = 0; i< 2; i++){
        //     cont.addInput(Simulator.falseLogic);

        // }
        // cont.addInput(Simulator.trueLogic);
        // cont.addInput(Simulator.falseLogic);
        // cont.addInput(Simulator.trueLogic);
        
        // //imm
        // cont.addInput(Simulator.falseLogic);
        // //opcode
        // cont.addInput(Simulator.trueLogic);
        // for(int i = 0; i < 2; i++){
        //     cont.addInput(Simulator.falseLogic);
        // }

        // ALU alu = new ALU("alu", "68x33");

        // for(int i =0; i < 4; i++){
        //     alu.addInput(cont.getOutput(i));
        // }

        // //2
        // for(int i = 0; i < 30; i++){
        //     alu.addInput(Simulator.falseLogic);
        // }
        // alu.addInput(Simulator.trueLogic);
        // alu.addInput(Simulator.falseLogic);
        // //3
        // for(int i = 0; i < 30; i++){
        //     alu.addInput(Simulator.falseLogic);
        // }
        // alu.addInput(Simulator.trueLogic);
        // alu.addInput(Simulator.trueLogic);



        // Control control = new Control("control", "6x10");
        // control.addInput(Simulator.falseLogic);
        // control.addInput(Simulator.falseLogic);
        // control.addInput(Simulator.falseLogic);
        // control.addInput(Simulator.trueLogic);
        // control.addInput(Simulator.falseLogic);
        // control.addInput(Simulator.falseLogic);

        // ShiftRight shift = new ShiftRight("shift", "37x32");

        // // shift left by 10
        // shift.addInput(Simulator.falseLogic);
        // shift.addInput(Simulator.trueLogic);
        // shift.addInput(Simulator.falseLogic);
        // shift.addInput(Simulator.trueLogic);
        // shift.addInput(Simulator.falseLogic);

        // for (int i = 0; i < 16; i++) {
        //     shift.addInput(Simulator.falseLogic);
        // }
        
        // for (int i = 0; i < 16; i++) {
        //     shift.addInput(Simulator.trueLogic);
        // }
        
        CPU cpu = new CPU();

        Simulator.debugger.addTrackItem(cpu.instructionMemory, cpu.regFile);
        Simulator.debugger.setDelay(0);
        Simulator.circuit.startCircuit(8);
    }
}
