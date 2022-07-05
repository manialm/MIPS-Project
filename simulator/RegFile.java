package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.And;
import simulator.network.Link;
import simulator.wrapper.Wrapper;
import simulator.wrapper.wrappers.Decoder;

/*
 * a register file
 *  in:
 *      0 : clock
 *      1 : regWrite
 *      2 -> 6 : read register 1
 *      7 -> 11 : read register 2
 *      12 -> 16 : write register
 *      17 -> 48 : write data
 */
public class RegFile extends Wrapper {
    static final int size = 32;
    public RegFile(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        Register[] registers = new Register[size];
        And[] ands = new And[size];
        for(int i = 0 ; i < size; i++){
            registers[i] = new Register("reg" + i, "34x32", getInput(0), Simulator.falseLogic);
            ands[i] = new And("and"+ i, getInput(1));
            for(int j = 17 ; j < 49; j++){
                registers[i].addInput(getInput(j));
            }
        }
        Decoder decoder = new Decoder("decoder", "5x32");
        for(int i = 12; i < 17; i++){
            decoder.addInput(getInput(i));
        }
        for(int i =0; i< 32; i++){
            ands[i].addInput(decoder.getOutput(i));
            registers[i].setInput(1, ands[i].getOutput(0));
        }
        // add read registers

        /* 
        for(int i = 0; i< 32; i++){
            addOutput(registers[0].getOutput(i));
        }
        for(int i = 0; i< 32; i++){
            addOutput(registers[1].getOutput(i));
        }
        */
    }
    
}
