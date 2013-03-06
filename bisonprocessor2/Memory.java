/* Program 2 
 * Tacuma Solomon
 * SYCS 401 - Operating Systems (Fall 2011)
 * Dr. Legand Burge
 *  This program is a simulator of the Bison Processor2. 
 */

package bisonprocessor2;
/**
 *
 * @author TaKuma
 */
public class Memory {
    
   private int memory[];
   
   
   public Memory(int size){
      this.memory=new int[size];
   }
   
  
   public int getMemoryWord(int loc){
       int word = memory[loc];
       return word;
   }
   
   public void clearMemory(){
      for(int i=0; i<memory.length;i++){
             memory[i] = 0;
      }
   }
   
   public String getMemoryString(int loc){      
       int instruction = (memory[loc]);  
       int ascii[] = new int [2];
       char chars[] = new char[2];
       String memstring= ("");
       ascii[0] = instruction >> 8;
       ascii[1] = instruction & 0x00FF;
       while((ascii[0] != 0) && (ascii[1] != 0)){
           chars[0] = (char) ascii[0];
           chars[1] = (char) ascii[1];
           memstring = memstring + chars[0]+ chars[1];
           loc++;
           instruction = (memory[loc]);    
           ascii[0] = instruction >> 8;
           ascii[1] = instruction & 0x00FF;
       }
       return memstring;
   }     
         
   
   public void setMemoryString(String s, int loc){     
        int i=0;
        char char1;
        char char2;
        while(i < s.length()){
            char1 = s.charAt(i);
            i++;
            char2 = s.charAt(i);
            i++;
            int ascii1 = (int)char1;
            int ascii2 = (int)char2;
            ascii1 = ascii1 << 8;
            int instruction = ascii1 & ascii2;
            setMemoryWord(instruction,loc);
            loc++;
        }
       setMemoryWord(0,loc);
   }
   
   public void setMemoryWord(int word, int loc){
       memory[loc] = word;
   }
   
   public void setMemoryWord(String Hexword, int loc){
      int int_value = Integer.parseInt(Hexword, 16); // line is changed from a hex string to an integer
      memory[loc] = int_value;
   }
  
}
