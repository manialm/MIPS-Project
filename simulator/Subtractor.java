package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.Not;
import simulator.network.Link;
import simulator.wrapper.Wrapper;
import simulator.wrapper.wrappers.FullAdder;

public class Subtractor extends Wrapper{

    public Subtractor(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        FullAdder fadders[] = new FullAdder[32];
        Not nots[] = new Not[32];
        for(int i = 0; i < 32; i++){
            nots[i] = new Not("not");
            nots[i].addInput(getInput(i + 32));
            fadders[i] = new FullAdder("fadder", "3x2");
        }
        fadders[31].addInput(getInput(31));
        fadders[31].addInput(nots[31].getOutput(0));
        fadders[31].addInput(Simulator.trueLogic);
        for(int i= 0; i< 31; i++){
            fadders[i].addInput(getInput(i));
            fadders[i].addInput(nots[i].getOutput(0));
            fadders[i].addInput(fadders[i + 1].getOutput(0));
        }
        for(int i =0; i< 32; i++){
            addOutput(fadders[i].getOutput(1));
        }

    }
    
}
