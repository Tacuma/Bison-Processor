/*
 * Program 2 
 * Tacuma Solomon
 * SYCS 401 - Operating Systems (Fall 2011)
 * Dr. Legand Burge
 *  This program is a simulator of the Bison Processor2. 
 */


package bisonprocessor2;

/* This class represents an instruction in the bison processor. Instances generated from this class
 * are the manifestations of intstructions on the processor.
 * Each instruction (ZERO TYPE) has an Instr_type enum, and opcode
 * Each instrucition (ONE TYPE) has an Intr_Type, Opcode, Index_bit enum, and daddr
 */

public class Instruction {

    private Instr Instr_type;      //This represents the instruction type
    private Instr Index_bit;       // index bit
    private int Opcode;            // instruction opcode
    private int Daddr;             // Memory address
    private String line;           // Takes in the substring of the read in line


    // instantiates a blank instruction
    public Instruction(){
    }

    /* instantiates a zero bit instruction
     * containing an instruction type, opcode, and substring
     */
    public Instruction (Instr Instr_type, int Opcode){    
        this.Instr_type = Instr_type;
        this.Opcode = Opcode;
       // this.line = line;
    }

    /* instantiates a one bit instruction
     * containing instruction type, opcode, index bit, daddr, line
     */
    public Instruction (Instr Instr_type, int Opcode, Instr Index_bit,  int Daddr){
        this.Instr_type = Instr_type;
        this.Index_bit = Index_bit;
        this.Opcode = Opcode;
        this.Daddr = Daddr;
        //this.line = line;
    }

   // setter and getter methods for the private data types

   public void setInstr_type (Instr Instr_type) {
       this.Instr_type = Instr_type;
   }

   public Instr getInstr_type(){
       return Instr_type;
   }

   public void Index_bit (Instr Index_bit) {
       this.Index_bit = Index_bit;
   }

   public Instr getIndex_bit(){
       return Index_bit;
   }

   public void setOpcode(int Opcode){
       this.Opcode = Opcode;
   }

   public int getOpcode(){
       return Opcode;
   }

   public void setDaddr(short Daddr){
       this.Daddr=Daddr;
   }

   public int getDaddr(){
       return Daddr;
   }

   public String getline(){
       return line;
   }

   /* This is the method for displaying a zero instruction.
    * case statements are used to decode the opcode into its
    * relevant instruction.
    */
   public void displayzeroinstruction(long IC){
       String display;
       switch(getOpcode()) {
           case 0: display = "NOP";      break;
           case 1: display = "OR";      break;
           case 2: display = "AND";     break;
           case 3: display = "NOT";     break;
           case 4: display = "XOR";     break;

           case 5: display = "ADD";     break;
           case 6: display = "SUB";     break;
           case 7: display = "MUL";     break;
           case 8: display = "DIV";     break;
           case 9: display = "MOD";     break;

           case 10: display = "SL";     break;
           case 11: display = "SR";     break;

           case 12: display = "CPG";    break;
           case 13: display = "CPL";    break;
           case 14: display = "CPE";    break;

           case 15: display = "BR";     break;
           case 16: display = "BRT";    break;
           case 17: display = "BRF";    break;
           case 18: display = "CALL";   break;

           case 19: display = "SYSCALL";break;

           case 21: display = "RTN";    break;
           case 22: display = "PUSH";   break;
           case 23: display = "POP";    break;
           case 24: display = "HALT";   break;
           default: display = "invalid";break;
        }
       // String subline = getline().substring(0,2);
        System.out.println("Instruction ("+IC+"): "+"0x");//+getline());
        System.out.println("          Type: zero-address");
        System.out.println("          Opcode: ("+getOpcode()+") "+ display);
   }


   /* This method displays information for a one bit instruction.
    * Using case statements, the opcode is translated to its relevant instruction.
    * The PC, opcode, index bit, and daddr is displayed
    */
    public void displayoneinstruction(long IC){
       String display;
       switch(getOpcode()) {
           case 0: display = "NOP";     break;
           case 1: display = "OR";      break;
           case 2: display = "AND";     break;
           case 3: display = "NOT";     break;
           case 4: display = "XOR";     break;

           case 5: display = "ADD";     break;
           case 6: display = "SUB";     break;
           case 7: display = "MUL";     break;
           case 8: display = "DIV";     break;
           case 9: display = "MOD";     break;

           case 10: display = "SL";     break;
           case 11: display = "SR";     break;

           case 12: display = "CPG";    break;
           case 13: display = "CPL";    break;
           case 14: display = "CPE";    break;

           case 15: display = "BR";     break;
           case 16: display = "BRT";    break;
           case 17: display = "BRF";    break;
           case 18: display = "CALL";   break;

           case 19: display = "SET";     break;
           case 21: display = "SYSCALL"; break;
           case 22: display = "PUSH";    break;
           case 23: display = "POP";     break;
           case 24: display = "HLT";     break;
           default: display = "invalid"; break;
       }
       Instr bit = getIndex_bit();
       String display_index_bit;
       // Assigns value of the display index string based on value of index_bit
       switch(bit){
           case ZERO: display_index_bit = "0 (no indexing)"; break;
           case ONE: display_index_bit = "1 (indexing)";     break;
           default: display_index_bit = "Error";
       }
       // printlines the instruction data
        System.out.println("Instruction ("+IC+"): "+"0x");//+getline());
        System.out.println("          Type: one-address");
        System.out.println("          Opcode: ("+getOpcode()+") "+ display);
        System.out.println("          Index bit: "+display_index_bit);
        System.out.println("          DADDR: "+getDaddr());
        System.out.println("          EA: "+getDaddr());
   }
}
