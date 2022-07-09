package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.And;
import simulator.network.Link;
import simulator.wrapper.Wrapper;
import simulator.wrapper.wrappers.Decoder;
import simulator.wrapper.wrappers.Multiplexer;

/*
 * a register file
 *  in:
 *      0 : clock
 *      1 : regWrite
 *      2 -> 6 : read register 1
 *      7 -> 11 : read register 2
 *      12 -> 16 : write register
 *      17 -> 48 : write data
 * 
 * out:
 *      0 -> 31 : Read Data 1
 *      32 -> 63 : Read Data 2
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
        Multiplexer muxs1[] = new Multiplexer[32];
        Multiplexer muxs2[] = new Multiplexer[32];
        for(int i = 0; i < 32; i++){
            muxs1[i] = new Multiplexer("1mux" + i, "37x1");
            muxs2[i] = new Multiplexer("2mux" + i, "37x1");
        }
        for(int i =0; i<32; i++){
            for(int j = 2; j < 7; j++){
                muxs1[i].addInput(getInput(j));
                muxs2[i].addInput(getInput(j + 5));
            }
        }
        for(int i = 0; i < 32; i++){
            for(int j = 0; j < 32; j++){
                muxs1[i].addInput(registers[j].getOutput(i));
                muxs2[i].addInput(registers[j].getOutput(i));
            }
        }
        for(int i = 0; i< 32; i++){
            addOutput(muxs1[i].getOutput(0));
        }
        for(int i = 0; i< 32; i++){
            addOutput(muxs2[i].getOutput(0));
        }
        
    }
    
}
