import java.util.ArrayList;

public class Players {

	//Fields
	private String name;
	private int numRailroads;
	private int numUtilities;
	private ArrayList<Spaces> properties;
	private int jailTime;
	private boolean jailFree;
	private int money;
	private int onSpaceNum;
	//14 options + 1 repeat
	private String[] chanceOptions = {"Advance to \"Go\". (Collect $200)", "Advance to Illinois Ave. If you pass \"Go\", collect $200.", 
			"Advance to St. Charles Place. If you pass \"Go\", collect $200.", 
			"Advance to the nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total 10 times the amount thrown.", 
			"Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rent to which they are otherwise entitled.",
			"Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rent to which they are otherwise entitled.",
			"Bank pays you dividend of $50.", "Speeding Fine $15", "Advance to Boardwalk.",
			"Get out of Jail Free. This card may be kept until needed.", "Go Back Three Spaces.",
			"Go to Jail. Go directly to Jail. Do not pass \"GO\", do not collect $200.",
			"Make general repairs on all your property: For each house pay $25, For each hotel {pay} $100.",
			"Take a trip to Reading Railroad. If you pass \"Go\", collect $200.",
			"Your building loan matures. Collect $150."};
	private ArrayList<Integer> chanceNums;
	//15 options
	private String[] communityChestOptions = {"Advance to \"Go\". (Collect $200)","Bank error in your favor. Collect $200.", 
			"Doctor's fees. Pay $50.", "From sale of stock you get $50.", "Get Out of Jail Free. This card may be kept until needed.", 
			"Go to Jail. Go directly to jail. Do not pass \"Go\", Do not collect $200.", "Holiday Fund matures. Receive $100.",
			"Income tax refund. Collect $20.", "Life insurance matures. Collect $100",
			"Pay hospital fees of $100.", "School fees. Pay $50.", "Receive $25 consultancy fee.", 
			"You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.", 
			"You have won second prize in a beauty contest. Collect $10.", "You inherit $100."};
	private ArrayList<Integer> chestNums;
	
	
	//Constructor
	public Players(String name) {
		this.name = name;
		numRailroads = 0;
		numUtilities = 0;
		jailTime = -1;
		jailFree = false;
		money = 1500;
		onSpaceNum = 0;
		properties = new ArrayList<Spaces>();
		chestNums = new ArrayList<Integer>();
		chanceNums = new ArrayList<Integer>();
		setCards(chestNums, 15);
		setCards(chanceNums, 15);
	}
	
	//Mutators
	public void gainMoney(int amount) {
		money += amount;
	}
	
	public void move(int dice1, int dice2) {
		onSpaceNum += dice1 + dice2;
		if (onSpaceNum > 39) {
			onSpaceNum -= 40;
			gainMoney(200);
			System.out.println(name + " has passed \"GO\", they receive $200. They now have $"+money);
		}
	}
	
	public void boughtRailroad(Spaces property) {
		gainMoney(-200);
		numRailroads++;
		properties.add(property);
		property.paperwork(this);
	}
	
	public void boughtUtility(Spaces property) {
		gainMoney(-150);
		numUtilities++;
		properties.add(property);
		property.paperwork(this);
	}
	
	public void boughtHouse(int cost, Spaces property) {
		gainMoney(-1*cost);
		int index = 0;
		for (int i = 0; i < properties.size(); i++) {
			if (properties.get(i) == property)
				index = i;
		}
		properties.get(index).buyHouse();
	}
	
	public void boughtProperty(Spaces property) {
		gainMoney(-1*property.getPrice());
		properties.add(property);
		property.paperwork(this);
	}
	
	public void chance() {
		int card = chanceNums.get((int)Math.random()*chanceNums.size());
		System.out.println(chanceOptions[card-1]);
		if (card == 1) {
			onSpaceNum = 0;
		}
		if (card == 2) {
			if (onSpaceNum > 24)
				gainMoney(200);
			onSpaceNum = 24;
		}
		if (card == 3) {
			if (onSpaceNum > 11)
				gainMoney(200);
			onSpaceNum = 11;
		}
		if (card == 4) {
			if (onSpaceNum <= 12)
				onSpaceNum = 12;
			else if(onSpaceNum > 28) {
				onSpaceNum = 12;
				gainMoney(200);
			} else
				onSpaceNum = 28;
		}
		if (card == 5 || card == 6) {
			int[] railroads = {5, 15, 25, 35};
			int[] diffs = new int[4];
			if(onSpaceNum > 35) {
				onSpaceNum = 5;
				gainMoney(200);
			}else {
				for (int i = 0; i<4; i++) {
					diffs[i] = railroads[i] - onSpaceNum;
				}
				int index = 0;
				int diff = 100;
				for (int i = 0; i<4; i++) {
					if ((Math.abs(diffs[i])<Math.abs(diff))&&(diffs[i]>=0)) {
						index = i;
						diff = diffs[i];
					}
				}
				onSpaceNum = railroads[index];
			}
		}
		if (card == 7)
			gainMoney(50);
		if (card == 8)
			gainMoney(-15);
		if (card == 9)
			onSpaceNum = 39;
		if (card == 10) {
			jailFree = true;
		}
		if (card == 11) {
			if (onSpaceNum < 3)
				onSpaceNum += 40;
			onSpaceNum -= 3;
		}
		if (card ==12) {
			onSpaceNum = 10;
			jailTime = 0;
		}
		if (card == 13) {
			int cost = 0;
			for (Spaces prop: properties) {
				if (prop.getHotel())
					cost += 100;
				else
					cost += prop.getHouses()*25;
			}
			gainMoney(-1*cost);
		}
		if (card == 14) {
			if (onSpaceNum > 5)
				gainMoney(200);
			onSpaceNum = 5;
		}
		if (card == 15)
			gainMoney(150);
		int index = 0;
		for (int i = 0; i < chanceNums.size(); i++) {
			if (card == chanceNums.get(i)) {
				index = i;
			}
		}
		chanceNums.remove(index);
		if (chanceNums.size()==0) {
			setCards(chanceNums, 15);
		}
	}
	
