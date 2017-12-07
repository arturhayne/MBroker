package br.com.htech.mbroker;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.TextView;

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    Double latitude; // latitude
    Double longitude; // longitude
    
    //int idLatitude, idLongitude;
    int idAddress;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    
    ProgressDialog alertDialog;

	private Activity calleractivity;
	
	TextView txtCoordenadas;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }
    
    public GPSTracker(Context context, Activity calleractivity, int idAddress) {
        this.mContext = context;
        getLocation();

        //this.idLatitude = latitude;
        //this.idLongitude = longitude;
        this.idAddress = idAddress;
        this.calleractivity=calleractivity;
    }
    
    

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled){
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                	this.buscarCoordenadas();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }    
    
    

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
    	

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
	
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS Settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is disabled, set it On?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    
    public void buscarCoordenadas(){

        alertDialog = ProgressDialog.show(mContext, "Searching...", "Getting Coordinates...", true, true);
        alertDialog.show();
    	
    	/*latitude = -12.972229;
    	 longitude= -38.50142;
        new RetreiveFeedTask().execute();*/
    	
    	
    }


    public void onLocationChanged(Location location) {
    	
		latitude = location.getLatitude();
		longitude = location.getLongitude();	          
		
		// This needs to stop getting the location data and save the battery power.
		locationManager.removeUpdates(this);
		//txtCoordenadas.setText( "Searching for matches "+latitude+" "+longitude);
		alertDialog.setMessage("Latitude: "+latitude.toString()+"; Longitude:"+longitude.toString());
		
		new RetreiveFeedTask().execute();
		
		
		/*EditText txtLatitude = (EditText) calleractivity.findViewById(idLatitude);		
		txtLatitude.setText(latitude.toString());*/
		
		//EditText txtLongitude = (EditText)calleractivity.findViewById(idAddress);
		//txtLongitude.setText(longitude.toString());
		
		//getJSONAddressFromGoogleMaps();
		
		alertDialog.dismiss();    	
    	
    }

   
    public void onProviderDisabled(String provider) {
    }

    
    public void onProviderEnabled(String provider) {
    }

    
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
        
    
    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            InputStream is  = null;
            //JSONObject jObj = null;
            String address = "";
            //String json     = "";
            try {
                // Making http request
                DefaultHttpClient httpClient = new DefaultHttpClient ( );
                HttpGet httpPost = new HttpGet ( "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=false" );//&region=" + region + "&language=" + language               
                HttpResponse httpResponse = httpClient.execute ( httpPost );
                HttpEntity httpEntity = httpResponse.getEntity ( );
                is = httpEntity.getContent ( );
            } catch ( UnsupportedEncodingException e ) {
                // Exception
            } catch ( ClientProtocolException e ) {
                // Exception
            } catch ( IOException e ) {
                // Exception
            }
            try {
                BufferedReader reader = new BufferedReader ( new InputStreamReader ( is, "UTF-8" ), 8 );
                //StringBuilder sb = new StringBuilder ( );
                String line = null;
                while ( ( line = reader.readLine ( ) ) != null ) {
                   // sb.append ( line ).append ( "\n" );
                    if(line.contains("formatted_address")){
                    	address = line.substring(32);
                    	address = address.substring(0, address.length()-2);
                    	break;
                    }
                }
                is.close ( );
               // json = sb.toString ( );
            } catch ( Exception e ) {
                // Exception
            }
            // Try parse the string to a JSON object
           /* try {
                jObj = new JSONObject ( json );
            } catch ( JSONException e ) {
                // Exception
            }*/
            // Return JSON String
            return address;

        }

        protected void onPostExecute(String address) {

    		EditText txtAddress = (EditText)calleractivity.findViewById(idAddress);
    		txtAddress.setText(address);
        }
    }
    


}