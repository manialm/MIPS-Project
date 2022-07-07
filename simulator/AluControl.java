package simulator;

import simulator.gates.combinational.And;
import simulator.gates.combinational.Not;
import simulator.gates.combinational.Or;
import simulator.network.Link;
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

    public AluControl(String label, String stream, Link[] links) {
        super(label, stream, links);
    }

    @Override
    public void initialize() {
        //and : xxx100 => 0000
        Not not_input6 = new Not("not", getInput(6));
        Not not_input7 = new Not("not", getInput(7));
        And and_and = new And("and", getInput(5));
        and_and.addInput(not_input6.getOutput(0), not_input7.getOutput(0));
        Link link_and[] = new Link[4];
        for(int i =0 ; i < 4; i++){
            link_and[i] = new Link(false);
        }

        //or : xxx101 => 0001
        And and_or = new And("and", getInput(5), getInput(7));
        and_or.addInput(not_input6.getOutput(0));
        Link link_or[] = new Link[4];
        for(int i = 0; i < 3; i++){
            link_or[i] = new Link(false);
        }
        link_or[3] = new Link(true);

        //nor : xxx111 => 1100
        And and_nor = new And("and", getInput(5), getInput(6), getInput(7));
        Link link_nor[] = new Link[4];
        for(int i = 0; i < 2; i++){
            link_nor[i] = new Link(true);
        }
        for(int i = 2; i < 4; i++){
            link_nor[i] = new Link(false);
        }

        //add : xxx000 => 1010
        And and_add = new And("and", getInput(5), getInput(6), getInput(7));
        Not not_add = new Not("not", and_add.getOutput(0));
        Link link_add[] = new Link[4];
        link_add[0] = new Link(true);
        link_add[1] = new Link(false);
        link_add[2] = new Link(true);
        link_add[3] = new Link(false);

        //sub : xxx010 => 0110
        Not not_input5 = new Not("not", getInput(5));
        And and_sub = new And("and", getInput(6));
        and_sub.addInput(not_input5.getOutput(0));
        and_sub.addInput(not_input7.getOutput(0));
        Link link_sub[] = new Link[4];
        link_sub[0] = new Link(false);
        link_sub[1] = new Link(true);
        link_sub[2] = new Link(true);
        link_sub[3] = new Link(false);

        // 5 instruction * 4 link = 20
        And and_rformat[] = new And[20];
        for(int i =0; i< 20; i++){
            and_rformat[i] = new And("and");
        }
        /*
         * and
         *  0 -> 3  : and
         *  4 -> 7  : or
         *  8 -> 11 : nor
         *  12 -> 15: add
         *  16 -> 19: sub
         */
        int index = 0;
        for(int i = 0; i < 4; i++){
            and_rformat[i].addInput(and_and.getOutput(0), link_and[index]);;
            index++;
        }
        index = 0;
        for(int i = 4; i < 8; i++){
            and_rformat[i].addInput(and_or.getOutput(0), link_or[index]);
            index++;
        }
        index = 0;
        for(int i = 8; i < 12; i++){
            and_rformat[i].addInput(and_nor.getOutput(0), link_nor[index]);
            index++;
        }
        index = 0;
        for(int i = 12; i < 16; i++){
            and_rformat[i].addInput(not_add.getOutput(0), link_add[index]);
            index++;
        }
        index = 0;
        for(int i = 16; i < 20; i++){
            and_rformat[i].addInput(and_sub.getOutput(0), link_sub[index]);
            index++;
        }

        Or or_rformat[] = new Or[4];
        for(int i = 0; i < 4; i++){
            or_rformat[i] = new Or("or");
            or_rformat[i].addInput(and_rformat[i].getOutput(0));        // and
            or_rformat[i].addInput(and_rformat[i + 4].getOutput(0));    // or
            or_rformat[i].addInput(and_rformat[i + 8].getOutput(0));    // nor
            or_rformat[i].addInput(and_rformat[i + 12].getOutput(0));   // add
            or_rformat[i].addInput(and_rformat[i + 16].getOutput(0));   // sub
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
            and_addi[i].addInput(link_add[i]);
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
            and_andi[i].addInput(link_and[i]);
        }
        //ori
        // opcode : xxx101
        Not not_input10 = new Not("not", getInput(10));
        And and_ori_found = new And("and", not_input10.getOutput(0), getInput(9), getInput(11));
        And and_ori[] = new And[4];
        for(int i = 0; i < 4; i++){
            and_ori[i] = new And("and", getInput(8));
            and_ori[i].addInput(and_ori_found.getOutput(0));
            and_ori[i].addInput(link_or[i]);
        }




        // aluop1,aluop0 = 00
        // lw, sw : add
        Or or_rw = new Or("or", getInput(0), getInput(1));
        Not not_rw = new Not("not", or_rw.getOutput(0));
        And and_rw[] = new And[4];
        for(int i = 0; i< 4; i++){
            and_rw[i] = new And("and", not_rw.getOutput(0));
            and_rw[i].addInput(link_add[i]);
        }



        // aluop1,aluop0 = 01
        // branch : sub
        Not not_input0 = new Not("not", getInput(0));
        And and_branch_found = new And("and", not_input0.getOutput(0), getInput(1));
        And and_branch[] = new And[4];
        for(int i = 0; i < 4; i++){
            and_branch[i] = new And("and", and_branch_found.getOutput(0), link_sub[i]);
        }



        Or or_final[] = new Or[4];
        for(int i = 0; i < 4; i++){
            or_final[i] = new Or("or");
            or_final[i].addInput(and_10b[i].getOutput(0));
            or_final[i].addInput(and_addi[i].getOutput(0));
            or_final[i].addInput(and_andi[i].getOutput(0));
            or_final[i].addInput(and_ori[i].getOutput(0));
            or_final[i].addInput(and_rw[i].getOutput(0));
            or_final[i].addInput(and_branch[i].getOutput(0));

        }

        for(int i = 0; i< 4; i++){
            addOutput(or_final[i].getOutput(0));
        }


    }
    
}
