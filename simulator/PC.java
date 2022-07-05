package simulator;

import simulator.network.Link;
import simulator.wrapper.Wrapper;

public class PC extends Wrapper {

    public PC(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        
    }
    
}
