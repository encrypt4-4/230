/*
 * This program assembles Assembly code to binary/hex numbers and writes them to a MIP file to be used in Team 9's project
 * 
 * @file Assembler.java
 * @author Ja'lon Clark, Val Hahn, Derek Vogel
 * @version 1.0
 */

import java.util.Scanner;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.io.*;
import java.lang.*;
import java.util.Dictionary;

public class Assembler {
	//TODO create new exceptions to throw for invalid instructions
	//Converts inputs from decimal into binary

	private final Dictionary INSTRUCTION_OP = new Hashtable();//dictionary for single ops
	private final Dictionary REGISTER_LOCATIONS = new Hashtable();//dictionary for registers
	private final String PADDING = "0000000000";//for unused bits on single ops
	private String opCode;//Instruction Op codes
	private String registerCode;//register files input
	private String hex;//instruction output in hex
	private String instruction[];//instruction input in assembly
	private String sBit;//s bit flag
	private String src;//source register
	private String dst;//destination register
	private String immediate;//immediate values if used
	private String label;//lables for jumps
	private String type;//identifies eachi instruction type


	public Assembler(String[] inst)
	{
		this.opCode = " ";
		this.registerCode=" ";
		this.sBit = "0";
		this.instruction = inst; //potential errors
		/*TODO modify the tables to use strings and set conditions
		 * using .contains to allow for flag setting using s bit.
		 */

		//Double Op instruction table
		INSTRUCTION_OP.put("add", "00011");
		INSTRUCTION_OP.put("sub", "00100");
		INSTRUCTION_OP.put("and","00000");
		INSTRUCTION_OP.put("or", "00001");
		INSTRUCTION_OP.put("xor", "00010");
		INSTRUCTION_OP.put("ld", "00101");
		INSTRUCTION_OP.put("st", "00110");

		//Single Op instruction table
		INSTRUCTION_OP.put("dec", "01000");
		INSTRUCTION_OP.put("inc", "01001");
		INSTRUCTION_OP.put("jr", "01101");
		INSTRUCTION_OP.put("clr", "01100");

		//Jump instruction table
		INSTRUCTION_OP.put("jeq", "10000");
		INSTRUCTION_OP.put("jl", "00001");
		
		//registers table r0-r15
		REGISTER_LOCATIONS("r0", "0000");
		REGISTER_LOCATIONS("r1", "0001");
		REGISTER_LOCATIONS("r2", "0010");
		REGISTER_LOCATIONS("r3", "0011");
		REGISTER_LOCATIONS("r4", "0100");
		REGISTER_LOCATIONS("r5", "0101");
		REGISTER_LOCATIONS("r6", "0110");
		REGISTER_LOCATIONS("r7", "0111");
		REGISTER_LOCATIONS("r8", "1000");
		REGISTER_LOCATIONS("r9", "1001");
		REGISTER_LOCATIONS("r10", "1010");
		REGISTER_LOCATIONS("r11", "1011");
		REGISTER_LOCATIONS("r12", "1100");
		REGISTER_LOCATIONS("r13", "1101");
		REGISTER_LOCATIONS("r14", "1110");
		REGISTER_LOCAtiONS("r15", "1111");

	}

	//Converts inputs from binary into hexadecimal outputs
	public String getHex(){
		//TODO implement switch cases for the three kinds of operatiosn
		String hex = "0x00000";
		String binary = "0000";
		String op = "00";
		//TODO implement conversion from binary values to hex values
		return hex;
	}
	//Determines which operation is being used
	public void setType(String opCode) throws BadInstructionException
	{

		switch(this.opCode.substring(0,2))
		{
		case "00":
		{
			this.type = "d";
			break;
		}
		case "01":
		{
			this.type = "s";
			break;
		}
		case "10": 
		{
			this.type = "j"; 
			break;
		}
		default: 
		{
			throw new BadInstructionException("noncompatable type");
		}

		}

	}
	//takes the instruction input
	public void setInstruction()
	{
		//TODO look up hash map usage instructions to retrieve hash value and set opcode
		

	}
	//Sets the S bit if an instruction requires it
	public void setSbit()
	{
		this.sBit = "1"; //sets the s bit to one when necessary
	}
	//set the register bits if necessary from instruction
	public void setRegisters()
	{
		switch(this.type)
		{
		case "d":
		{
			
		}
		case "s":
		{
			//TODO set register for single operation, locate immediate
		}
		case "j":
			{
				
			}
		default:
		}

	}
}
