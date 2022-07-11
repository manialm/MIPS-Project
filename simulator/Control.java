package simulator;

import simulator.gates.combinational.And;
import simulator.gates.combinational.Not;
import simulator.gates.combinational.Or;
import simulator.network.Link;
import simulator.wrapper.Wrapper;

/*
 * control unit
 *  in:
 *      0 -> 5 : opcode
 *  out:
 *      0 : RegDst
 *      1 : Branch
 *      2 : MemRead
 *      3 : MemtoReg
 *      4 -> 5: ALUOp
 *      6 : MemWrite
 *      7 : ALUSrc
 *      8 : RegWrite
 *      9 : Jump
 *      10 : Imm
 */

 // all don't care states are assumed as 0

public class Control extends Wrapper {

    public Control(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        // RegDst
        // r-format : 1
        Or or_rformat = new Or("or_rformat");
        for(int i = 0; i< 6; i++){
            or_rformat.addInput(getInput(i));
        }
        Not not_rformat = new Not("not", or_rformat.getOutput(0));
        addOutput(not_rformat.getOutput(0)); // if r-format (regdst = 1) else (regdst = 0)

        // Branch
        // beq = 000100	: 1
        // bne = 000101 : 1
        Not not_fourthinput = new Not("not", getInput(3));
        Or or_branch = new Or("or", not_fourthinput.getOutput(0));
        for(int i = 0; i < 5; i++){
            if(i == 3){
                continue;
            }
            or_branch.addInput(getInput(i));
        }
        Not not_branch = new Not("not", or_branch.getOutput(0));
        addOutput(not_branch.getOutput(0));

        // MemRead
        // lb = 100000 : 1
        // lh = 100001 : 1
        // lw = 100011 : 1
        // lbu= 100100 : 1
        // lhu= 100101 : 1
        // first 3 bits are enough
        Not not_firstbit = new Not("not", getInput(0));
        Or or_load = new Or("or", not_firstbit.getOutput(0));
        or_load.addInput(getInput(1));
        or_load.addInput(getInput(2));
        Not not_load = new Not("not", or_load.getOutput(0));
        addOutput(not_load.getOutput(0));
        

        // MemtoReg
        // (don't care states = 0) => (MemtoReg = MemRead)
        addOutput(not_load.getOutput(0));

        // AluOp1
        // (don't care states = 0) => (AluOp1 = RegDst)
        addOutput(not_rformat.getOutput(0));

        // AluOp0
        // AluOp0 = Branch
        addOutput(not_branch.getOutput(0));

        // MemWrite
        // sb = 101000 : 1
        // sh = 101001 : 1
        // sw = 101011 : 1
        // first 3 bits are enough
        Not not_secondbit = new Not("not", getInput(1));
        And and_write = new And("and", not_secondbit.getOutput(0));
        and_write.addInput(getInput(0));
        and_write.addInput(getInput(2));
        addOutput(and_write.getOutput(0));

        // AluSrc
        // loads, stors : 1
        // immediates => first 3 bits = 001
        Not not_thirdbit = new Not("not", getInput(2));
        Or or_imm = new Or("or", not_thirdbit.getOutput(0));
        or_imm.addInput(getInput(0));
        or_imm.addInput(getInput(1));
        Not not_imm = new Not("not", or_imm.getOutput(0));
        Or or_alusrc = new Or("or", not_imm.getOutput(0));
        or_alusrc.addInput(and_write.getOutput(0));
        or_alusrc.addInput(not_load.getOutput(0));
        addOutput(or_alusrc.getOutput(0));

        // RegWrite
        // loads, r-format
        Or or_regwrite = new Or("or", not_load.getOutput(0), not_rformat.getOutput(0));
        addOutput(or_regwrite.getOutput(0));

        // Jump
        // j = 000010
        // jal = 000011
        Not not_input5 = new Not("not", getInput(4));
        Or or_j = new Or("or", not_input5.getOutput(0));
        for(int i = 0; i < 4; i++){
            or_j.addInput(getInput(i));
        }
        Not not_jump = new Not("not", or_j.getOutput(0));
        addOutput(not_jump.getOutput(0));
        

        //Imm
        Not not_input8 = new Not("not", getInput(0));
        Not not_input9 = new Not("not", getInput(1));
        And and_imm = new And("and", getInput(2));
        and_imm.addInput(not_input8.getOutput(0), not_input9.getOutput(0));
        addOutput(and_imm.getOutput(0));
    }
    
}
