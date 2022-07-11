package simulator;

import simulator.control.Simulator;
import simulator.network.Link;
import simulator.wrapper.Wrapper;
import simulator.wrapper.wrappers.Adder;

/*
 * pc
 *  in:
 *      0 : clock
 *      1 -> 33 : data
 * 
 * out:
 *   0 -> 31 : register output (address)
 */

public class PC extends Wrapper {

    public PC(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        Register reg = new Register("pc reg", "34x32", getInput(0), Simulator.trueLogic);
        // Adder adder = new Adder("adder", "64x33");
        // for(int i = 0; i< 32; i++){
        //     adder.addInput(reg.getOutput(i));
        // }

        // for(int i = 0; i < 29; i++){
        //     adder.addInput(Simulator.falseLogic);
        // }

        // adder.addInput(Simulator.trueLogic);
        // adder.addInput(Simulator.falseLogic);
        // adder.addInput(Simulator.falseLogic);

        // for(int i = 1; i < 33; i++){
        //     reg.addInput(adder.getOutput(i));
        // }
        // for(int i = 0; i < 32; i++){
        //     addOutput(reg.getOutput(i));
        // }
    }
    
}
