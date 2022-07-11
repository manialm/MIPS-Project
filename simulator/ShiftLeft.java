package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.Or;
import simulator.network.Link;
import simulator.wrapper.Wrapper;
import simulator.wrapper.wrappers.Decoder;
import simulator.wrapper.wrappers.Multiplexer;

/* shift left logical
 * in:
 *      0 -> 4 : shift amount
 *      5 -> 36 : input
 * 
 * out:
 *      0 -> 31 : shifted input
 */

public class ShiftLeft extends Wrapper {

    public ShiftLeft(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        Decoder decoder = new Decoder("decoder", "5x32");

        for (int i = 0; i < 5; i++) {
            decoder.addInput(getInput(i));
        }

        // modified barrel shifter
        Or[] or = new Or[32];
        for (int i = 0; i < 32; i++) {
            or[i] = new Or("or"+i);

            Multiplexer[] mux = new Multiplexer[32];
            for (int j = 0; j < 32; j++) {
                int sel = i - j;
                if (sel < 0) sel += 32;
                sel = 32 - sel;
                if (sel == 32) sel = 0;

                mux[j] = new Multiplexer("mux"+j, "3x1",
                decoder.getOutput(sel),
                Simulator.falseLogic,
                getInput(5 + j));
            }


            for (int j = 0; j < 32; j++) {
                if (i <= j)
                    or[i].addInput(mux[j].getOutput(0));
            }
        }

        for (int i = 0; i < 32; i++) {
            addOutput(or[i].getOutput(0));
        }
        
    }
}
