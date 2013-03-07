#Bison Processor

#Data Structurs/Concepts Used:
Stack, Arrays, Familiarity of Computer Organization, CPU Architecture  and Memory Hierarchy

#Output:

	max.exe
	Enter number between 1 - 100: 
	2
	
	Enter an integer:
	75
	
	Enter an integer:
	25
	
	The maximum value: 
	75
	
	min.exe
	Enter number between 1 - 100: 
	2
	
	Enter an integer:
	100
	
	Enter an integer:
	50
	
	The minimum value: 
	50
	
	nfact.exe
	Enter N:
	5
	Factorial of N: 
	120
	
	sum.exe
	Enter N:
	10
	Sum is: 
	45
	
	isprime.exe
	Enter a number between 1 - 32000: 
	5
	
	The number:
	5
	
	Is a prime number

####input.txt
	max.exe
	min.exe
	nfact.exe
	sum.exe
	isprime.exe


#Description:
####Specifications are included in p2_2011.doc
####ISA Architecture is included in lab1_2001.doc


This program is a Simulation of a Stack-based CPU with a mini Kernel for running batch programs written in the Stack-based ISA. 
The program reads in a series of .exe files from a batch file where the instructions are read and executed.   
The program is divided into 5 main classes. 'CPU.java', 'Instruction.java', 'Kernel.java', 'Memory.java', and 'Simulation.java'.     
#####Program Execution           
The simulation class holds the main method. When the program is run, the Simulation class creates instances of the Memory, CPU, and Kernel classes.
The Kernel class, via the load_jobs_from_batchFile(), then reads the instructions from the batch files, inserting them into an array of shorts, in the memory class. When the loading is done, the CPU.run method is called from within the loader. From within this Method, the fetch method is run. A line from the memory is read, decoded, and executed, until the program ends.
From this we will now go into each class with more detail.

####1 - The CPU Class:

The CPU class contains:
+	A stack which holds a maximum of 7 registers
+	A short which holds the program counter
+	An integer which holds the instruction register
+	The base and limit registers which are saved as shorts
+	An enum type for the mode
+	A short value for the system call status
+	And a long type variable for the clock.
+	Setter and Getter methods for these private variables
+	The run method, which controls the fetch, decode, and execution cycle
+	Decode method, which decodes an instruction from the instruction register
+	Execution method, which executes the instruction to the stack
+	The fetch method, loads a line of instruction from memory


#####1.1	Run Method
The run method is a loop of execution that begins after the instructions are read into memory. It is a loop that runs the fetch, the decode, and the execute methods, one after the other until the halt instruction, where it ends.

#####1.2 Fetch Method
The fetch method , using the PC counter value, retrieves a word of instructions from the memory class. It accomplishes this using the getMemoryWord(int loc), and the getMemoryString(int loc).	

#####1.3 The Decode Method
This method is purely responsible for fetching the value that is contained in the instruction register, and converting it into the appropriate code. Using the getIR instruction, the value of IR is saved into a variable called instruction.
+	The value is then copied into instruction type.
+	A shift of the instruction type variable is done to find the first bit.
+	If the instruction bit is equal to zero, then a sequence for zero-bit instructions is initiated.
+	Zero Bit Instructions:
	+	A copy of the original IR value is copied into an int variable called zero opcode 1
	+	A copy of the original IR value is copied into another int variable called zero opcode 2
	+	A shift of 8 bytes is performed on zero opcode 1 and its result is stored
	+	The resulting value is then masked with the value 0x001F to find the opcode
	+	A substring of the original hex line is also taken for storage
	+	A new instance of the instruction class is created
	+	The method that displays the instruction information is invoked.
	+	The program counter is increased by 1
	+	The second opcode variable zero opcode 2 is masked with 0x001F
	+	The second half of the original hex substring is taken and stored.
	+	A new instance of the instruction class is called, named zero instruction 2
	+	The method that displays the instruction information is invoked

+	Else, if the instruction bit is equal to one, then a sequence for one-bit instructions is initiated.

