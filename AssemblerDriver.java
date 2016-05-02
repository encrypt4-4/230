import java.util.InputMismatchException;
import java.util.Scanner;

import javax.print.attribute.standard.Destination;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
/*
 * Executes the Assembler class's methods.
 * 
 *  @file AssemblerDriver
 *  @authors Ja'lon Clark, Val Hahn, Derek Vogel
 *  @Version 1.0
 */
public class AssemblerDriver {
	private final String file_Extension = ".MIP";


	public static void main(String[] args) throws BadInstructionException, FileNotFoundException {
		/* Driver should handle all file operations such as taking in the instruction word
		 * Splitting off the registers for the destinations. Will also handle all parsing
		 */

		boolean valid = true;//checks the validity of user input
		Assembler assemble = new Assembler();//intialize assembler class
		Scanner instruction = new Scanner(System.in);//scanner to take operation
		String choose;//variable that takes the user input to determine if the program should loop for another instruction
		int line = 0;//counter to track which instruction panel the hex should be input into
		String hex = null;//transfer variable to give to the print writer
		Writer mif = new PrintWriter(new File("data/MemoryInitialization.mif"));//writer for mif file

		try{
			mif.write("WIDTH = 24; \nDEPTH = 1024; \n\n");
			mif.append("ADDRESS_RADIX=UNS; \nDATA_RADIX=HEX; \n\n");
			mif.append("CONTENT BEGIN \n");
			mif.append("0 \t:\t000000;\n");
		}
		catch(Exception e){
			System.out.println("Fatal Error. Could not create MIF file." );
			e.printStackTrace();
			instruction.close();
			System.exit(0);

		}

		do{
			line ++;

			do{
				System.out.println("Please type which operation you would liked to use (add, sub, etc.)");
				//phase 1 of assembling the instruction, setting the operation from here the type is determined and the conditions
				try{
					assemble.setOperation(instruction.next().toLowerCase());
				}
				catch (Exception e){
					valid = false;
					System.out.println("operation entry error please try again!");
					continue;
				}

				//phase 2 setting source/destination/label depending on the operation type
				boolean valid2 = true;
				if(valid){
					do{
						int immediate = 0;
						String source = "";
						String destination = "";


						try{
							switch(assemble.getType()){

							case "d":
								System.out.println("Please enter a source register (r2, r6, etc)");
								instruction.nextLine();
								source = instruction.nextLine().toLowerCase();
								

								System.out.println("Please enter a destination register (r2, r6, etc)");
								destination = instruction.nextLine().toLowerCase();
								
								
								//handling offset values
								if(source.length()>2){
								if(source.charAt(2) == '('){
									source = source.substring(3,source.length()-1);
									immediate = Integer.parseInt(source.substring(0,2));
									assemble.setImmediate(immediate);
								}
								}
								
								if(destination.length()>2){
								if(destination.charAt(2) == '('){
									destination = destination.substring(3,destination.length()-1);
									immediate = Integer.parseInt(destination.substring(0,2));
									assemble.setImmediate(immediate);
								}
								}
								
								

								assemble.setRegister(source, destination);

								
								
									System.out.println("Please enter an immediate value(0, 5, etc.)");
									immediate = instruction.nextInt();
									assemble.setImmediate(immediate);
								
								break;
							case "s":
								System.out.println("Please enter a source register (r2, r6, etc)");
								String source2 = instruction.next().toLowerCase();
								assemble.setRegister(source2);
								break;
							case "j":
								System.out.println("Please enter which pc location you would like to jumb back to(0, 5, etc)");
								int pc = instruction.nextInt();
								assemble.setLabel(pc);
								break;
							default:
								valid2 = false;
								System.out.println("your instruction was not valid");
								continue;
							}
						}
						catch(Exception e){
							System.out.println(destination);
							System.out.println("something went wrong with setting the register, please try again!");
							valid2 = false;
							continue;

						}

						//phase 3 of assembling code, concatonating the instruction binary and converting to hex
						if(valid2){

							try{
								hex = assemble.getHex();
							}
							catch(Exception e){
								
								System.out.println("something went wrong, please review the errors then try again later");
								instruction.close();
								System.out.println("fatal errors, shutting down....");
								System.exit(0);
							}
						}




					}while(!valid2);//end register loop
				}
			}while(!valid);//end operation entry loop

			System.out.println("writing to mif file...");

			//final error check
			if(hex.length()>6 ){
				instruction.close();
				throw new BadInstructionException("We can't write this instruction for some reason: \n \t Fatal Error Shutting down");
			}

			try {
				mif.append(line + "\t:\t" + hex +";\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("would you like to enter another instruction? ('y' for yes any other button for no)");
			choose = instruction.next();

		}while(choose.equalsIgnoreCase("y"));//end multiple instruction entry loop

		try {
			line++;
			mif.append("[" + line +"..1023]  :  000000;\n");
			mif.append("END;");
			mif.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		instruction.close();
	}//end main


}//end class
