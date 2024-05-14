import java.util.ArrayList;

public class Country {//This class stores the name, series, year, and data for a single country
	
	private String countryName;
	private String series;
	private ArrayList<Integer> years;
	private ArrayList<Double> data;
	
	public Country(String countryName, String series, ArrayList<Integer> years, ArrayList<Double> data) {//Constructor for the country class
		this.countryName = countryName;
		this.series = series;
		this.years = years;
		this.data = data;
	}
	
	public String getCountry() {//returns the country name
		return countryName;
	}
	
	public String getSeries() {//returns the series
		String justSeries;
		if (series.indexOf("(")==-1)
			justSeries = series;
		else
			justSeries = series.substring(0, series.indexOf("("));
		return justSeries;
	}
	
	public ArrayList<Integer> getYears() {//returns an arraylist of integers for the years
		return years;
	}
	
	public ArrayList<Double> getData() {//returns an array list of doubles for the data
		return data;
	}
	
	public String getTrend() {//determines the trend of the data
		if (trendsUp())
			return "up";
		if (trendsDown())
			return "down";
		return "no trend";
	}
	
	public String getAcronym() {//returns the acronym of the country name.
		String acronym = "";
		String justSeries;
		if (series.indexOf("(")==-1)
			justSeries = series;
		else
			justSeries = series.substring(0, series.indexOf("("));
		String[] excludedWords = {"of","in","the","at","to","by","per","on","a","an","with"};
		String[] splitSeries = justSeries.split(" ");
		for (String word: splitSeries) {
			boolean wordExcluded = false;
			for (String excludeWord: excludedWords) {
				if (excludeWord.equals(word))
					wordExcluded = true;
			}
			if (!wordExcluded)
				acronym += word.charAt(0);
		}
		return acronym.toUpperCase();
	}
	
/*	private double regression() {
		double xmean = 0;
		for (double values: years) {
			xmean += values;
		}
		xmean /= years.size();
		
		double ymean = 0;
		for (double values: data) {
			ymean += values;
		}
		ymean /= data.size();
		
		double topSum = 0;
		for (int i = 0; i < data.size(); i++) {
			topSum += ((data.get(i)-ymean)*(years.get(i)-xmean));
		}
		
		double bottomSum = 0;
		for (double values: years) {
			bottomSum += (values-xmean)*(values-xmean);
		}
		
		return topSum/bottomSum;
		
	}*/
	
	private boolean trendsUp() {//Determines if the data trends up
		/*if (regression()>0)
			return true;
		return false; */
		for (int i = 0; i < data.size()-1; i++) {
			if (data.get(i) >= data.get(i+1))
				return false;
		}
		return true;
	}
	
	private boolean trendsDown() {//Determines if the data trends down
		/*if (regression()<0)
			return true;
		return false;*/
		for (int i = 0; i < data.size()-1; i++) {
			if (data.get(i) <= data.get(i+1))
				return false;
		}
		return true;
	}
	
	public String getUnits() {//Returns the units of the data
		String output = "";
		if (series.indexOf("(")!=-1)
			output = series.substring(series.indexOf("(")+1,series.indexOf(")"));
		return output;
	}
	
	public void setSeries(String newSeries) {//Allows you to set the series
		series = newSeries;
	}
	
	public void setData (ArrayList<Double> newData) {//Allows you to set the data
		data = newData;
	}
	
	public double max () {//Determines the maximum value in the data
		double maximum = data.get(0);
		for (double elem: data) {
			if (elem > maximum)
				maximum = elem;
		}
		return maximum;
	}
	
	public double min () {//Determines the minimum value in the data
		double minimum = data.get(0);
		for (double elem: data) {
			if (elem < minimum)
				minimum = elem;
		}
		return minimum;
	}
	
	public String toString () {//Converts all the fields stored in a Country class into a printable output
		String output = "";
		for (int elem: years)
			output += elem + "\t";
		output += "\n";
		for (double elem: data)
			output += Math.round(elem*100)/100.0 + "\t";
		output += "\nThis is the \"" + series + "\" for " + countryName + "\nMinimum: " + Math.round(min()*100)/100.0+ "\nMaximum: " + Math.round(max()*100)/100.0 + "\nTrending: " + getTrend() + "\n";
		return output;
	}
	
	public void addDataPoint (int year, double newDatum) {//Allows you to add a new year and a new data points corresponding to that year
		years.add(year);
		data.add(newDatum);
	}
	
	public void editDataPoint (int year, double newDatum) {//Allows you to change one of the data points
		for (int i = 0; i < years.size(); i++) {
			if (years.get(i)==year)
				data.set(i, newDatum);
		}
	}
}
