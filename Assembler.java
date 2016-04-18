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

	private final Dictionary SINGLE_OP = new Hashtable();//dictionary for single ops
	private final Dictionary DOUBLE_OP = new Hashtable();//dictionary for double ops
	private final Dictionary JUMP_OP = new Hashtable();//dictionary for jumbs
	private final Dictionary REGISTER_LOCATIONS = new Hashtable();//dictionary for registers
	private final String PADDING = "0000000000";//for unused bits on single ops
	private String opCode;//Instruction Op codes
	private String registerCode;//register files input
	private String hex;//instruction output in hex
	private String instruction;//instruction input in assembly
	private String sBit;//s bit flag
	private String src;//source register
	private String dst;//destination register
	private String immediate;//immediate values if used
	private String label;//lables for jumps
	private String type;//identifies eachi instruction type


	public Assembler()
	{
		this.opCode = " ";
		this.registerCode=" ";
		this.sBit = "0";
		/*TODO modify the tables to use strings and set conditions
		 * using .contains to allow for flag setting using s bit.
		 */

		//Double Op instruction table
		DOUBLE_OP.put("add", "00011");
		DOUBLE_OP.put("sub", "00100");
		DOUBLE_OP.put("and","00000");
		DOUBLE_OP.put("or", "00001");
		DOUBLE_OP.put("xor", "00010");
		DOUBLE_OP.put("ld", "00101");
		DOUBLE_OP.put("st", "00110");

		//Single Op instruction table
		SINGLE_OP.put("dec", "01000");
		SINGLE_OP.put("inc", "01001");
		SINGLE_OP.put("jr", "01101");
		SINGLE_OP.put("clr", "01100");

		//Jump instruction table
		SINGLE_OP.put("jeq", "10000");
		SINGLE_OP.put("jl", "00001");

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
	public void setType(String instruction) throws BadInstructionException
	{

		switch(instruction.substring(0,2))
		{
		case "00":{
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
	public void setInstruction(String inst)
	{
		

	}
	//Sets the S bit if an instruction requires it
	public void setSbit()
	{
		this.sBit = "1"; //sets the s bit to one when necessary
	}
	//set the register bits if necessary from instruction
	public void setRegisters(String instruction)
	{
		switch(this.type)
		{
		case "d":
		{
			//TODO set registers for double operation instructions
		}
		case "s":
		{
			//TODO set register for single operation, locate immediate
		}
		default:
		}

	}
}
