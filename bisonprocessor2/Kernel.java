/*
 * Program 2 
 * Tacuma Solomon
 * SYCS 401 - Operating Systems (Fall 2011)
 * Dr. Legand Burge
 *  This program is a simulator of the Bison Processor2. 
 */

package bisonprocessor2;
import java.io.*;

public class Kernel {
    
    /* The Kernel class loads the batch file that contains the file name of a given program
     * written in the Stack-based instruction set language. The Kernel loads one job at a time
     * into memory, starting at address addr; default address is zero. Once the entire program is 
     * loaded into memory, the kernel will call the CPU.run() method to start processing the code
     */
    
    
    private String file;
    private Memory memoryRef;
    private CPU cpuRef;
    
    Kernel(){ 
    }
    
    Kernel(String file, Memory mem, CPU c){
        this.file = file;
        memoryRef=mem;
        cpuRef=c;
    }
    
    void load_Jobs_From_BatchFile(int addr) throws IOException{
        String line;
        String innerline;
        FileReader instream = null;
        File infile = new File(file);
        instream = new FileReader(infile);
        BufferedReader input = new BufferedReader(instream);                //sets up the string buffer
        while(( line = input.readLine()) != null ){                         // while file is not empty
                addr = 0;
                System.out.println(line);
                File infile2 = new File(line);
                FileReader instream2 = new FileReader(infile2);
                BufferedReader input2 = new BufferedReader(instream2);
                while(( innerline = input2.readLine()) != null ){           // while file is not empty
                      memoryRef.setMemoryWord(innerline, addr);
                      if (addr>255)
                          break;
                      addr++;
                }
                cpuRef.run();
                memoryRef.clearMemory();
                System.out.println("");
        }
    }
}
