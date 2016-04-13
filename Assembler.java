/*
 * This program assembles Assembly code to binary/hex numbers and writes them to a MIP file to be used in Team 9's project
 * 
 * @file Assembler.java
 * @author Ja'lon Clark, Val Hahn, Derek Vogel
 * @version 1.0
 */

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
public class Assembler {
	//TODO create new exceptions to throw for invalid instructions
	//Converts inputs from decimal into binary

	public int BinaryConverter(int val){
		int binary = 0000;
		// TODO implement conversion from decimal values to binary values
		return binary;
	}
	//Converts inputs from binary into hexadecimal outputs
	public String HexConverter(int val){
		String hex = "0x00000";
		//TODO implement conversion from binary values to hex values
		return hex;
	}
	//Single Op Mapping/Dictionary
	public int SingleOpBinary(String instruction){
		int binary = 0000;
		// TODO Use instruction from assembly to find op code
		return binary;
	}
	//Double Op Mapping/Dictionary
	public int DoubleOpBinary(String instruction){
		int binary = 0000;
		//TODO Use instruction from assembly file to find op code
		return binary;
	}
	//Jump Op Mapping/Dictionary
	public int  JumpOpBinary(String instruction){
		int binary =0000;
		
		return binary =0000;
	}
	
}
