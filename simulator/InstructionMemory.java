package simulator;
import simulator.gates.combinational.ByteMemory;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

/* instruction memory
 * (constructor takes a ByteMemory object)
 * in:
 *   0 -> 31 : read address (PC)
 * 
 * out: (0 -> 31 : instruction)
 * 
 *   0 -> 15 : immediate for I-type, last 6 bits are func for R-type
 *   11 -> 15 : rd
 *   16 -> 20 : rt
 *   21 -> 25 : rs
 */

public class InstructionMemory extends Wrapper {
    
    private ByteMemory memory;
    
    public InstructionMemory(String label, String stream, ByteMemory memory, Link... links) {
        super(label, stream, links);
        this.memory = memory;
        initialize();
    }

    @Override
    public void initialize() {

        if (memory == null) {
            return;
        }

        for (int i = 0; i < 16; i++) {
            // get the 16 most signifcant bits of PC for address
            memory.addInput(getInput(i + 16));
        }

        for (int i = 0; i < 32; i++) {
            addOutput(memory.getOutput(i));
        }
    }
    
}
