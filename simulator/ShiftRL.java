package simulator;

import simulator.control.Simulator;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

public class ShiftRL extends Wrapper{

    public ShiftRL(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        addOutput(Simulator.falseLogic);
        for(int i = 0; i < 31; i++){
            addOutput(getInput(i));
        }
    }
    
}
