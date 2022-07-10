package simulator;
import java.lang.reflect.Member;

import simulator.gates.combinational.ByteMemory;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

/* instruction memory
 * (constructor takes a ByteMemory object)
 *  in:
 *      0 : MemWrite
 *      1 : MemRead
 *      2 -> 33 : read address
 *      34 -> 65 : write data
 * 
 *  out:
 *      0 -> 31 : data
 */

public class DataMemory extends Wrapper {
    
    private ByteMemory memory;
    
    public DataMemory(String label, String stream, ByteMemory memory, Link... links) {
        super(label, stream, links);
        this.memory = memory;
        initialize();
    }

    @Override
    public void initialize() {

        if (memory == null) {
            return;
        }

        // write signal
        memory.addInput(getInput(0));

        for(int i = 18; i< 34; i++){
            memory.addInput(getInput(i));
        }

        for(int i = 34; i < 66 ; i++){
            memory.addInput(getInput(i));
        }

        for(int i =0 ; i< 32; i++){
            addOutput(memory.getOutput(i));
        }

    }
    
}