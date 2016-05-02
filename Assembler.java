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
import java.util.InputMismatchException;
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
	private String label;//labels for jumps
	private String type;//identifies each instruction type
	private String condition;//binary for condition bits
	private String operation;//The input string for possible operations
	private String binary;//The complete 24 bit instruction in binary
	private String tBit;//the T bit flag for immediate values

	//constructor for user input in console
	public Assembler(){
		this.opCode = " ";
		this.registerCode=" ";
		this.sBit = "0";
		this.immediate = "00000";
	

		//Double Op instruction table
		INSTRUCTION_OP.put("add", "00011");
		INSTRUCTION_OP.put("addal","00011");
		INSTRUCTION_OP.put("addnv", "00011");
		INSTRUCTION_OP.put("addeq", "00011");
		INSTRUCTION_OP.put("addne", "00011");
		INSTRUCTION_OP.put("addvs", "00011");
		INSTRUCTION_OP.put("addvc", "00011");
		INSTRUCTION_OP.put("addmi", "00011");
		INSTRUCTION_OP.put("addpl", "00011");
		INSTRUCTION_OP.put("addcs", "00011");
		INSTRUCTION_OP.put("addcc", "00011");
		INSTRUCTION_OP.put("addhi", "00011");
		INSTRUCTION_OP.put("addls", "00011");
		INSTRUCTION_OP.put("addgt", "00011");
		INSTRUCTION_OP.put("addlt", "00011");
		INSTRUCTION_OP.put("addge", "00011");
		INSTRUCTION_OP.put("addle", "00011");
		INSTRUCTION_OP.put("sub", "00100");
		INSTRUCTION_OP.put("subal", "00100");
		INSTRUCTION_OP.put("subnv", "00100");
		INSTRUCTION_OP.put("subeq", "00100");
		INSTRUCTION_OP.put("subne", "00100");
		INSTRUCTION_OP.put("subvs", "00100");
		INSTRUCTION_OP.put("subvc", "00100");
		INSTRUCTION_OP.put("submi", "00100");
		INSTRUCTION_OP.put("subpl", "00100");
		INSTRUCTION_OP.put("subcs", "00100");
		INSTRUCTION_OP.put("subcc", "00100");
		INSTRUCTION_OP.put("subhi", "00100");
		INSTRUCTION_OP.put("subls", "00100");
		INSTRUCTION_OP.put("subgt", "00100");
		INSTRUCTION_OP.put("sublt", "00100");
		INSTRUCTION_OP.put("subge", "00100");
		INSTRUCTION_OP.put("suble", "00100");
		INSTRUCTION_OP.put("and","00000");
		INSTRUCTION_OP.put("andal","00000");
		INSTRUCTION_OP.put("andnv","00000");
		INSTRUCTION_OP.put("andeq","00000");
		INSTRUCTION_OP.put("andne","00000");
		INSTRUCTION_OP.put("andvs","00000");
		INSTRUCTION_OP.put("andvc","00000");
		INSTRUCTION_OP.put("andmi","00000");
		INSTRUCTION_OP.put("andpl","00000");
		INSTRUCTION_OP.put("andcs","00000");
		INSTRUCTION_OP.put("andcc","00000");
		INSTRUCTION_OP.put("andhi","00000");
		INSTRUCTION_OP.put("andls","00000");
		INSTRUCTION_OP.put("andgt","00000");
		INSTRUCTION_OP.put("andlt","00000");
		INSTRUCTION_OP.put("andge","00000");
		INSTRUCTION_OP.put("andle","00000");
		INSTRUCTION_OP.put("or", "00001");
		INSTRUCTION_OP.put("oral", "00001");
		INSTRUCTION_OP.put("ornv", "00001");
		INSTRUCTION_OP.put("oreq", "00001");
		INSTRUCTION_OP.put("orne", "00001");
		INSTRUCTION_OP.put("orvs", "00001");
		INSTRUCTION_OP.put("orvc", "00001");
		INSTRUCTION_OP.put("ormi", "00001");
		INSTRUCTION_OP.put("orpl", "00001");
		INSTRUCTION_OP.put("orcs", "00001");
		INSTRUCTION_OP.put("orcc", "00001");
		INSTRUCTION_OP.put("orhi", "00001");
		INSTRUCTION_OP.put("orls", "00001");
		INSTRUCTION_OP.put("orgt", "00001");
		INSTRUCTION_OP.put("orlt", "00001");
		INSTRUCTION_OP.put("orge", "00001");
		INSTRUCTION_OP.put("orle", "00001");
		INSTRUCTION_OP.put("xor", "00010");
		INSTRUCTION_OP.put("xoral", "00010");
		INSTRUCTION_OP.put("xornv", "00010");
		INSTRUCTION_OP.put("xoreq", "00010");
		INSTRUCTION_OP.put("xorne", "00010");
		INSTRUCTION_OP.put("xorvs", "00010");
		INSTRUCTION_OP.put("xorvc", "00010");
		INSTRUCTION_OP.put("xormi", "00010");
		INSTRUCTION_OP.put("xorpl", "00010");
		INSTRUCTION_OP.put("xorcs", "00010");
		INSTRUCTION_OP.put("xorcc", "00010");
		INSTRUCTION_OP.put("xorhi", "00010");
		INSTRUCTION_OP.put("xorls", "00010");
		INSTRUCTION_OP.put("xorgt", "00010");
		INSTRUCTION_OP.put("xorlt", "00010");
		INSTRUCTION_OP.put("xorge", "00010");
		INSTRUCTION_OP.put("xorle", "00010");
		INSTRUCTION_OP.put("ld", "00101");
		INSTRUCTION_OP.put("ldal", "00101");
		INSTRUCTION_OP.put("ldnv", "00101");
		INSTRUCTION_OP.put("ldeq", "00101");
		INSTRUCTION_OP.put("ldne", "00101");
		INSTRUCTION_OP.put("ldvs", "00101");
		INSTRUCTION_OP.put("ldvc", "00101");
		INSTRUCTION_OP.put("ldmi", "00101");
		INSTRUCTION_OP.put("ldpl", "00101");
		INSTRUCTION_OP.put("ldcs", "00101");
		INSTRUCTION_OP.put("ldcc", "00101");
		INSTRUCTION_OP.put("ldhi", "00101");
		INSTRUCTION_OP.put("ldls", "00101");
		INSTRUCTION_OP.put("ldgt", "00101");
		INSTRUCTION_OP.put("ldlt", "00101");
		INSTRUCTION_OP.put("ldge", "00101");
		INSTRUCTION_OP.put("ldle", "00101");
		INSTRUCTION_OP.put("st", "00110");
		INSTRUCTION_OP.put("stal", "00110");
		INSTRUCTION_OP.put("stnv", "00110");
		INSTRUCTION_OP.put("steq", "00110");
		INSTRUCTION_OP.put("stne", "00110");
		INSTRUCTION_OP.put("stvs", "00110");
		INSTRUCTION_OP.put("stvc", "00110");
		INSTRUCTION_OP.put("stmi", "00110");
		INSTRUCTION_OP.put("stpl", "00110");
		INSTRUCTION_OP.put("stcs", "00110");
		INSTRUCTION_OP.put("stcc", "00110");
		INSTRUCTION_OP.put("sthi", "00110");
		INSTRUCTION_OP.put("stls", "00110");
		INSTRUCTION_OP.put("stgt", "00110");
		INSTRUCTION_OP.put("stlt", "00110");
		INSTRUCTION_OP.put("stge", "00110");
		INSTRUCTION_OP.put("stle", "00110");
		INSTRUCTION_OP.put("cmp", "00111");
		INSTRUCTION_OP.put("cmpal", "00111");
		INSTRUCTION_OP.put("cmpnv", "00111");
		INSTRUCTION_OP.put("cmpeq", "00111");
		INSTRUCTION_OP.put("cmpne", "00111");
		INSTRUCTION_OP.put("cmpvs", "00111");
		INSTRUCTION_OP.put("cmpvc", "00111");
		INSTRUCTION_OP.put("cmpmi", "00111");
		INSTRUCTION_OP.put("cmppl", "00111");
		INSTRUCTION_OP.put("cmpcs", "00111");
		INSTRUCTION_OP.put("cmpcc", "00111");
		INSTRUCTION_OP.put("cmphi", "00111");
		INSTRUCTION_OP.put("cmpls", "00111");
		INSTRUCTION_OP.put("cmpgt", "00111");
		INSTRUCTION_OP.put("cmplt", "00111");
		INSTRUCTION_OP.put("cmpge", "00111");
		INSTRUCTION_OP.put("cmple", "00111");


		//Single Op instruction table
		INSTRUCTION_OP.put("dec", "01000");
		INSTRUCTION_OP.put("decal", "01000");
		INSTRUCTION_OP.put("decnv", "01000");
		INSTRUCTION_OP.put("deceq", "01000");
		INSTRUCTION_OP.put("decne", "01000");
		INSTRUCTION_OP.put("decvs", "01000");
		INSTRUCTION_OP.put("decvc", "01000");
		INSTRUCTION_OP.put("decmi", "01000");
		INSTRUCTION_OP.put("decpl", "01000");
		INSTRUCTION_OP.put("deccs", "01000");
		INSTRUCTION_OP.put("deccc", "01000");
		INSTRUCTION_OP.put("dechi", "01000");
		INSTRUCTION_OP.put("decls", "01000");
		INSTRUCTION_OP.put("decgt", "01000");
		INSTRUCTION_OP.put("decge", "01000");
		INSTRUCTION_OP.put("decle", "01000");
		INSTRUCTION_OP.put("inc", "01001");
		INSTRUCTION_OP.put("incal", "01001");
		INSTRUCTION_OP.put("incnv", "01001");
		INSTRUCTION_OP.put("inceq", "01001");
		INSTRUCTION_OP.put("incne", "01001");
		INSTRUCTION_OP.put("incvs", "01001");
		INSTRUCTION_OP.put("incvc", "01001");
		INSTRUCTION_OP.put("incmi", "01001");
		INSTRUCTION_OP.put("incpl", "01001");
		INSTRUCTION_OP.put("inccs", "01001");
		INSTRUCTION_OP.put("inccc", "01001");
		INSTRUCTION_OP.put("inchi", "01001");
		INSTRUCTION_OP.put("incls", "01001");
		INSTRUCTION_OP.put("incgt", "01001");
		INSTRUCTION_OP.put("inclt", "01001");
		INSTRUCTION_OP.put("incge", "01001");
		INSTRUCTION_OP.put("incle", "01001");
		INSTRUCTION_OP.put("jr", "01101");
		INSTRUCTION_OP.put("jral", "01101");
		INSTRUCTION_OP.put("jrnv", "01101");
		INSTRUCTION_OP.put("jreq", "01101");
		INSTRUCTION_OP.put("jrne", "01101");
		INSTRUCTION_OP.put("jrvs", "01101");
		INSTRUCTION_OP.put("jrvc", "01101");
		INSTRUCTION_OP.put("jrmi", "01101");
		INSTRUCTION_OP.put("jrpl", "01101");
		INSTRUCTION_OP.put("jrcs", "01101");
		INSTRUCTION_OP.put("jrcc", "01101");
		INSTRUCTION_OP.put("jrhi", "01101");
		INSTRUCTION_OP.put("jrpl", "01101");
		INSTRUCTION_OP.put("jrcs", "01101");
		INSTRUCTION_OP.put("jrcc", "01101");
		INSTRUCTION_OP.put("jrhi", "01101");
		INSTRUCTION_OP.put("jrls", "01101");
		INSTRUCTION_OP.put("jrgt", "01101");
		INSTRUCTION_OP.put("jrlt", "01101");
		INSTRUCTION_OP.put("jrge", "01101");
		INSTRUCTION_OP.put("jrle", "01101");
		INSTRUCTION_OP.put("clr", "01100");
		INSTRUCTION_OP.put("clral", "01100");
		INSTRUCTION_OP.put("clrne", "01100");
		INSTRUCTION_OP.put("clreq", "01100");
		INSTRUCTION_OP.put("clrne", "01100");
		INSTRUCTION_OP.put("clrvs", "01100");
		INSTRUCTION_OP.put("clrvc", "01100");
		INSTRUCTION_OP.put("clrmi", "01100");
		INSTRUCTION_OP.put("clrpl", "01100");
		INSTRUCTION_OP.put("clrcs", "01100");
		INSTRUCTION_OP.put("clrcc", "01100");
		INSTRUCTION_OP.put("clrhi", "01100");
		INSTRUCTION_OP.put("clrls", "01100");
		INSTRUCTION_OP.put("clrgt", "01100");
		INSTRUCTION_OP.put("clrlt", "01100");
		INSTRUCTION_OP.put("clrge", "01100");
		INSTRUCTION_OP.put("clrle", "01100");

		//Jump instruction table
		INSTRUCTION_OP.put("j", "10000");
		INSTRUCTION_OP.put("jal", "10000");
		INSTRUCTION_OP.put("jnv", "10000");
		INSTRUCTION_OP.put("jeq", "10000");
		INSTRUCTION_OP.put("jne", "10000");
		INSTRUCTION_OP.put("jvs", "10000");
		INSTRUCTION_OP.put("jvc", "10000");
		INSTRUCTION_OP.put("jmi", "10000");
		INSTRUCTION_OP.put("jpl", "10000");
		INSTRUCTION_OP.put("jcs", "10000");
		INSTRUCTION_OP.put("jcc", "10000");
		INSTRUCTION_OP.put("jhi", "10000");
		INSTRUCTION_OP.put("jls", "10000");
		INSTRUCTION_OP.put("jgt", "10000");
		INSTRUCTION_OP.put("jlt", "10000");
		INSTRUCTION_OP.put("jge", "10000");
		INSTRUCTION_OP.put("jle", "10000");
		INSTRUCTION_OP.put("j.l", "00001");
		INSTRUCTION_OP.put("j.lal", "00001");
		INSTRUCTION_OP.put("j.lnv", "00001");
		INSTRUCTION_OP.put("j.leq", "00001");
		INSTRUCTION_OP.put("j.lne", "00001");
		INSTRUCTION_OP.put("j.lvs", "00001");
		INSTRUCTION_OP.put("j.lvc", "00001");
		INSTRUCTION_OP.put("j.lmi", "00001");
		INSTRUCTION_OP.put("j.lpl", "00001");
		INSTRUCTION_OP.put("j.lcs", "00001");
		INSTRUCTION_OP.put("j.lcc", "00001");
		INSTRUCTION_OP.put("j.lhi", "00001");
		INSTRUCTION_OP.put("j.lls", "00001");
		INSTRUCTION_OP.put("j.lgt", "00001");
		INSTRUCTION_OP.put("j.llt", "00001");
		INSTRUCTION_OP.put("j.lge", "00001");
		INSTRUCTION_OP.put("j.lle", "00001");


		//registers table r0-r15
		REGISTER_LOCATIONS.put("r0", "0000");
		REGISTER_LOCATIONS.put("r1", "0001");
		REGISTER_LOCATIONS.put("r2", "0010");
		REGISTER_LOCATIONS.put("r3", "0011");
		REGISTER_LOCATIONS.put("r4", "0100");
		REGISTER_LOCATIONS.put("r5", "0101");
		REGISTER_LOCATIONS.put("r6", "0110");
		REGISTER_LOCATIONS.put("r7", "0111");
		REGISTER_LOCATIONS.put("r8", "1000");
		REGISTER_LOCATIONS.put("r9", "1001");
		REGISTER_LOCATIONS.put("r10", "1010");
		REGISTER_LOCATIONS.put("r11", "1011");
		REGISTER_LOCATIONS.put("r12", "1100");
		REGISTER_LOCATIONS.put("r13", "1101");
		REGISTER_LOCATIONS.put("r14", "1110");
		REGISTER_LOCATIONS.put("r15", "1111");
	}

	//Converts inputs from binary into hexadecimal outputs
	public String getHex(){
		//TODO break up the hex sextion to find each individual hex string, hex values are incorrect currently
			setInstruction();
			int instruction = Integer.parseInt(this.binary,2);
			String hex = Integer.toHexString(instruction);


		return hex;
	}
	//Determines which operation is being used
	private void setType() throws BadInstructionException
	{
		System.out.println("setting type...");

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
	private void setInstruction()
	{
		System.out.println("concatonating the instruction...");
		//determine if we need to change the tBit
		if(this.immediate.equals("00000")|| this.immediate == null){
			this.tBit = "0";
		}
		else if(this.immediate.contains("1")){
			setT();	
		}

		if(this.condition.equals("cmp")){
			setSbit();
		}
		else{
			this.sBit = "0";
		}

		switch(this.type){
		case "d": 
			this.binary = this.opCode + this.condition + this.sBit + this.src
			+ this.dst + this.immediate + this.tBit;
			break;
		case "s":
			this.binary = this.opCode + this.condition + this.sBit + this.src
			+PADDING;
			break;
		case "j":
			this.binary = this.opCode + this.condition + this.label;
		}
		

	}
	//converts the immediate to binary for user input
	public void setImmediate(int immediate) throws OverFlowException 
	{ 
		
			if(immediate > 15){
				throw new OverFlowException("immediate too large");

			}
			else if(immediate < -16){
				throw new OverFlowException("immediate too small");
			}
			else{
				String im =Integer.toBinaryString(immediate);
				this.immediate = String.format( "%5s", im).replace(' ', '0');

			}

	}
	//sets the label to the desired pc value for jump instructions.
	public void setLabel(int pc){
		String lbl = "";
		if(pc>=0){
			lbl = Integer.toBinaryString(pc);
			this.label =  String.format("%15s", lbl).replace(' ', '0');
		}
		else{
			lbl = Integer.toBinaryString(pc -1);
			this.label = lbl.substring(lbl.length()-15);
			
		}

	}
	//Sets the S bit if an instruction requires it
	public void setSbit()
	{
		this.sBit = "1"; //sets the s bit to one when necessary
	}
	//for user input register setting, takes source and destination from driver class
	public void setRegister(String source, String destination) throws RegisterNotFoundException, NumberFormatException, OverFlowException{
		
		//these two if statements should be handling offset values
		
		if(this.type.equals("d")){
		
				this.src = (String) REGISTER_LOCATIONS.get(source);
				this.dst = (String) REGISTER_LOCATIONS.get(destination);
			
		}
		else if(this.type.equals("s")){
			
				this.src = (String) REGISTER_LOCATIONS.get(source);
			
			
				
			
		}
		else if(destination.equals("r0")){ //Make sure this matches expected outputs to check source and destination errors
			throw new RegisterNotFoundException("This register is not accessble in this context");
		}
		else{
			throw new RegisterNotFoundException("This register does not exist or can't currently be accessed");
		}


	}
	//set register for single ops
	public void setRegister(String source){
		this.src = (String) REGISTER_LOCATIONS.get(source);
	}
	//set condition for file
	private void setCondition() throws BadInstructionException{
		String cond = " ";
		if(operation.length()>=3)
			cond = this.operation.substring(this.operation.length()-2);//possible indexing issues here, be sure to check

		System.out.println("setting the condition bits...");

		switch(cond){
		case "al": this.condition = "0000";
		break;
		case "nv": this.condition = "0001";
		break;
		case "eq": this.condition = "0010";
		break;
		case "ne": this.condition = "0011";
		break;
		case "vs": this.condition = "0100";
		break;
		case "vc": this.condition = "0101";
		break;
		case "mi": this.condition = "0110";
		break;
		case "pl": this.condition = "0111";
		break;
		case "cs": this.condition = "1000";
		break;
		case "cc": this.condition = "1001";
		break;
		case "hi": this.condition = "1010";
		break;
		case "ls": this.condition = "1011";
		break;
		case "gt": this.condition = "1100";
		break;
		case "lt": this.condition = "1101";
		break;
		case "ge": this.condition = "1110";
		break;
		case "le": this.condition = "1111";
		break;
		default: this.condition = "0000";

		}

	}

	//sets the instruction operation for user input
	public void setOperation(String op) throws NumberFormatException, OverFlowException, BadInstructionException{


	
			this.operation = op;
			this.opCode = (String)INSTRUCTION_OP.get(this.operation);
			if(opCode == null){
				throw new BadInstructionException("That instruction does not exist");
			}
			setType();
			setCondition();


		

	}
	private void setT(){
		this.tBit = "1";
	}
	public String getType(){
		return this.type;
	}
	public int getImmediate(){
		int immval = Integer.parseInt(this.immediate,2);
		return immval;
	}
}
