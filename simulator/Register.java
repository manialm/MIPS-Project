package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.And;
import simulator.gates.combinational.Not;
import simulator.gates.combinational.Or;
import simulator.gates.sequential.BigClock;
import simulator.network.Link;
import simulator.wrapper.Wrapper;
import simulator.wrapper.wrappers.DFlipFlop;

/*a data flip-flop
 * in:
 *   0 : clock signal 
 *   1 : load  signal*/

public class Register extends Wrapper{
    static final int size = 32;
    public Register(String label, String stream, Link... links) {
        super(label, stream, links);
    }
    @Override
    public void initialize() {
        Not not1 = new Not("not1", getInput(1));
        Not not2 = new Not("not2", not1.getOutput(0));
        DFlipFlop dffs[] = new DFlipFlop[size];
        Or ors[] = new Or[size];
        And ands[] = new And[size * 2];
        for(int i = 0; i< size; i++){
            dffs[i] = new DFlipFlop("dff" + i, "2x2", getInput(0), Simulator.falseLogic);
        }
        int i = 0;
        int j = 0;
        while(i < size * 2){
            ands[i] = new And("and" + i, dffs[j].getOutput(0), not1.getOutput(0));
            i++;
            ands[i] = new And("and" + i, not2.getOutput(0), getInput(j + 2));
            j++;
            i++;
        }
        j = 0;
        for(i = 0; i< size; i++){
            ors[i] = new Or("or" + i,ands[j].getOutput(0), ands[j + 1].getOutput(0));
            j += 2;
        }
        for(i = 0; i < size; i++){
            dffs[i].setInput(1, ors[i].getOutput(0));
        }
        for(i = 0; i< size; i++){
            addOutput(dffs[i].getOutput(0));
        }
    }

}
