package simulator;

import simulator.control.Simulator;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

/*
 * shll
 *  in:
 *      0 : 
 */

public class ShiftLL extends Wrapper {

    public ShiftLL(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        for(int i = 1; i < 32; i++){
            addOutput(getInput(i));
        }
        addOutput(Simulator.falseLogic);
    }
    
}
