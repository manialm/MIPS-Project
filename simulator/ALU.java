package simulator;

import simulator.network.Link;
import simulator.wrapper.Wrapper;
import simulator.wrapper.wrappers.Adder;

public class ALU extends Wrapper{

    public ALU(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        Adder adder = new Adder("adder", "64x33");
        for(int i = 0; i < 64; i++){
            adder.addInput(getInput(i));
        }
        
    }
    
}
