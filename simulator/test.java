package simulator;


import simulator.control.Simulator;
import simulator.gates.combinational.Not;
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
        

        Simulator.debugger.addTrackItem();
        Simulator.debugger.setDelay(0);
        Simulator.circuit.startCircuit(3);



    }
}
