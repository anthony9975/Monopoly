
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Arrays;

import java.util.Scanner;
import java.util.ArrayList;
public class Checkpoint5Tests {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Test
  public void testMax() {
    System.out.println("testMax");

    Double[][] values = { { 28.14971007, 28.98916968, 28.70592263, 28.77157013, 29.27456382 },
        { 36.81818182, 36.81818182, 30.45454545, 30.454, 4545.0 }, { 18.77984085, 19.27561399, 18.88402121 },
        { 36.36363636 }, { 49.0, 49.0, 56.0, 60.0, 7.0 } };
    Integer[] years = { 1, 2, 3, 4, 5 };
    Country myCountry = new Country("Utopia", "Something something", new ArrayList<Integer>(Arrays.asList(years)),
        new ArrayList<Double>(Arrays.asList(values[0])));

    Double[] actuals = new Double[5];
    Double[] expecteds = { 29.27456382, 4545.0, 19.27561399, 36.36363636, 60.0 };

    String[] arrays = new String[5];
    for (int i = 0; i < values.length; i++) {
      myCountry.setData(new ArrayList<Double>(Arrays.asList(values[i])));
      actuals[i] = myCountry.max();
      arrays[i] = Arrays.toString(values[i]) + "\n";
    }

    String msg = String.format("For max %s", Arrays.toString(arrays));
    assertEquals(msg, Arrays.toString(expecteds), Arrays.toString(actuals));
  }

  @Test
  public void testMin() {
    System.out.println("testMin");
    Double[][] values = { { 28.14971007, 28.98916968, 28.70592263, 28.77157013, 29.27456382 },
        { 36.81818182, 36.81818182, 30.45454545, 30.454, 4545.0 }, { 18.77984085, 19.27561399, 18.88402121 },
        { 36.36363636 }, { 49.0, 49.0, 56.0, 60.0, 7.0 } };
    Integer[] years = { 1, 2, 3, 4, 5 };
    Country myCountry = new Country("Utopia", "Something something", new ArrayList<Integer>(Arrays.asList(years)),
        new ArrayList<Double>(Arrays.asList(values[0])));

    Double[] actuals = new Double[5];
    Double[] expecteds = { 28.14971007, 30.454, 18.77984085, 36.36363636, 7.0 };

    String[] arrays = new String[5];
    for (int i = 0; i < values.length; i++) {
      myCountry.setData(new ArrayList<Double>(Arrays.asList(values[i])));
      actuals[i] = myCountry.min();
      arrays[i] = Arrays.toString(values[i]) + "\n";
    }

    String msg = String.format("For min %s", Arrays.toString(arrays));
    assertEquals(msg, Arrays.toString(expecteds), Arrays.toString(actuals));
  }

  @Test
  public void countryConstructorTest() {
    System.out.println("countryConstructorTest");
    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[] data = { 36.81818182, 36.81818182, 30.45454545, 30.454, 4545.0 };
    Country myCountry = new Country("Utopia", "Something about somethingElse (in years)",
        new ArrayList<Integer>(Arrays.asList(years)), new ArrayList<Double>(Arrays.asList(data)));
    assertEquals("Testing getter of the name of the country", "Utopia", myCountry.getCountry());
    assertEquals("Testing getter of the series of the country", "Something about somethingElse ",
        myCountry.getSeries());
    assertEquals("Testing getter of the years of the country", new ArrayList<Integer>(Arrays.asList(years)),
        myCountry.getYears());
    assertEquals("Testing getter of the data of the country", new ArrayList<Double>(Arrays.asList(data)),
        myCountry.getData());
  }

  @Test
  public void getUnitsTest() {
    System.out.println("getUnitsTest");

    String[] values = { "Mobile cellular subscriptions (per 100 people)", "Gross domestic Product growth (annual %)",
        "Annual freshwater withdrawals, industry (% of total freshwater withdrawal)",
        "Proportion of seats held by women in national parliaments (%)", "Mobile cellular subscriptions" };
    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[] val = { 21.0, 2022.0, 2023.0, 2024.0, 2025.0 };
    Country myCountry = new Country("Utopia", "something something", new ArrayList<Integer>(Arrays.asList(years)),
        new ArrayList<Double>(Arrays.asList(val)));

    String[] actuals = new String[5];
    String[] expecteds = { "per 100 people", "annual %", "% of total freshwater withdrawal", "%", "" };

    String[] arrays = new String[5];
    for (int i = 0; i < values.length; i++) {
      myCountry.setSeries(values[i]);
      actuals[i] = myCountry.getUnits();
      arrays[i] = values[i] + "\n";
    }

    String msg = String.format("For getUnits %s", Arrays.toString(arrays));
    assertEquals(msg, Arrays.toString(expecteds), Arrays.toString(actuals));
  }

