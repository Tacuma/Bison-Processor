/*
 * Program 2 
 * Tacuma Solomon
 * SYCS 401 - Operating Systems (Fall 2011)
 * Dr. Legand Burge
 *  This program is a simulator of the Bison Processor2. 
 */

package bisonprocessor2;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

enum Instr { ZERO, ONE };

/*This is the CPU class. The class contains,:
 *  a stack which acts as the program registers,
 *  a Program Counter, which contains the a number, keeping track of the instuctions output,
 *  the IR, which holds a value of an instuction,
 *  the BR and LR, which is the base and limit registers,
 *  The system mode,
 *  syscall status,
 *  and the sustem clock.
 *  methods for setting and getting the privately held variables, 'HEXStringToShort', and 'decode'
 */

public class CPU {

   private Stack<Integer> s = new Stack();  //stack for the accumulator
   private int PC;                             //program counter
   private int IR;                               //instruction register
   private int BR;                             //base register
   private short LR;                             //limit register
   private int mode;                          //system mode
   private int SC;                             //syscall status
   private int IC;
   private long Clock;                           //system clock, init to zero
   Boolean branched = false;
   Boolean halt = false;
   Memory memRef;

   //This is the first constuctor for CPU. It creates an instance without initiating any of the values of the
   // private values
   public CPU(){
   }

   public CPU(Memory m){
       memRef=m;       
   }
   // The second constructor for CPU constructs an instance of CPU, whilst initiating values for CPU
   public CPU(int IC, int br, int ir, int pc, long clock, Memory m){
    IC = 0;
    BR = br;
    IR = ir;
    PC = pc;
    Clock = clock;
    memRef = m;
    s.push(0);
   }
   
   public void pushStack(int push){
       s.push(push);
   }
   public void setIR(int ir){      //setter method for IR
       IR = ir;
   }

   public int getIR(){                      //getter method for IR
       return IR;
   }

   public void setBR(short br){    //setter method for BR
       BR = br;
   }

   public int getBR(){                    //getter method for BR
       return BR;
   }

   public void setLR(short lr){    // setter method for LR
       LR = lr;
   }

   public short getLR(){                    // getter method for LR
       return LR;
   }

   public void setSC(int sc){    // setter method for SC
       SC = sc;
   }

   public int getSC(){                    // getter method for SC
       return SC;
   }

   public void setClock(long c){  // setter method for clock
       Clock = c;
   }

   public long getClock(){                  // getter method for clock
       return Clock;
   }

   public void setPC(int PC){             // setter method for PC
       this.PC = PC;
   }

   public int getPC(){                    // getter method for PC
       return PC;
   }
   
   public int getmode(){
       return mode;
   }
   
 /*  public String toString(){
       return
   } */


  /* This method takes a string that was read in from the main, and translates it into a omt, where it is
   * stored in the IR
   */

  public void HEXStringToShort(String hex){  // read in line is passed into the method
      int int_value = Integer.parseInt(hex, 16); // line is changed from a hex string to an integer
      setIR(int_value); //value is set to IR
    }

  /* The decode function takes the IR, and decodes it, depending on the value of the Intruction type.
   * It also takes in a copy of the undecoded line, for outputing the orignal hex later in the display instruction methods
   * of the instruction class.
   */

  public void decode() throws IOException{//(String line){
      Instruction inst;
      int instruction = getIR();
      int instructiontype = instruction;
      instructiontype = instructiontype >> 15; // parses instruction for instruction bit
        if (instructiontype == 0){             // if instruction bit is zero..
            int zero_opcode1 = instruction;
            int zero_opcode2 = instruction;    // create two int vars to hold the instruction for decoding
            zero_opcode1 = zero_opcode1 >> 8;
            zero_opcode1 = zero_opcode1 & 0x001F; // decodes the opcode for substring 1
            //String part1 = line.substring(0,2);   // stores the first half of the hex string
            Instruction zeroinstruction = new Instruction(Instr.ZERO,zero_opcode1);
                                                  // creates a new instance of an instruction
            IC++;
          // zeroinstruction.displayzeroinstruction(IC);  //invokes the instruction's display method
           //System.out.println(toString());
            exec(zeroinstruction);
            

            zero_opcode2 = zero_opcode2 & 0x001F;  // deodes the second zero instruction's opcode
            //String part2 = line.substring(2,4);    // takes the second half of the hex substring
            Instruction zeroinstruction2 = new Instruction(Instr.ZERO,zero_opcode2);
                                                   // creates a new instance of an instruction
            IC++;
          //  zeroinstruction2.displayzeroinstruction(IC); //invokes instruction display method
            //System.out.println(toString());
            exec(zeroinstruction2);
        }
        else if (instructiontype == 1) {    // if instruction bit is = 1
            int opcode = instruction;
            int index_bit = instruction;
            int daddr = instruction;
            Instr bit = null;               // sets up int vars to collect decoded data
            opcode = opcode >> 10;
            opcode = opcode & 0x001F;       //decodes opcode
            index_bit = index_bit >> 9;
            index_bit = index_bit & 0x0001; // decodes index bit
                 if (index_bit==0){
                    bit=Instr.ZERO;
            }
                 if (index_bit==1){
                    bit=Instr.ONE;
            }
            daddr = daddr & 0x00FF;         // decodes daddr
            Instruction oneinstruction = new Instruction(Instr.ONE, opcode, bit, daddr);
                                            // creates a new instance of a one bit instruction
            IC++;
           // oneinstruction.displayoneinstruction(IC);
                                            // invokes display method for instruction
           // System.out.println(toString());
            exec(oneinstruction);
        }
  } 

