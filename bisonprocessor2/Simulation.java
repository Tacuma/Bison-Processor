/*
 * Program 2 
 * Tacuma Solomon
 * SYCS 401 - Operating Systems (Fall 2011)
 * Dr. Legand Burge
 *  This program is a simulator of the Bison Processor2. 
 */

package bisonprocessor2;

import java.io.IOException;

/**
 *
 * @author TaKuma
 */
public class Simulation {
    public static void main(String[] args) throws IOException{
         Memory Bisonmemory = new Memory(256);
         CPU BisonCPU = new CPU(0,0,0,0,0,Bisonmemory);
         Kernel Bisonkernel= new Kernel("input2.txt", Bisonmemory, BisonCPU);
         Bisonkernel.load_Jobs_From_BatchFile(0);
    }
}
