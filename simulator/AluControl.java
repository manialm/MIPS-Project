package simulator;

import simulator.control.Simulator;
import simulator.gates.combinational.And;
import simulator.gates.combinational.Not;
import simulator.gates.combinational.Or;
import simulator.network.Link;
import simulator.network.Node;
import simulator.wrapper.Wrapper;

/*
 * alu control
 *  in:
 *      0 : AluOp1   (highest order bit)
 *      1 : AluOp0
 *      2 -> 7 : function
 *      8 : Imm signal
 *      9 -> 11: 3 last bits of opcode
 *  out:
 *      0 -> 3 : control signals
 *      4 : shift signal
 */

/*
 * functions
 *  add : 100000
 *  sub : 100010
 *  and : 100100
 *  or  : 100101
 *  nor : 100111
 * three last bits are enough (from fifth to seventh input bit)
 */
 
public class AluControl extends Wrapper{

    public AluControl(String label, String stream, Link... links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        //and : 1xx100 => 0000
        Not not_input6 = new Not("not", getInput(6));
        Not not_input7 = new Not("not", getInput(7));
        And and_and = new And("and", getInput(5));
        and_and.addInput(not_input6.getOutput(0), not_input7.getOutput(0), getInput(2));
        Buffer buffer_and[] = new Buffer[4];
        for(int i =0 ; i < 4; i++){
            buffer_and[i] = new Buffer("buffer", Simulator.falseLogic);
        }

        //or : 1xx101 => 0001
        And and_or = new And("and", getInput(5), getInput(7), getInput(2));
        and_or.addInput(not_input6.getOutput(0));
        Buffer buffer_or[] = new Buffer[4];
        for(int i = 0; i < 3; i++){
            buffer_or[i] = new Buffer("buffer",Simulator.falseLogic);
        }
        buffer_or[3] = new Buffer("buffer",Simulator.trueLogic);

        //nor : 1xx111 => 1100
        And and_nor = new And("and", getInput(5), getInput(6), getInput(7), getInput(2));
        Buffer buffer_nor[] = new Buffer[4];
        for(int i = 0; i < 2; i++){
            buffer_nor[i] = new Buffer("buffer", Simulator.trueLogic);
        }
        for(int i = 2; i < 4; i++){
            buffer_nor[i] = new Buffer("buffer", Simulator.falseLogic);
        }

        //add : 1xx000 => 1010
        Not not_input2 = new Not("not", getInput(2));
        Or or_add = new Or("or", getInput(5), getInput(6), getInput(7), not_input2.getOutput(0));
        Not not_add = new Not("not", or_add.getOutput(0));
        Buffer buffer_add[] = new Buffer[4];
        buffer_add[0] = new Buffer("buffer", Simulator.trueLogic);
        buffer_add[1] = new Buffer("buffer", Simulator.falseLogic);
        buffer_add[2] = new Buffer("buffer", Simulator.trueLogic);
        buffer_add[3] = new Buffer("buffer", Simulator.falseLogic);

        //sub : 1xx010 => 0110
        Not not_input5 = new Not("not", getInput(5));
        And and_sub = new And("and", getInput(6), getInput(2));
        and_sub.addInput(not_input5.getOutput(0));
        and_sub.addInput(not_input7.getOutput(0));
        Buffer buffer_sub[] = new Buffer[4];
        buffer_sub[0] = new Buffer("buffer",Simulator.falseLogic);
        buffer_sub[1] = new Buffer("buffer",Simulator.trueLogic);
        buffer_sub[2] = new Buffer("buffer",Simulator.trueLogic);
        buffer_sub[3] = new Buffer("buffer",Simulator.falseLogic);

        //srl : 0xx010 => 1111
        Or or_srl = new Or("or", getInput(2),getInput(5), getInput(7));
        or_srl.addInput(not_input6.getOutput(0));
        Not not_srl = new Not("not", or_srl.getOutput(0));
        Buffer buffer_srl[] = new Buffer[4];
        for(int i = 0; i< 4; i++){
            buffer_srl[i] = new Buffer("buffer", Simulator.trueLogic);
        }

        //sll : 0xx000 => 1101
        Or or_sll = new Or("or", getInput(2), getInput(5), getInput(6), getInput(7));
        Not not_sll = new Not("not", or_sll.getOutput(0));
        Buffer buffer_sll[] = new Buffer[4];
        buffer_sll[0] = new Buffer("buffer", Simulator.trueLogic);
        buffer_sll[1] = new Buffer("buffer", Simulator.trueLogic);
        buffer_sll[2] = new Buffer("buffer", Simulator.trueLogic);
        buffer_sll[3] = new Buffer("buffer", Simulator.trueLogic);


        // 7 instruction * 4 link = 28
        And and_rformat[] = new And[28];
        for(int i =0; i< 28; i++){
            and_rformat[i] = new And("and");
        }
        /*
         * and
         *  0 -> 3  : and
         *  4 -> 7  : or
         *  8 -> 11 : nor
         *  12 -> 15: add
         *  16 -> 19: sub
         *  20 -> 23: srl
         *  24 -> 28: sll
         */
        int index = 0;
        for(int i = 0; i < 4; i++){
            and_rformat[i].addInput(and_and.getOutput(0), buffer_and[index].getOutput(0));;
            index++;
        }
        index = 0;
        for(int i = 4; i < 8; i++){
            and_rformat[i].addInput(and_or.getOutput(0), buffer_or[index].getOutput(0));
            index++;
        }
        index = 0;
        for(int i = 8; i < 12; i++){
            and_rformat[i].addInput(and_nor.getOutput(0), buffer_nor[index].getOutput(0));
            index++;
        }
        index = 0;
        for(int i = 12; i < 16; i++){
            and_rformat[i].addInput(not_add.getOutput(0), buffer_add[index].getOutput(0));
            index++;
        }
        index = 0;
        for(int i = 16; i < 20; i++){
            and_rformat[i].addInput(and_sub.getOutput(0), buffer_sub[index].getOutput(0));
            index++;
        }
        index = 0;
        for(int i = 20; i < 24; i++){
            and_rformat[i].addInput(not_srl.getOutput(0), buffer_srl[index].getOutput(0));
            index++;
        }
        index = 0;
        for(int i = 24; i < 28; i++){
            and_rformat[i].addInput(not_sll.getOutput(0), buffer_sll[index].getOutput(0));
            index++;
        }

        Or or_rformat[] = new Or[4];
        for(int i = 0; i < 4; i++){
            or_rformat[i] = new Or("or");
        }
        for(int i = 0; i < 4; i++){
            or_rformat[i].addInput(and_rformat[i].getOutput(0));        // and
            or_rformat[i].addInput(and_rformat[i + 4].getOutput(0));    // or
            or_rformat[i].addInput(and_rformat[i + 8].getOutput(0));    // nor
            or_rformat[i].addInput(and_rformat[i + 12].getOutput(0));   // add
            or_rformat[i].addInput(and_rformat[i + 16].getOutput(0));   // sub
            or_rformat[i].addInput(and_rformat[i + 20].getOutput(0));   // srl
            or_rformat[i].addInput(and_rformat[i + 24].getOutput(0));   // sll
        }
        

        And and_10b[] = new And[4];
        Not not_input1 = new Not("not", getInput(1));
        for(int i = 0; i< 4; i++){
            and_10b[i] = new And("and", getInput(0));
            and_10b[i].addInput(not_input1.getOutput(0));
            and_10b[i].addInput(or_rformat[i].getOutput(0));
        }





        //addi
        // opcode : xxx000
        Or or_addi = new Or("or", getInput(9), getInput(10), getInput(11));
        Not not_addi = new Not("not", or_addi.getOutput(0));
        And and_addi[] = new And[4];
        for(int i = 0; i < 4; i++){
            and_addi[i] = new And("and", getInput(8));
            and_addi[i].addInput(not_addi.getOutput(0));
            and_addi[i].addInput(buffer_add[i].getOutput(0));
        }
        //andi
        // opcode : xxx100
        Not not_input9 = new Not("not", getInput(9));
        Or or_andi = new Or("or", getInput(10), getInput(11), not_input9.getOutput(0));
        Not not_andi = new Not("not", or_andi.getOutput(0));
        And and_andi[] = new And[4];
        for(int i = 0; i < 4; i++){
            and_andi[i] = new And("and", getInput(8));
            and_andi[i].addInput(not_andi.getOutput(0));
            and_andi[i].addInput(buffer_and[i].getOutput(0));
        }
        //ori
        // opcode : xxx101
        Not not_input10 = new Not("not", getInput(10));
        And and_ori_found = new And("and", not_input10.getOutput(0), getInput(9), getInput(11));
        And and_ori[] = new And[4];
        for(int i = 0; i < 4; i++){
            and_ori[i] = new And("and", getInput(8));
            and_ori[i].addInput(and_ori_found.getOutput(0));
            and_ori[i].addInput(buffer_or[i].getOutput(0));
        }




        // aluop1,aluop0 = 00
        // lw, sw : add
        Or or_rw = new Or("or", getInput(0), getInput(1));
        Not not_rw = new Not("not", or_rw.getOutput(0));
        And and_rw[] = new And[4];
        for(int i = 0; i< 4; i++){
            and_rw[i] = new And("and", not_rw.getOutput(0));
            and_rw[i].addInput(buffer_add[i].getOutput(0));
        }



        // aluop1,aluop0 = 01
        // branch : sub
        Not not_input0 = new Not("not", getInput(0));
        And and_branch_found = new And("and", not_input0.getOutput(0), getInput(1));
        And and_branch[] = new And[4];
        for(int i = 0; i < 4; i++){
            and_branch[i] = new And("and", and_branch_found.getOutput(0), buffer_sub[i].getOutput(0));
        }

        Not not_imm = new Not("not", getInput(8));
        And and_10b_imm[] = new And[4];
        for(int i = 0; i < 4; i++){
            and_10b_imm[i] = new And("and", and_10b[i].getOutput(0));
            and_10b_imm[i].addInput(not_imm.getOutput(0));
        }

        And and_rw_imm[] = new And[4];
        for(int i = 0; i< 4; i++){
            and_rw_imm[i] = new And("and", and_rw[i].getOutput(0));
            and_rw_imm[i].addInput(not_imm.getOutput(0));
        }

        And and_branch_imm[] = new And[4];
        for(int i = 0; i< 4; i++){
            and_branch_imm[i] = new And("and", and_branch[i].getOutput(0));
            and_branch_imm[i].addInput(not_imm.getOutput(0));
        }
        


        Or or_final[] = new Or[4];
        for(int i = 0; i < 4; i++){
            or_final[i] = new Or("or");
            or_final[i].addInput(and_10b_imm[i].getOutput(0));
            or_final[i].addInput(and_addi[i].getOutput(0));
            or_final[i].addInput(and_andi[i].getOutput(0));
            or_final[i].addInput(and_ori[i].getOutput(0));
            or_final[i].addInput(and_rw_imm[i].getOutput(0));
            or_final[i].addInput(and_branch_imm[i].getOutput(0));

        }

        for(int i = 0; i< 4; i++){
            addOutput(or_final[i].getOutput(0));
        }
        //shift signal
        Or or_shift = new Or("or");
        or_shift.addInput(not_srl.getOutput(0));
        or_shift.addInput(not_sll.getOutput(0));
        addOutput(or_shift.getOutput(0));
    }
    
}
