package simulator;

import simulator.network.Link;
import simulator.wrapper.Wrapper;

public class InstructionMemory extends Wrapper{

    public InstructionMemory(String label, String stream, Link[] links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        
    }
    
}
