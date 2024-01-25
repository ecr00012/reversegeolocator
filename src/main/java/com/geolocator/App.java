package com.geolocator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.cdimascio.dotenv.Dotenv;

class App  {


// example usage
    public static void main(String args[]) throws Exception   {

        // load .env resources
        Dotenv dotenv = Dotenv.configure().load();
        File inFile = new File(dotenv.get("inFile"));
        CSVReverseGeolocator locator = new CSVReverseGeolocator(inFile);
        
// File Writing Example
    File outFile = new File(dotenv.get("outFile"));
    locator.writeLocations( outFile, "LocationLatitude", "LocationLongitude");

// List<Location> example 
    
     List<Location> locations = locator.getLocations("LocationLatitude", "LocationLongitude");



      
        
            
   
    }       

}
