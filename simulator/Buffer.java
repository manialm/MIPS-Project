package simulator;

import simulator.network.Link;
import simulator.network.Node;

public class Buffer extends Node{

    public Buffer(String label, Link... links) {
        super(label, links);
        addOutputLink(true);
    }

    @Override
    public void evaluate() {
        getOutput(0).setSignal(getInput(0).getSignal());
    }
    
}