  public void exec(Instruction inst) throws IOException{
       int result;
       String output;
       int item1,item2;
       if (inst.getInstr_type()==Instr.ZERO){
           switch(inst.getOpcode()) {
               case 0: {//no op
               } break;    

               case 1: {     //OR
                  item1 = s.pop();
                  item2 = s.pop();
                  result = (item1 | item2);
                  s.push(result);
               } break;

               case 2:{     //AND
                  item1 = s.pop();
                  item2 = s.pop();
                  result = (item1 & item2);
                  s.push(result);
               } break;

               case 3: {    //NOT
                  item1 = s.pop();
                  result = ~item1;
                  s.push(result);
               } break;

               case 4: {    //XOR
                  item1 = s.pop();
                  item2 = s.pop();
                  result = (item1 ^ item2);
                  s.push(result);
               } break;

               case 5: {    //ADD
                   item1 = s.pop();
                  item2 = s.pop();
                  result = (item1 + item2);
                  s.push(result);
               } break;

               case 6: {    //SUB
                  item1 = s.pop();
                  item2 = s.pop();
                  result = (item2 - item1);
                  s.push(result);
               } break;

               case 7: {    //MUL
                  item1 = s.pop();
                  item2 = s.pop();
                  result = (item1 * item2);
                  s.push(result);
               } break;

               case 8: {    //DIV
                  item1 = s.pop();
                  item2 = s.pop();
                  result = (item2 / item1);
                  s.push(result);
               } break;

               case 9: {    //MOD
                  item1 = s.pop();
                  item2 = s.pop();
                  result = (item2 % item1);
                  s.push(result);
               } break;

               case 10: {   //SL
                  item1 = s.pop();
                  result = item1 << 1 ;
                  s.push(result);
               } break;

               case 11: {   //SR
                  item1 = s.pop();
                  result = item1 >> 1 ;
                  s.push(result);
               } break;

               case 12: {   //CPG
                  item1 = s.pop();
                  item2 = s.pop();
                  if (item2>item1)
                      result = 1;
                  else 
                      result = 0;
                  s.push(item2);
                  s.push(item1);
                  s.push(result);
               } break;

               case 13: {   //CLG
                  item1 = s.pop();
                  item2 = s.pop();
                  if (item2<item1)
                      result = 1;
                  else 
                      result = 0;
                  s.push(item2);
                  s.push(item1);
                  s.push(result);
               } break;

               case 14: {   //CPE
                  item1 = s.pop();
                  item2 = s.pop();
                  if (item2==item1)
                      result = 1;
                  else 
                      result = 0;
                  s.push(item2);
                  s.push(item1);
                  s.push(result);
               } break;

               case 15: { // -----
               } break;

               case 16: { // ------
               } break;

               case 17: { // -----
               } break;

               case 18: { // ------
               } break;

               case 19: { //SYSCALL (may need a reference variable for memory ...
                   if (getSC()==1){
                           item1 = s.pop();
                           System.out.println(item1);
                       }
                   else if (getSC()==3){
                          InputStreamReader inp = new InputStreamReader(System.in); 
                          BufferedReader br = new BufferedReader(inp); 
                          String str = br.readLine();
                          int number = Integer.parseInt(str);
                          s.push(number); 
                   }
               } break;

               case 21: {//RTN
                          setPC(s.pop());
               }    break;

               case 22: {} break;//PUSH
                   
               case 23: {} break;//POP
                   
               case 24: {
                  halt = true;
                  return;
                       }
               default: {
                   System.out.println( "error");
               }break;

            }
       }
       else if (inst.getInstr_type()==Instr.ONE){
           switch(inst.getOpcode()) {
               case 0: {//no op
                   output = "No op";
               } break;    

               case 1: {     //OR
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  result = (item1 | item2);
                  s.push(result);
               } break;

               case 2:{     //AND
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  result = (item1 & item2);
                  s.push(result);
               } break;

               case 3: {    //NOT
               } break;

               case 4: {    //XOR
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  result = (item1 ^ item2);
                  s.push(result);
               } break;

               case 5: {    //ADD
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  result = (item1 + item2);
                  s.push(result);
               } break;

               case 6: {    //SUB
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  result = (item1 - item2);
                  s.push(result);
               } break;

               case 7: {    //MUL
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  result = (item1 * item2);
                  s.push(result);
               } break;

               case 8: {    //DIV
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  result = (item1 / item2);
                  s.push(result);
               } break;

               case 9: {    //MOD
                  item1 = s.pop();
                  item2 = s.pop();
                  result = (item1 % item2);
                  s.push(result);
               } break;

               case 10: {   //SL
               } break;

               case 11: {   //SR
               } break;

               case 12: {   //CPG
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  if (item1>item2)
                      result = 1;
                  else 
                      result = 0;
                  s.push(item1);
                  s.push(result);
               } break;

               case 13: {   //CPL
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  if (item1<item2)
                      result = 1;
                  else 
                      result = 0;
                  s.push(item1);
                  s.push(result);
               } break;

               case 14: {   //CPE
                  item1 = s.pop();
                  item2 = memRef.getMemoryWord(inst.getDaddr());
                  if (item2==item1)
                      result = 1;
                  else 
                      result = 0;
                  s.push(item1);
                  s.push(result);
               } break;

               case 15: { //BR
                   setPC(inst.getDaddr());
                   branched = true;
               } break;

               case 16: { //BRT
                   item1 = s.pop();
                   if(item1==1) {
                           setPC(inst.getDaddr());
                           branched = true;
                   }
               } break;
              
               case 17:  { //BRF
                   item1 = s.pop();
                   if(item1==0) {
                            setPC(inst.getDaddr());
                            branched = true;
                   }
               } break;

               case 18:  { //CALL
                   setPC(inst.getDaddr());
                   s.push(memRef.getMemoryWord(getPC()));
               } break;

               case 19: { //SET (may need a reference variable for memory ...
                  setSC(memRef.getMemoryWord(inst.getDaddr()));
               } break;

               case 21: {//SYSCALL
                   if (getSC()==2){
                       System.out.println(memRef.getMemoryString(inst.getDaddr()));
                   }
                       //System.out.println(memRef.getMemoryWord(inst.getDaddr()));
                   else if (getSC()==4) {
                          InputStreamReader inp = new InputStreamReader(System.in); 
                          BufferedReader br = new BufferedReader(inp); 
                          String str = br.readLine();
                   }
               }break;
                   
               case 22: {} { //PUSH
                   //System.out.println(inst.getDaddr());
                   s.push(memRef.getMemoryWord(inst.getDaddr()));
                   //s.push(inst.getDaddr());
               }break;
                   
               case 23: { //POP
                   memRef.setMemoryWord(s.pop(), inst.getDaddr());
               } break;
                   
               default: {
                   System.out.println( "error");
               }break;

            } //end case
           
       }//end if
       setClock(getClock()+1);
       
  }//end method
  
  public void fetch() throws IOException{
            IR = memRef.getMemoryWord(PC);     
            decode();
            if (halt == true)
                return;
            if (branched == false) // Don't increment the PC if it is fetching from a just branched address
                PC++;
            branched = false;      // Resets branched
}
  
  public String toString(){
      return "CPU["+getClock()+"] PC=("+getPC()+"), IR=("+getIR()+")>{"+getmode()+"}, SC = "+getSC()+" ,Stack = "+s.peek()+"\n";
  }
  
 
  public void run() {
      halt = false;
       do {  
           try {
               fetch();
           } catch (IOException ex) {
               Logger.getLogger(CPU.class.getName()).log(Level.SEVERE, null, ex);
           } 
       }while(halt != true);
       PC = 0;
  }

}



