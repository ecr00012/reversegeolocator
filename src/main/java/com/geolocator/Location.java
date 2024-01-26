package com.geolocator;

public class Location {
   private  String city;
   private  String state;
   private  String country;
   private  double latitude;
   private double longitude;
   private double distance;

    public Location(String city, String country, double latitude, double longitude) {
        this.city = city;
        this.state = "";
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = -1;
    }
    public Location(String city, String country, String state, double latitude, double longitude, double distance) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public Location(String city, String country, String state, double latitude, double longitude) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = -1;
       
    }


    public Location(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = this.city = this.country = "";
        this.distance = -1;
        
    }

    public Location(Location location, double distance){
        this.latitude = location.latitude;
        this.longitude = location.longitude;
        this.country = location.country;
        this.state = location.state;
        this.city = location.city;
        this.distance = distance;
    }

    public String getCity(){
         return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public Object getDistance(){
        return distance;
    }

    @Override
    public String toString(){
        if (country.equals("United States"))
                
        return city + ", " + state + ", " + country + ", Distance: " + distance;

        else
        return city +", " + country + ", Distance: " + distance;
    }

   
    public boolean equals(Location location){
       if (this.latitude == location.latitude && this.longitude == location.longitude)
       return true;

       return false;
    }




}