  @Test
  public void getAcronymTest() {
    System.out.println("getAcronymTest");

    String[] values = { "Mobile cellular subscriptions (per 100 people)", "Gross domestic Product growth (annual %)",
        "Annual freshwater withdrawals, industry (% of total freshwater withdrawal)",
        "Proportion of seats held by women in national parliaments (%)", "Mobile cellular subscriptions" };
    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[] val = { 021.0, 2022.0, 2023.0, 2024.0, 2025.0 };
    Country myCountry = new Country("Utopia", "something something", new ArrayList<Integer>(Arrays.asList(years)),
        new ArrayList<Double>(Arrays.asList(val)));

    String[] actuals = new String[5];
    String[] expecteds = { "MCS", "GDPG", "AFWI", "PSHWNP", "MCS" };

    String[] arrays = new String[5];
    for (int i = 0; i < values.length; i++) {
      myCountry.setSeries(values[i]);
      // System.out.println(values[i]);
      actuals[i] = myCountry.getAcronym();
      // System.out.println(actuals[i]);
      arrays[i] = values[i] + "\n";
    }

    String msg = String.format("For getAcronym %s", Arrays.toString(arrays));
    assertEquals(msg, Arrays.toString(expecteds), Arrays.toString(actuals));
  }

  @Test
  public void getTrendTest() {
    System.out.println("getTrendTest");

    Double[][] values = { { 28.14971007, 28.98916968, 29.70592263, 30.77157013, 31.27456382 },
        { 36.81818182, 36.80818181, 30.45454545, 30.354, 29.0 }, { 18.77984085, 19.27561399, 18.88402121 },
        { 36.36363636, 36.36363636 }, { 49.0, 49.0, 56.0, 60.0, 7.0 } };
    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };

    Country myCountry = new Country("Utopia", "Mobile cellular subscriptions (per 100 people)",
        new ArrayList<Integer>(Arrays.asList(years)), new ArrayList<Double>(Arrays.asList(values[0])));

    String[] actuals = new String[5];
    String[] expecteds = { "up", "down", "no trend", "no trend", "no trend" };

