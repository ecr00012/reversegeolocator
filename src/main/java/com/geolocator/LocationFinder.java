package com.geolocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;

public class LocationFinder {
    

  
  private static ArrayList<Location> cities;
  private static ArrayList<Location> citiesUS;
  private static String cityFilePath = "src/countries&cities/worldcities.csv";
  private static BufferedReader cityReader;
  private static String cityFilePathUS = "src/countries&cities/uscities.csv";
  private static BufferedReader cityReaderUS;
  
  // write own disrance formula 
  // Implement States
  public LocationFinder(){
    try {
      
        cities = new ArrayList<Location>();
            cityReader = new BufferedReader(new FileReader(new File(cityFilePath)));
            cityReaderUS = new BufferedReader(new FileReader(new File(cityFilePathUS)));
            cityReader.readLine();
            cityReaderUS.readLine();
            cities =  new ArrayList<Location>();
            citiesUS = new ArrayList<Location>();
            String line;
            String line2;
            
            while ((line = cityReaderUS.readLine()) != null){
                try {
                Location location;
                String[] entry = line.split(",");
                // formatting 
                for (int i = 0; i < entry.length; i++)
               entry[i] =  entry[i].replaceAll("\"","");
                
                String city = entry[0];
                String state = entry[3];
                String country = "United States";
                double longitude = Double.parseDouble(entry[7]), latitude = Double.parseDouble(entry[6]);
                location = new Location(city, country, state, latitude, longitude);
                citiesUS.add(location);
                }catch(NumberFormatException e ) {}
            }
            while ((line2 = cityReader.readLine()) !=null){
                Location location;
                String[] entry = line2.split(",");
                //formatting
                for (int i = 0; i < entry.length; i++)
                entry[i] = entry[i].replaceAll("\"","");

                String city = entry[0];
                String country = entry[4];
                double longitude = Double.parseDouble(entry[3]), latitude = Double.parseDouble(entry[2]);
                // to get state
                if (country.equals("United States")){
                    String state = "";
                    for (Location cityUS : citiesUS)
                    if (city.equals(cityUS.getCity()))
                    state = cityUS.getState();
                    location = new Location(city, country, state, latitude, longitude);
                }
                // country not US
               else  location = new Location(city, country, latitude, longitude);
                cities.add(location);
    
        }
        // list of citiesfound

    } catch (FileNotFoundException e) {
        System.out.println(e);
    } catch (IOException i) {
        
        System.out.println(i);
    }

    
  }

public Location findNearestCity(Location location){

     // find country with smallest distance from given coordinate
     Location nearestCity= cities.get(0);
     double minDistance = DistanceCalculator.calculateDistance(nearestCity, location);

     for (Location city: cities){
         
         double distance = DistanceCalculator.calculateDistance(location, city);

         if (distance < minDistance)
            {nearestCity = city; minDistance = distance;}
 
     }
     
     
             return nearestCity;

    }

}

