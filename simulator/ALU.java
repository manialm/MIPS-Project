package simulator;

import simulator.gates.combinational.And;
import simulator.gates.combinational.Nor;
import simulator.gates.combinational.Not;
import simulator.gates.combinational.Or;
import simulator.network.Link;
import simulator.wrapper.Wrapper;
import simulator.wrapper.wrappers.Adder;
import simulator.wrapper.wrappers.Decoder;

/*
 * alu
 *  in:
 *      0 -> 3 : alu control
 *      4 -> 67: data
 *  out:
 *      0 : zero flag
 *      1 -> 32 : data
 */

 /*control singals:
  *     add = 10  
  *     sub = 6
  *     and = 0
  *     or = 1
  *     nor = 12
  *     srl = 15
  *     sll = 13
  */

public class ALU extends Wrapper{

    public ALU(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        Decoder decoder = new Decoder("decoder", "4x16");
        for(int i = 0; i < 4; i++){
            decoder.addInput(getInput(i));
        }
        Adder adder = new Adder("adder", "64x33");
        Subtractor sub = new Subtractor("sub", "64x32");
        ShiftRight srl = new ShiftRight("shiftRight", "37x32");
        ShiftLeft sll = new ShiftLeft("shiftLeft", "37x32");
        And and[] = new And[32];
        Or or[] = new Or[32];
        Nor nor[] = new Nor[32];
        for(int i = 4; i< 36 ; i++){
            adder.addInput(getInput(i));
            sub.addInput(getInput(i));
            and[i - 4] = new And("and", getInput(i));
            or[i - 4] = new Or("or", getInput(i));
            nor[i - 4] = new Nor("nor", getInput(i));
        }
        for(int i = 0; i <5 ;i++){
            srl.addInput(getInput(i + 31));
            sll.addInput(getInput(i + 31));
        }
        for(int i = 36; i < 68; i++){
            adder.addInput(getInput(i));
            sub.addInput(getInput(i));
            and[i - 36].addInput(getInput(i));
            or[i - 36].addInput(getInput(i));
            nor[i - 36].addInput(getInput(i));
            srl.addInput(getInput(i));
            sll.addInput(getInput(i));
        }
        
        And and_adder[] = new And[32];
        for(int i = 0; i< 32; i++){
            and_adder[i] = new And("and", adder.getOutput(i + 1));
            and_adder[i].addInput(decoder.getOutput(10)); // 10 is control signal for add
        }
        
        And and_sub[] = new And[32];
        for(int i = 0; i< 32; i++){
            and_sub[i] = new And("and", sub.getOutput(i));
            and_sub[i].addInput(decoder.getOutput(6)); // 6 is control signal for sub
        }

        And and_and[] = new And[32];
        for(int i = 0; i< 32; i++){
            and_and[i] = new And("and", and[i].getOutput(0));
            and_and[i].addInput(decoder.getOutput(0)); // 0 is control signal for and
        }

        And and_or[] = new And[32];
        for(int i = 0; i< 32; i++){
            and_or[i] = new And("and", or[i].getOutput(0));
            and_or[i].addInput(decoder.getOutput(1)); // 1 is control signal for or
        }

        And and_nor[] = new And[32];
        for(int i = 0; i< 32; i++){
            and_nor[i] = new And("and", nor[i].getOutput(0));
            and_nor[i].addInput(decoder.getOutput(12)); // 12 is control signal for nor
        }

        And and_srl[] = new And[32];
        for(int i = 0; i< 32; i++){
            and_srl[i] = new And("and", srl.getOutput(i));
            and_srl[i].addInput(decoder.getOutput(15)); // 15 is control signal for srl
        }

        And and_sll[] = new And[32];
        for(int i = 0; i< 32; i++){
            and_sll[i] = new And("and", sll.getOutput(i));
            and_sll[i].addInput(decoder.getOutput(13)); // 13 is control signal for sll
        }

        Or outputOr[] = new Or[32];
        for(int i = 0; i < 32; i++){
            outputOr[i] = new Or("or", 
            and_adder[i].getOutput(0),
            and_sub[i].getOutput(0),
            and_and[i].getOutput(0),
            and_or[i].getOutput(0),
            and_nor[i].getOutput(0));
            and_srl[i].getOutput(0);
            and_sll[i].getOutput(0);
        }

        Or or_zero = new Or("or");
        for(int i = 0; i< 32; i++){
            or_zero.addInput(outputOr[i].getOutput(0));
        }

        Not not_zero = new Not("not", or_zero.getOutput(0));
        addOutput(not_zero.getOutput(0)); // zero flag

        for(int i = 0; i< 32; i++){
            addOutput(outputOr[i].getOutput(0)); // data
        }
    }
    
}