    String[] arrays = new String[5];
    for (int i = 0; i < values.length; i++) {
      myCountry.setData(new ArrayList<Double>(Arrays.asList(values[i])));
      actuals[i] = myCountry.getTrend();
      arrays[i] = Arrays.toString(values[i]) + "\n";
    }
    String msg = String.format("For getTrend %s", Arrays.toString(arrays));
    assertEquals(msg, Arrays.toString(expecteds), Arrays.toString(actuals));
  }

  @Test
  public void addDataPointTest() {
    System.out.println("addDataPointTest");

    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[] data = { 36.81818182, 36.81818182, 30.45454545, 30.454, 4545.0 };
    Country myCountry = new Country("Utopia", "Something about somethingElse (in years)",
        new ArrayList<Integer>(Arrays.asList(years)), new ArrayList<Double>(Arrays.asList(data)));
    myCountry.addDataPoint(2027, 4242);

    assertEquals("Testing Adding data Point Year", 2027,
        (int) myCountry.getYears().get(myCountry.getYears().size() - 1));
    assertEquals("Testing Adding data Point Data", 4242.0,
        (double) myCountry.getData().get(myCountry.getData().size() - 1), 0.01);
  }

  @Test
  public void editDataPointTest() {
    System.out.println("editDataPointTest");

    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[] data = { 36.81818182, 36.81818182, 30.45454545, 30.454, 4545.0 };
    Country myCountry = new Country("Utopia", "Something about somethingElse (in years)",
        new ArrayList<Integer>(Arrays.asList(years)), new ArrayList<Double>(Arrays.asList(data)));
    myCountry.editDataPoint(2023, 62);

    assertEquals("Testing Edit data Point Year", 2023, (int) myCountry.getYears().get(2));
    assertEquals("Testing Edit data Point Data", 62.0, (double) myCountry.getData().get(2), 0.01);
  }

  @Test
  public void removeByNameTest() {
    System.out.println("removeByNameTest");
    ArrayList<Country> listCountries = new ArrayList<Country>();
    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[][] data = { { 36.81818182, 36.81818181, 30.45454545, 30.454, 29.2 },
        { 43.4, 42.5, 39.6, 35.7, 32.2 },
        { 21.5, 34.7, 56.4, 102.4, 12.4, 12.5 },
        { 43.4, 42.5, 39.6, 56.4, 102.4, 12.4 },
        { 36.81, 36.80, 30.45454545, 30.454, 29.2 },
        { 44.4, 42.5, 39.6, 37.7, 32.9 } };
    String[] countryName = { "Togo", "Benin", "Yemen", "Ghana", "Sudan", "Laos" };
    for (int i = 0; i < countryName.length; i++) {
      listCountries.add(new Country(countryName[i], "Something about somethingElse (in years)",
          new ArrayList<Integer>(Arrays.asList(years)), new ArrayList<Double>(Arrays.asList(data[i]))));
    }
    CountryClient.removeByName(listCountries, "Benin");

    assertEquals("Testing removeCountryByName size", 5, listCountries.size());
    assertEquals("Testing removeCountryByName the new 2nd element name is the one at place 3 ", "Yemen",
        listCountries.get(1).getCountry());
  }

  @Test
  public void removeNoTrendTest() {
    System.out.println("removeNoTrendTest");

    ArrayList<Country> listCountries = new ArrayList<Country>();
    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[][] data = { { 36.81818182, 36.81818181, 30.45454545, 30.454, 29.2 }, // down
        { 43.4, 44.5, 45.6, 47.7, 350.2 }, // up
        { 21.5, 34.7, 56.4, 102.4, 12.4, 12.5 }, // no trend
        { 43.4, 42.5, 39.6, 56.4, 102.4, 12.4 }, // no trend
        { 36.81, 36.80, 30.45454545, 30.454, 29.2 }, // down
        { 144.4, 242.5, 339.6, 437.7, 532.9 } };// up
    String[] countryName = { "Togo", "Benin", "Yemen", "Ghana", "Sudan", "Laos" };
    for (int i = 0; i < countryName.length; i++) {
      listCountries.add(new Country(countryName[i], "Something about somethingElse (in years)",
          new ArrayList<Integer>(Arrays.asList(years)), new ArrayList<Double>(Arrays.asList(data[i]))));
    }
    CountryClient.removeNoTrend(listCountries);

    assertEquals("Testing removeNoTrend size", 4, listCountries.size());
    assertEquals("Testing removeNoTrend the new 2nd element name is the one at place 5 ", "Sudan",
        listCountries.get(2).getCountry());
    assertEquals("Testing removeNoTrend the 2nd element name is still the same one ", "Benin",
        listCountries.get(1).getCountry());

  }

  @Test
  public void getListBasedOnTrendTest1() {
    System.out.println("getListBasedOnTrendTest1");

    ArrayList<Country> listCountries = new ArrayList<Country>();
    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[][] data = { { 36.81818182, 36.81818181, 30.45454545, 30.454, 29.2 }, // down Togo
        { 43.4, 44.5, 45.6, 47.7, 350.2 }, // up Benin
        { 21.5, 34.7, 56.4, 102.4, 12.4, 12.5 }, // no trend Yemen
        { 43.4, 42.5, 39.6, 56.4, 102.4, 12.4 }, // no trend Ghana
        { 36.81, 36.80, 30.45454545, 30.454, 29.2 }, // down Sudan
        { 144.4, 242.5, 339.6, 437.7, 532.9 } };// up Laos
    String[] countryName = { "Togo", "Benin", "Yemen", "Ghana", "Sudan", "Laos" };
    for (int i = 0; i < countryName.length; i++) {
      listCountries.add(new Country(countryName[i], "Something about somethingElse (in years)",
          new ArrayList<Integer>(Arrays.asList(years)), new ArrayList<Double>(Arrays.asList(data[i]))));
    }
    ArrayList<String> newList = CountryClient.getListBasedOnTrend(listCountries, "no trend");

    assertEquals("Testing getListBasedOnTrend size", 2, newList.size());
    assertEquals("Testing getListBasedOnTrend 1st country ", true,
        newList.contains("Yemen"));
    assertEquals("Testing getListBasedOnTrend 2nd country ", true,
        newList.contains("Ghana"));
    assertEquals("Testing getListBasedOnTrend size old list", 6, listCountries.size());

  }

  @Test
  public void getListBasedOnTrendTest2() {
    System.out.println("getListBasedOnTrendTest2");

    ArrayList<Country> listCountries = new ArrayList<Country>();
    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[][] data = { { 36.81818182, 36.81818181, 30.45454545, 30.454, 29.2 }, // down
        { 43.4, 44.5, 45.6, 47.7, 350.2 }, // up
        { 21.5, 34.7, 56.4, 102.4, 12.4, 12.5 }, // no trend
        { 43.4, 42.5, 39.6, 56.4, 102.4, 12.4 }, // no trend
        { 36.81, 36.80, 30.45454545, 30.454, 29.2 }, // down
        { 144.4, 242.5, 339.6, 437.7, 532.9 } };// up
    String[] countryName = { "Togo", "Benin", "Yemen", "Ghana", "Sudan", "Laos" };
    for (int i = 0; i < countryName.length; i++) {
      listCountries.add(new Country(countryName[i], "Something about somethingElse (in years)",
          new ArrayList<Integer>(Arrays.asList(years)), new ArrayList<Double>(Arrays.asList(data[i]))));
    }
    ArrayList<String> newList = CountryClient.getListBasedOnTrend(listCountries, "down");

    assertEquals("Testing getListBasedOnTrend size", 2, newList.size());
    assertEquals("Testing getListBasedOnTrend 1st country ", true,
    	newList.contains("Togo"));
    assertEquals("Testing getListBasedOnTrend 2nd country ", true,
        newList.contains("Sudan"));
    assertEquals("Testing getListBasedOnTrend size old list", 6, listCountries.size());

  }

  @Test
  public void getListBasedOnTrendTest3() {
    System.out.println("getListBasedOnTrendTest3");

    ArrayList<Country> listCountries = new ArrayList<Country>();
    Integer[] years = { 2021, 2022, 2023, 2024, 2025 };
    Double[][] data = { { 36.81818182, 36.81818181, 30.45454545, 30.454, 29.2 }, // down
        { 43.4, 44.5, 45.6, 47.7, 350.2 }, // up
        { 21.5, 34.7, 56.4, 102.4, 12.4, 12.5 }, // no trend
        { 43.4, 42.5, 39.6, 56.4, 102.4, 12.4 }, // no trend
        { 36.81, 36.80, 30.45454545, 30.454, 29.2 }, // down
        { 144.4, 242.5, 339.6, 437.7, 532.9 } };// up
    String[] countryName = { "Togo", "Benin", "Yemen", "Ghana", "Sudan", "Laos" };
    for (int i = 0; i < countryName.length; i++) {
      listCountries.add(new Country(countryName[i], "Something about somethingElse (in years)",
          new ArrayList<Integer>(Arrays.asList(years)), new ArrayList<Double>(Arrays.asList(data[i]))));
    }
    ArrayList<String> newList = CountryClient.getListBasedOnTrend(listCountries, "up");

    assertEquals("Testing getListBasedOnTrend size", 2, newList.size());
    assertEquals("Testing getListBasedOnTrend 1st country", true,
        newList.contains("Benin"));
    assertEquals("Testing getListBasedOnTrend 2nd country ", true,
        newList.contains("Laos"));
    assertEquals("Testing getListBasedOnTrend size old list", 6, listCountries.size());

  }

  @Test
  public void getListBasedOnTrendTestException() {
    System.out.println("getListBasedOnTrendTestException");
    
    ArrayList<Country> c = new ArrayList<Country>();
    c.add(new Country("random name", "serious series", new ArrayList<Integer>(), new ArrayList<Double>()));
    String trendType = "dowwwwn";
    try {
      ArrayList<String> list = CountryClient.getListBasedOnTrend(c, "dowwwwn");
      fail(String.format("getListBasedOnTrend did not throw exception with an input of %s", trendType));
    } catch (Exception e) {
      String allGood = "All Good";
    }
  }

}



