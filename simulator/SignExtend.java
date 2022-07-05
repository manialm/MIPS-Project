package simulator;

import simulator.network.Link;
import simulator.wrapper.Wrapper;


public class SignExtend extends Wrapper {

    public SignExtend(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        for(int i = 0; i < 16; i++){
            addOutput(getInput(0));
        }
        for(int i = 0 ; i < 16; i++){
            addOutput(getInput(i));
        }
    }
    
}
