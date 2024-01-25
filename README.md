Reverse Geolocator: Takes a CSV with lat/long columns, modifies entry line with city, country, state ( if US), and distance from city;
I also implemented the OpenCage API with the same algo, but wanted to compare with a custom implementation, eliminating ineffeciencies, rates, and limits.

Global distance is calculated using the HaverSine formula, and Wikipedia's mean Globe Radius.
Custom implementation uses a csv of ~43000 popular world cities
Implements dotenv 
.env contains the OpenCage API key and files for input/output

For entries without lat/long: no added data (empty fields)
