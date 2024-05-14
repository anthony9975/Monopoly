/*Anthony Ma
 * 10/24/2023
 * Read country data from a file and prints it out.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class CountryClient {//Client code
	public static void main (String[] args) throws FileNotFoundException {
		File inputFile = new File ("CountryDataSet.txt");
		Scanner input = new Scanner(inputFile);
		
		String line1 = input.nextLine();
		String series = line1.substring(0, line1.indexOf(","));
		
		String[] tempYears = input.nextLine().split(",");
		ArrayList<Integer> years = new ArrayList<>();
		for (int i = 1; i < tempYears.length; i++)
			years.add(Integer.parseInt(tempYears[i]));
		
		ArrayList<Country> countryArr = new ArrayList<>();
		
		String eachLine = input.nextLine();
		while (!eachLine.equals("END")) {
			
			String[] eachLineArr = eachLine.split(",");
			String countryName = eachLineArr[0];
			int initialValue = 1;
			if (countryName.indexOf("\"")!=-1) {
				countryName = eachLineArr[1].substring(1, eachLineArr[1].length()-1) + " " + eachLineArr[0].substring(1);
				initialValue++;
			}
				
			ArrayList<Double> valuesArr = new ArrayList<>();
			for (int j = initialValue; j < eachLineArr.length; j++) {
				valuesArr.add(Double.parseDouble(eachLineArr[j]));
			}
			
			countryArr.add(new Country(countryName, series, years, valuesArr));
			eachLine = input.nextLine();
		}
		
		System.out.println(countryArr.get(0).getAcronym()+" for "+ countryArr.get(0).getYears().get(0)+"-"+  countryArr.get(0).getYears().get(countryArr.get(0).getYears().size()-1)+"\n");
		for (int i = 0 ; i < countryArr.size(); i++) {
			System.out.println(countryArr.get(i));
		}
		
		input.close();
	}
	
	public static void removeByName (ArrayList<Country> countries, String name) {//Removes a country from the arraylist when given the name of that country
		for (int i = countries.size()-1; i >= 0; i--) {
			if (countries.get(i).getCountry().equals(name))
				countries.remove(i);
		}
	}
	
	public static void removeNoTrend(ArrayList<Country> countries) {//Removes all countries that have no trend
		for (int i = countries.size()-1; i >= 0; i--) {
			if (countries.get(i).getTrend().equals("no trend"))
				countries.remove(i);
		}
	}
	
	public static ArrayList<String> getListBasedOnTrend(ArrayList<Country> countries, String trendType){//Returns an arraylist of the names of countries who match the given trend
		if (!trendType.equals("up") && !trendType.equals("down") && !trendType.equals("no trend"))
			throw new IllegalArgumentException("Trend Type "+ trendType+" is not a valid Trend Type");
		ArrayList<String> matchTrendType = new ArrayList<>();
		for (Country coun: countries) {
			if (coun.getTrend().equals(trendType))
				matchTrendType.add(coun.getCountry());
		}
		return matchTrendType;
	}
}
