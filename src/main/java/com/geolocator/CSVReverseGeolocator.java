package com.geolocator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CSVReverseGeolocator {

    File inFile;
   
  

public CSVReverseGeolocator(File inFile){
this.inFile = inFile;

}
    /*Converts lat/longs to list of locations 
     * @return list of Locations with distances from given city
     * 
     */
    public List<Location> getLocations(String latitudeColumnName, String longitudeColumnName) throws Exception{

        try {
            LocationFinder locationFinder = new LocationFinder();
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            String headerLine = reader.readLine();
            String[] header = headerLine.split(",");
            List<Location> locations = new ArrayList<Location>();
            

            

            // find Indices of lat/long columns
            int count = 0;
            int latitudeHeader = -1, longitudeHeader = -1;  // if there are no lat/long columns
            for (String str : header){
                   
             
                if (str.equals(longitudeColumnName))
                    {longitudeHeader = count; System.out.println("Longitude is at index "+count);}
                if (str.equals(latitudeColumnName))
                    {latitudeHeader= count; System.out.println("Latitude is at index "+count);}
                    count++;

                   
            }
                
            // if no lat/long columns in file, exception
            if (latitudeHeader == -1 || longitudeHeader == -1)  {
                reader.close();  
                throw new IllegalArgumentException("Invalid lat/long indices");
            }
            


            // begin reading in entries
            String line;
           
            while ((line = reader.readLine()) != null ){
                try{
            // location lat/long coords
              String[] fields = line.split(",");
             // if lat/long not in a particular entry
                if (fields[latitudeHeader].isEmpty() || fields[longitudeHeader].isEmpty())
                continue;
                
                double latitude = Double.parseDouble(fields[latitudeHeader]);
                double longitude = Double.parseDouble(fields[longitudeHeader]);
                
                
                // finds city, state (if in U.S.), and country
                
            // MY METHOD
            Location nearestCity = locationFinder.findNearestCity(new Location(latitude, longitude));
            
            Double distance = DistanceCalculator.calculateDistance(nearestCity, new Location(latitude, longitude));
            
                    
                    locations.add(new Location(nearestCity, distance));
                  
                
            }catch(NumberFormatException e){ 
            }catch(IndexOutOfBoundsException n){ System.out.println(n); break; 
            } catch (NullPointerException np) { break; // if reader goes past final entry
            }

        }


        // close resources
         reader.close();
        
         return locations;

        } catch (FileNotFoundException e) { System.out.println(e);
        } catch (IOException i){System.out.println(i);
        } 
            throw new Exception("Unable to read locations");
    }

    public void writeLocations(File outFile, String latitudeColumnName, String longitudeColumnName)  {
       
       
        try {
            LocationFinder locationFinder = new LocationFinder();
            BufferedReader reader = new BufferedReader(new FileReader(outFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(inFile));
            String headerLine = reader.readLine();
            String[] header = headerLine.split(",");
            

            

            // find Indices of lat/long columns
            int count = 0;
            int latitudeHeader = -1, longitudeHeader = -1;  // if there are no lat/long columns
            for (String str : header){
                   
             
                if (str.equals(longitudeColumnName))
                    {longitudeHeader = count; System.out.println("Longitude is at index "+count);}
                if (str.equals(latitudeColumnName))
                    {latitudeHeader= count; System.out.println("Latitude is at index "+count);}
                    count++;

                   
            }
                
            // if no lat/long columns in file, exception
            if (latitudeHeader == -1 || longitudeHeader == -1)  {
                reader.close(); writer.close(); 
                throw new IllegalArgumentException("Invalid lat/long indices");
            }
            
           // write in header to results csv with additional location fields
            List<String> headerList = new ArrayList<String>();

            for (String str: header)    {
                headerList.add(str);
                
            }
            String[] locationNames = {"City", "State", "Country"};
            headerList.addAll(longitudeHeader+1, Arrays.asList(locationNames));

            for (String str : headerList)
                writer.write(str+",");  
                writer.newLine();

            // end write in header


            // begin reading in entries
            String line;
            
          
            while ((line = reader.readLine()) != null ){
                try{
                    
            // location lat/long coords
          
                String[] fields = line.split(",");
             // if lat/long not in a particular entry
                if (fields[latitudeHeader].isEmpty() || fields[longitudeHeader].isEmpty())
                continue;
                
                double latitude = Double.parseDouble(fields[latitudeHeader]);
                double longitude = Double.parseDouble(fields[longitudeHeader]);
                
                // finds city, state (if in U.S.), and country
                
            // MY METHOD
            Location nearestCity = locationFinder.findNearestCity(new Location(latitude, longitude));
            String[] location = {nearestCity.getCity(), nearestCity.getState(), nearestCity.getCountry() };
            Double distance = DistanceCalculator.calculateDistance(nearestCity, new Location(latitude, longitude));
            location[0] = location[0] + " , Distance: " + distance + " km";

                List<String> fieldsList = new ArrayList<String>();

                // add original entry data in the order it was given to match columns
                for (String str : fields){
                    fieldsList.add(str);
                
                }
                // adds new location data AFTER the latitude and longitude
                fieldsList.addAll(longitudeHeader+1, Arrays.asList(location));
    
                // writes new entry 
                for (String str : fieldsList ){
                    writer.write(str+",");
                }
                
                writer.newLine();
                
            }catch(NumberFormatException e){ 
            }catch(IndexOutOfBoundsException n){ System.out.println(n); break; 
            } catch (NullPointerException np) { break; // if reader goes past final entry
            }

        }


        // close resources
         reader.close();
         writer.close();
        } catch (FileNotFoundException e) { System.out.println(e);
        } catch (IOException i){System.out.println(i);
        } 
        
   
        
            
   
    }       

}