	public void communityChest() {
		int card = chestNums.get((int)Math.random()*chestNums.size());
		System.out.println(communityChestOptions[card-1]);
		if (card == 1) {
			onSpaceNum = 0;
			gainMoney(200);
		}
		if (card == 2)
			gainMoney(200);
		if (card == 3)
			gainMoney(-50);
		if (card == 4)
			gainMoney(50);
		if (card == 5)
			jailFree = true;
		if (card == 6) {
			onSpaceNum = 10;
			jailTime = 0;
		}
		if (card == 7)
			gainMoney(100);
		if (card == 8)
			gainMoney(20);
		if (card == 9)
			gainMoney(100);
		if (card == 10)
			gainMoney(-50);
		if (card == 11)
			gainMoney(-50);
		if (card == 12)
			gainMoney(25);
		if (card == 13){
			int cost = 0;
			for (Spaces prop: properties) {
				if (prop.getHotel())
					cost += 100;
				else
					cost += prop.getHouses()*25;
			}
			gainMoney(-1*cost);
			System.out.println(name+" has paid $"+cost+" for general repairs.");
		}
		if (card == 14)
			gainMoney(10);
		if (card == 15)
			gainMoney(100);
		int index = 0;
		for (int i = 0; i < chestNums.size(); i++) {
			if (card == chestNums.get(i)) {
				index = i;
			}
		}
		chestNums.remove(index);
		if (chestNums.size()==0) {
			setCards(chestNums, 15);
		}
	}
	
	public String toString() {
		String[] board = {"Go", "Mediterranean Avenue", "Community Chest", "Baltic Avenue", "Income Tax", "Reading Railroad", "Oriental Avenue", "Chance",
				"Vermont Avenue", "Connecticut Avenue", "Just Visiting", "St. Charles Place", "Electric Company", "States Avenue", "Virginia Avenue",
				"Pennsylvania Railroad", "St. James Place", "Community Chest", "Tennessee Avenue", "New York Avenue", "Free Parking", "Kentucky Avenue",
				"Chance", "Indiana Avenue", "Illinois Avenue", "B. & O. Railroad", "Atlantic Avenue", "Ventnor Avenue", "Water Works", "Marvin Gardens", "Go To Jail", 
				"Pacific Avenue", "North Carolina Avenue", "Community Chest", "Pennsylvania Avenue", "Short Line", "Chance", "Park Place", "Luxury Tax", "Boardwalk"};
		String currentSpace = board[onSpaceNum];
		if (jailTime>-1)
			currentSpace = "Jail";
		String output = "\nPlayer Name: " + name + "\nTotal Money: " + money + "\nIs Currently on Space: " + currentSpace + "\nNumber of Properties: " + properties.size();
		if (properties.size() == 0)
			output += "\nNo Properties Currently";
		else {
			for (Spaces prop: properties) {
				if (prop.getSpace()%5 == 0)
					output += "\n\t" + prop.getName();
				else if (prop.getSpace() == 12 || prop.getSpace() == 28)
					output += "\n\t" + prop.getName();
				else {
					output += "\n\t" + prop.getName() + ": ";
					if(prop.getHotel())
						output += "1 hotel";
					else if (prop.getHouses()>0)
						output += prop.getHouses()+ " houses";
					else
						output += "No Houses";
				}
			}
		}
		if (jailTime > -1)
			output += "\nCurrently Serving Jail Time: \n\tStill has " + (3 - jailTime) + " rounds left to serve";
		if (jailFree)
			output += "\nHas a \"Get Out of Jail Free\" card";
		return output + "\n";
	}

	public void setSpace(int space) {
		onSpaceNum = space;
	}
	
	public void outJail() {
		jailTime = -1;
	}
	
	public void serveTime() {
		jailTime++;
	}
	
	public void inJail() {
		jailTime = 0;
		onSpaceNum = 10;
	}

	//Getters
	public String getName() {
		return name;
	}

	public int getSpace() {
		return onSpaceNum;
	}
	
	public int getJail() {
		return jailTime;
	}

	public int getUtilities() {
		return numUtilities;
	}
	
	public int getRailroads() {
		return numRailroads;
	}

	public int getMoney() {
		return money;
	}
	
	//Helper Methods
	private void setCards(ArrayList<Integer> arr, int max) {
		for (int i = 1; i <= max; i++) {
			arr.add(i);
		}
	}
	
}