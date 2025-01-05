
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class MonopolyClient {
	
	public static void main(String[] args) throws FileNotFoundException  {

		//Setting Up Inputs
		File inputFile = new File ("MonopolyData.txt");
		Scanner fileInput = new Scanner(inputFile);
		Scanner input = new Scanner(System.in);
		
		//Setting Up Board
		Spaces[] board = new Spaces[40];
		for (int i = 0; i < board.length; i++) {
			String eachLine = fileInput.nextLine();
			String[] eachLineArr = eachLine.split(",");
			if (eachLineArr.length > 3) {
				int index = Integer.parseInt(eachLineArr[0]);
				String propertyName = eachLineArr[1];
				int cost = Integer.parseInt(eachLineArr[2]);
				int housePrice = Integer.parseInt(eachLineArr[3]);
				int[] rent = new int[6];
				for (int j = 0; j < rent.length; j++) {
					rent[j] = Integer.parseInt(eachLineArr[j+4]);
				}
				board[i] = new Spaces(index, propertyName, cost, housePrice, rent);
			} else if (eachLineArr.length > 2) {
				int index = Integer.parseInt(eachLineArr[0]);
				String propertyName = eachLineArr[1];
				int cost = Integer.parseInt(eachLineArr[2]);
				board[i] = new Spaces(index, propertyName, cost);
			} else {
				int index = Integer.parseInt(eachLineArr[0]);
				String propertyName = eachLineArr[1];
				board[i] = new Spaces(index, propertyName);
			}
		}
		
		//Setting Up Game
		System.out.print("Welcome to online Monopoly. How many people will be playing: ");
		Players[] tempPlayers = new Players[input.nextInt()];
		input.nextLine();
		for (int i = 0; i < tempPlayers.length; i++) {
			System.out.print("What is Player "+(i+1)+"'s name: ");
			String name = input.nextLine();
			tempPlayers[i] = new Players(name);
		}
		System.out.println("\nYou are so close to being able to play, but first, the order which everyone goes in needs to be decided.\nTo do this, everyone with roll the dice and will go in order of descending value.\n");
		for (Players play: tempPlayers) {
			int roll1 = roll();
			int roll2 = roll();
			play.move(roll1, roll2);
			System.out.println(play.getName()+" rolled a "+roll1+" and a "+roll2+" for a total of "+(roll1+roll2));
		}
		Players[] players = new Players[tempPlayers.length];
		for (int i = 0; i < players.length; i++) {
			if (tempPlayers.length == 1) {
				players[i] = tempPlayers[0];
				players[i].setSpace(0);
			} else {
				int max = -100;
				Players[] temp = tempPlayers;
				tempPlayers = new Players[temp.length-1];
				for (Players play: temp) {
					if (play.getSpace() > max) {
						max = play.getSpace();
						players[i] = play;
					}
				}
				players[i].setSpace(0);
				int index = 0;
				for (int j = 0; j < temp.length; j++) {
					if (players[i]!= temp[j]) {
						tempPlayers[index] = temp[j];
						index++;
					}
				}
			}	
		}
		tempPlayers = null;
		System.out.print(players[0].getName()+" will go first, ");
		for (int i = 1; i < players.length; i++) {
			if (i == players.length-1)
				System.out.print("and finally "+ players[i].getName()+" will go last.");
			else
				System.out.print(players[i].getName()+" next, ");
		}
		System.out.println("\n");
		
		//Main Game
		boolean gameOver = false;
		while (!gameOver) {
			for(Players player: players) {
				int doubles = 0;
				for (int i = 0; i <= doubles; i++) {
					System.out.print(player.getName()+ " what would you like to do (1: Roll the Dice or 2: Check Player Summary): ");
					int decision = input.nextInt();
					if (decision == 2)
						System.out.println(player);
					int roll1 = roll();
					int roll2 = roll();
					String name = player.getName();
					System.out.print(name+" rolled a "+roll1+" and a "+roll2+".");
					if(roll1 == roll2) {
						if (player.getJail()>-1){
							break;
						}
						doubles++;
					}
					if(doubles == 3) {
						player.inJail();
						System.out.println(name+" has rolled 3 doubles so they are now in Jail.");
						break;
					}else {
						moved(name, roll1, roll2, player, input, board);							
					}
					if (player.getMoney()<0) {
						System.out.println(player.getName()+" has gone broke. They have lost.");
						gameOver = true;
						break;
					}
				}
				if (gameOver)
					break;
			}
		}
		int max = -1;
		Players winner = null;
		for (Players player: players) {
			if (player.getMoney()>max) {
				max = player.getMoney();
				winner = player;
			}
		}
		System.out.println(winner.getName()+" has the most money. He has won.");
		fileInput.close();
		input.close();
	}

	public static int roll() {
		return (int)Math.round(Math.random()*5+1);
	}
	
	public static void moved(String name, int roll1, int roll2, Players player, Scanner input, Spaces[] board) {
		//Jail
		if (player.getJail()>-1)
			if (roll1 == roll2) {
				player.outJail();
				System.out.println(" "+player.getName()+ " has rolled a double. They are now free and has been moved to Just Visiting");
			} else {
				player.serveTime();
				if (player.getJail()==3) {
					player.outJail();
					player.gainMoney(-50);
					System.out.println(" "+player.getName()+" has unfortunately not rolled a double. They have served their time so they will now be released for the low, low price of $50.");
				}else	
					System.out.println(" "+player.getName()+" has unfortunately not rolled a double. They will continue to serve out their time. They have "+(3-player.getJail())+" more rounds to serve.");
			
			//Not Jail
			}else {
				System.out.println(" They have moved forward "+(roll1+roll2)+" spaces. ");
				player.move(roll1, roll2);
				System.out.println(name+" has moved to "+board[player.getSpace()].getName()+". ");
		
				//Corner Spaces
				if(player.getSpace()%10 == 0) {
					if(player.getSpace() == 30) {
						System.out.println(name + " has committed a crime and therefore, he has been sent to jail.");
						player.inJail();
					} else if (player.getSpace() != 0)
						System.out.println("Nothing happened, they get to chillax for a round.");
			
				//Chance & Community Chest
				}else if(player.getSpace() == 7||player.getSpace() == 22||player.getSpace() == 36) {
					System.out.print("They pull a card: ");
					int oldSpace = player.getSpace();
					player.chance();
					if(oldSpace != player.getSpace() && player.getSpace() != 10)
						moved(name, player, input, board);
				}else if (player.getSpace() == 2||player.getSpace() == 17||player.getSpace() == 33) {
					System.out.print("They pull a card: ");
					int oldSpace = player.getSpace();
					player.communityChest();
					if(oldSpace != player.getSpace() && player.getSpace() != 10)
						moved(name, player, input, board);
			
				//Taxes
				}else if (player.getSpace() == 4) {
					System.out.println("They pay $200 of income tax.");
					player.gainMoney(-200);
				}else if (player.getSpace() == 38) {
					System.out.println("They pay $100 of luxury tax.");
					player.gainMoney(-100);
			
				//Normal Spaces
				}else {
					Spaces currentSpace = board[player.getSpace()];
			
				//Already Bought Space
					if (currentSpace.getBought()) {
						
						//Own Space
						if (currentSpace.getOwner() == player) {
							if(player.getSpace()%10 == 5 || player.getSpace()==12|| player.getSpace()==28)
								System.out.println("They are currently on their own property so they can chillax for the round.");
							else {
								System.out.print(name+" currently has $"+player.getMoney()+". Getting a house on "+currentSpace.getName()+" costs $"+currentSpace.getHousePrice()+".\nDo you wish to purchase a house(Y or N): ");
								String answer = input.next().toUpperCase();
								input.nextLine();
								if (answer.equals("Y")||answer.equals("YES")) {
									if (player.getMoney() < currentSpace.getPrice()) {
										System.out.println("Sorry, you do not have enough money so you are unable to purchase a house.");
									}else {
										currentSpace.buyHouse();
										System.out.println(name+ " has paid $"+currentSpace.getHousePrice()+" to purchase a house. They now have $"+player.getMoney());
									}
								} else {
									System.out.println(name+" has chosen to be a cheapstake, saving $"+currentSpace.getHousePrice());
								}
							}
						
						//Pay Rent
						}else {
							if(currentSpace.getOwner().getJail()>-1) {
								System.out.println(name+" has landed on "+currentSpace.getOwner().getName()+"'s property but they are in Jail so no rent is needed.");
							}else {
								if(player.getSpace()%10 == 5) {
									System.out.println("They have paid "+currentSpace.getOwner().getName()+" $"+currentSpace.payRailroads(player));
								} else if(player.getSpace()==12||player.getSpace()==28) {
									System.out.println("They have paid "+currentSpace.getOwner().getName()+" $"+currentSpace.payUtilities(player, roll1, roll2));
								}else {
									System.out.println("They have paid "+currentSpace.getOwner().getName()+" $"+currentSpace.getRent());
									currentSpace.payRent(player);
								}
							}
						}
				
				//Buying Property
					} else {
						System.out.print(name+" currently has $"+player.getMoney()+". "+currentSpace.getName()+" costs $"+currentSpace.getPrice()+" to buy.\nDo you wish to purchase this property(Y or N): ");
						String answer = input.next().toUpperCase();
						input.nextLine();
						if (answer.equals("Y")||answer.equals("YES")) {
							if (player.getMoney() < currentSpace.getPrice()) {
								System.out.println("Sorry, you do not have enough money so you are unable to buy this property.");
							}else {
								if(player.getSpace()%10 == 5)
									player.boughtRailroad(currentSpace);
								else if(player.getSpace()==12||player.getSpace()==28)
									player.boughtUtility(currentSpace);
								else
									player.boughtProperty(currentSpace);
								System.out.println(name+ " has paid $"+currentSpace.getPrice()+" to purchase "+currentSpace.getName()+". They now have $"+player.getMoney());
							}
						} else {
							System.out.println(name+" has chosen to be a cheapskate, saving $"+currentSpace.getPrice());
						}
					}
				}
			}
		System.out.println();
	}
	
	public static void moved(String name, Players player, Scanner input, Spaces[] board) {
		System.out.print(name+" has moved to "+board[player.getSpace()].getName()+". ");
		
		//Corner Spaces
		if(player.getSpace()%10 == 0) {
			if(player.getSpace() == 30) {
				System.out.println(name + " has committed a crime and therefore, he has been sent to jail.");
			} else if (player.getSpace() != 0)
				System.out.print("Nothing happened, they get to chillax for a round.");
			
		//Taxes
		}else if (player.getSpace() == 4) {
			System.out.println("They pay $200 of income tax.");
			player.gainMoney(-200);
		}else if (player.getSpace() == 38) {
			System.out.println("They pay $100 of luxury tax.");
			player.gainMoney(-100);
			
		//Normal Spaces
		}else {
			Spaces currentSpace = board[player.getSpace()];
			
			//Own Property
			if (currentSpace.getBought()) {
				if (currentSpace.getOwner() == player) {
					if(player.getSpace()%10 == 5 || player.getSpace()==12|| player.getSpace()==28)
						System.out.println("They are currently on their own property so they can chillax for the round.");
					else {
						System.out.print(name+" currently has $"+player.getMoney()+". Getting a house on "+currentSpace.getName()+" costs $"+currentSpace.getHousePrice()+".\nDo you wish to purchase a house(Y or N): ");
						String answer = input.next().toUpperCase();
						input.nextLine();
						if (answer.equals("Y")||answer.equals("YES")) {
							if (player.getMoney() < currentSpace.getPrice()) {
								System.out.println("Sorry, you do not have enough money so you are unable to purchase a house.");
							}else {
								currentSpace.buyHouse();
								System.out.println(name+ " has paid $"+currentSpace.getHousePrice()+" to purchase a house. They now have $"+player.getMoney());
							}
						} else {
							System.out.println(name+" has chosen to not buy a house, saving $"+currentSpace.getHousePrice());
						}
					}
					
				//Paying Rent
				}else {
					if(player.getSpace()%10 == 5) {
						System.out.println("They have paid "+currentSpace.getOwner().getName()+" $"+currentSpace.payRailroads(player));
					} else if(player.getSpace()==12||player.getSpace()==28) {
						int roll1 = roll();
						int roll2 = roll();
						System.out.println("They have rolled a "+roll1+" and a "+roll2+". They have paid "+currentSpace.getOwner().getName()+" $"+currentSpace.payUtilities(player, roll1, roll2));
					}else {
						System.out.println("They have paid "+currentSpace.getOwner().getName()+" $"+currentSpace.getRent());
						currentSpace.payRent(player);
					}
				}
				
			//Buying Property
			} else {
				System.out.print(name+" currently has $"+player.getMoney()+". "+currentSpace.getName()+" costs $"+currentSpace.getPrice()+" to buy.\nDo you wish to purchase this property(Y or N): ");
				String answer = input.next().toUpperCase();
				input.nextLine();
				if (answer.equals("Y")||answer.equals("YES")) {
					if (player.getMoney() < currentSpace.getPrice()) {
						System.out.println("Sorry, you do not have enough money so you are unable to buy this property.");
					}else {
						if(player.getSpace()%10 == 5)
							player.boughtRailroad(currentSpace);
						else if(player.getSpace()==12||player.getSpace()==28)
							player.boughtUtility(currentSpace);
						else
							player.boughtProperty(currentSpace);
						System.out.println(name+ " has paid $"+currentSpace.getPrice()+" to purchase "+currentSpace.getName()+". They now have $"+player.getMoney());
					}
				} else {
					System.out.println(name+" has chosen to not buy this property, saving $"+currentSpace.getPrice());
				}
			}
		}
		System.out.println();
	}
}
