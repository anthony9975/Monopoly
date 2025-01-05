
public class Spaces {

	//Fields
	private int spaceNum;
	private String name;
	private int price;
	private int housePrice;
	private int numHouses;
	private boolean hotel;
	private int[] rentLevels;
	private boolean bought;
	private Players owner;
	
	//Constructors
 	public Spaces(int spaceNumber, String name, int price, int housePrice, int[] rentLevels) {
		this.spaceNum = spaceNumber;
		this.name = name;
		this.price = price;
		this.housePrice = housePrice;
		this.rentLevels = rentLevels;
		numHouses = 0;
		hotel = false;
		bought = false;
	}
	
	public Spaces(int spaceNumber, String name, int price) {
		this.spaceNum = spaceNumber;
		this.name = name;
		this.price = price;
		bought = false;
	}
	
	public Spaces(int spaceNumber, String name) {
		this.spaceNum = spaceNumber;
		this.name = name;
	}
	
	//Mutators
	public void buyHouse() {
		if (numHouses == 4) {
			hotel = true;
		} else {
			numHouses++;
		}
		owner.gainMoney(-1*housePrice);
	}
	
	public void paperwork(Players owner) {
		this.owner = owner;
		bought = true;
	}
	
	public void payRent(Players renter) {
		owner.gainMoney(this.getRent());
		renter.gainMoney(this.getRent()*-1);
	}
	
	public int payUtilities(Players customer, int roll1, int roll2) {
		if (owner.getUtilities() == 1) {
			owner.gainMoney((roll1+roll2)*4);
			customer.gainMoney((roll1+roll2)*-4);
			return (roll1+roll2)*4;
		} else {
			owner.gainMoney((roll1+roll2)*10);
			customer.gainMoney((roll1+roll2)*-10);
			return (roll1+roll2)*10;
		}
	}
	
	public int payRailroads(Players customer) {
		int cost = 25*exponent(2, owner.getRailroads()-1);
		owner.gainMoney(cost);
		customer.gainMoney(cost);
		return cost;
	}
	
	
	//Accessors
	public int getHouses() {
		return numHouses;
	}
	
	public boolean getHotel() {
		return hotel;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getSpace() {
		return spaceNum;
	}
	
	public boolean getBought() {
		return bought;
	}

	public int getRent() {
		if (hotel)
			return rentLevels[5];
		return rentLevels[numHouses];
	}

	public Players getOwner() {
		return owner;
	}
	
	public int getHousePrice() {
		return housePrice;
	}
	
	//Helper Methods
	public static int exponent (int base, int powerOf) {
		if (powerOf<0) {
			throw new IllegalArgumentException("Can't raise to the power of a negative number");
		}else if (powerOf==0) {
			if (base==0) {
				throw new IllegalArgumentException("Can't raise 0 to the power of 0");
			}
			base = 1;
			return base;
		}else{
			int product = 1;
			for (int i = 1;i<=powerOf;i++) {
				product=product*base;
			}
			return product;
		}
	}
}
