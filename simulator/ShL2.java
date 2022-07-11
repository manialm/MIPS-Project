package simulator;

import simulator.control.Simulator;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

public class ShL2 extends Wrapper{

    public ShL2(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        for(int i = 2; i< 32; i++){
            addOutput(getInput(i));
        }
        addInput(Simulator.falseLogic);
        addInput(Simulator.falseLogic);
    }
    
}
