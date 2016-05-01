import java.util.Scanner;

/*
 * Executes the Assembler class's methods.
 * 
 *  @file AssemblerDriver
 *  @authors Ja'lon Clark, Val Hahn, Derek Vogel
 *  @Version 1.0
 */
public class AssemblerDriver {
	private final String file_Extension = ".MIP";


	public static void main(String[] args) {
		/* TODO create a switch to help determine which type of operation to encode
		 * Driver should handle all file operations such as taking in the instruction word
		 * Splitting off the registers for the destinations. Will also handle all parsing
		 */
		boolean valid = true;
		Assembler assemble = new Assembler();
		Scanner instruction = new Scanner(System.in);
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


					try{
						switch(assemble.getType()){

						case "d":
							System.out.println("Please enter a source register (r2, r6, etc)");
							String source = instruction.next().toLowerCase();

							System.out.println("Please enter a destination register (r2, r6, etc)");
							String destination = instruction.next().toLowerCase();

							assemble.setRegister(source, destination);

							int immediate = 0;
							if(assemble.getImmediate() == 0){
								System.out.println("Please enter an immediate value(0, 5, etc.)");
								immediate = instruction.nextInt();
								assemble.setImmediate(immediate);
							}
							break;
						case "s":
							System.out.println("Please enter a source register (r2, r6, etc)");
							String source2 = instruction.next().toLowerCase();

							System.out.println("Please enter a destination register (r2, r6, etc)");
							String destination2 = source2; //error prevention, currently doesn't allow destination to be null

							assemble.setRegister(source2, destination2);
							break;
						case "j":
							System.out.println("Please enter which pc location you would like to jumb back to(0, 5, etc)");
							int pc = instruction.nextInt();
							assemble.setLabel(pc);
							break;
						default:
							valid2 = false;
							System.out.println("your instrucction was not valid");
							continue;
						}
					}
					catch(Exception e){
						System.out.println("something went wrong with setting the register, please try again!");
						valid2 = false;
						continue;

					}

					//phase 3 of assembling code, concatonating the instruction binary and converting to hex
					if(valid2){
						String hex;
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

					//TODO implement phase 4 writing to a mip file

				}while(!valid2);
			}
		}while(!valid);
		instruction.close();
	}


}
