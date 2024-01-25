Reverse Geolocator: Takes a CSV with lat/long columns, modifies entry line with city, country, state ( if US), and distance from city;
Also contains a method to generate a list of locations with an added "distance" field (location distance in km from said location) post caculation 

LocationFinder contains a method to gather Location from a single Lat/Long pair


Motivation was having previously implemented OpenCage API reverse geolocator but wanted to compare with a custom implementation, eliminating ineffeciencies, rates, and limits.

Global distance is calculated using the HaverSine formula, and Wikipedia's mean Globe Radius.
Custom implementation uses a csv of ~43000 popular world cities and then checks result (if in the states) against a list of U.S. cities to determine state.

Implements dotenv 
.env contains files "inFile" and "outFile" for input/output

For entries without lat/long: no added data (empty fields)