+	One Bit Instructions:
	+	A copy of the instruction is placed in an int variable called opcode
	+	A copy of the instruction is placed in an int variable called index_bit
	+	A copy of the instruction is placed in an int variable called ddr
	+	A shift of 10 bits to the right is performed on the opcode variable
	+	A mask of 0x001F is done on the result, saving the opcode in the opcode variable.
	+	The value in index bit is shifted to the right by 9 bits
	+	The resulting value in index bit is masked with 0x0001	
		+	If the resulting index bit is 0,
		+	We set the resulting bit variable to zero
		+	If the resulting index bit is 1
		+	We set the bit variable to one
	+	The  daddr is decoded by performing a mask of 0x007F on the daddr variable
	+	A new instance of one instruction is created using the instruction class
	+	The display one instruction method is invoked from the instruction class

#####1.4 Execute Instruction      
The execution instruction performs the operation on the stack, depending on the nature of the instruction. It uses an instance of an instruction that is fed into it.
	       
	           
	           
####2 - Instruction Class

This class represents an instruction in the bison processor. Instances generated from this class are the manifestations of instructions on the processor.  Each instruction (ZERO TYPE) has an Instr_type enum, and opcode. Each instruction (ONE TYPE) has an Intr_Type, Opcode, Index_bit enum, and daddr.
The instruction class contains as its private data members:
+	Instruction type(enum)
+	Index bit
+	Opcode
+	Daddr
+	Line
The class contains setter and getter methods. Methods of note are the displayzeroinstruction, which displays information specifically for zero bit instructions, and displayoneinstruction, which displays information specifically for a one bit instruction.


####3 - Kernel Class

The kernel class is responsible for loading the contents of the batch file into memory.  
The Class contains 
+	A reference to the memory class
+	A reference to the CPU class
+	A constructor with the file name, memory reference, and CPU reference as parameters
        
#####3.1 Kernel (String file, Memory mem, CPU c)        
The kernel constructs an instance of itself, with reference parameters to the Memory and CPU
	
#####3.2 load_jobs_from_batchfile(int addr)       
This class is responsible for reading the instructions from a batch file and assigning the instructions to memory. It performs this with the help of the setter methods such as
+	setMemoryWord(int loc)
+	SetMemoryString(String s, int loc)
+	setMemoryWord(String Hexword, int loc)
When the instructions from the programs are read, it executes the CPU. Run method.
Here is the sequence of events of how it is run:
+	Open batch file
	+	While !end of file do
	+	Read file name from batch file
		+	Open file from filename 
		+	While ! end of file do
			+	Begin loop
				+	Read instructions
				+	Set instruction to memory based on whether it is a word or hexstring			
		+	End loop
	+	End loop
+	run CPU.run () method


	             
####4 - Memory Class

The memory class represents the RAM or main memory.  It contains access to 256 words of memory. Each line holds 16 bits.
The Class Contains:
	short memory[]  //representation of the array, which contains shorts. Each short holds an instruction  
	
#####4.1 Memory(int size)    
This is a constructor. It creates an array of size size.  

#####4.2 Short getMemoryWord(int loc)	    
This returns the instruction from array location specified at index location 'loc'  

#####4.3 getMemoryString(int loc)    
This returns a string starting from location to the first occurrence to a null character   
+	While "Instruction read != null character"   
+	Begin loop    
+	Read in instruction     
+	Convert it to the appropriate character based on its ascii value    
+	End loop     

#####4.4 setMemoryWord(short word, int loc)                 
Assigns an instruction to the memory address array index based on the value of loc   
#####4.5 setMemoryString(String s, int loc)          
Converts at string from stdin, converts to hex, and places in "memory"   
#####4.6 setMemoryWord(String Hexword, int loc)                 
This method takes a hexadecimal string as a character and int address, converts the int into a short, and then assigns it to the memory at array index loc



####5 - Class Simulation    
        
This class is the main driver of the assignment. It contains the main method constructs instances for the Kernel, the Memory, and the CPU.     

#####5.1 Simulation.main()    
+	Constructs Memory   
+	Constructs Kernel   
+	Constructs CPU    
+	Calls Kernel.load_Jobs_From_BatchFile(int addr) //default address is zero     


	






	




