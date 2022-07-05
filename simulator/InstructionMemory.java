package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.Memory;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

/*
 * instruction memory
 *  in:
 *      0 : clock
 *      1 -> 16: 
 *      17 -> 48:
 *  out:
 *      1 -> 32 : data
 */

public class InstructionMemory extends Wrapper{

    public InstructionMemory(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        Memory memory = new Memory("inst mem",getInput(0));
        //***************************
        for(int i = 0; i< 16; i++){
            addInput(Simulator.falseLogic);
        }
        //*************************** 
        for(int i = 0; i< 32; i++){
            memory.addInput(Simulator.falseLogic);
        }
        for(int i = 0; i < 32; i++){
            addOutput(memory.getOutput(i));
        }
    }
    
}
