package netdb.courses.softwarestudio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;


/**
 * Created by Bill on 2016/6/19.
 */
public class SoloActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, OnMyLocationButtonClickListener
        , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private double current_lat = 25.03, current_log = 121.5631;
    private GoogleMap mMap;
    private UiSettings mui;
    private Vector<String> vec_lat = new Vector<String>();
    private Vector<String> vec_lng = new Vector<String>();
    private Vector<String> vec_name = new Vector<String>();
    private RequestQueue requestqueue;
    private GoogleApiClient google_api_client;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Toast.makeText(SoloActivity.this, R.string.permission_required_toast, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        requestqueue = Volley.newRequestQueue(this);

        if (google_api_client == null) {
            google_api_client = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        LatLng TapeiCityHall = new LatLng(25.03, 121.5631);
        map.addMarker(new MarkerOptions().position(TapeiCityHall).title("Start Point"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(TapeiCityHall, 13));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            map.setMyLocationEnabled(true);
        mMap = map;
        mui = mMap.getUiSettings();
        mui.setMapToolbarEnabled(true);
        //
    }

    @Override
    protected void onResume() {
        super.onResume();
        google_api_client.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (google_api_client.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(google_api_client, this);
            google_api_client.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(this, "Connection suspended...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Make sure the app is not already connected or attempting to connect
            if (!google_api_client.isConnecting() &&
                    !google_api_client.isConnected()) {
                google_api_client.connect();
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Google Play Services must be installed.",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onConnected(Bundle arg0) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(google_api_client);
        current_lat = location.getLatitude();
        current_log = location.getLongitude();
        onSearch();
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(google_api_client, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
        onSearch();
    }
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
    private void handleNewLocation(Location location) {

         current_lat = location.getLatitude();
         current_log = location.getLongitude();
        LatLng latLng = new LatLng(current_lat, current_log);
        onSearch();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
    private void onSearch()
    {
        System.out.println("search to "+current_lat+" , " + current_log);
        String location = current_lat + ","+ current_log;
        String types = "stadium|school|gym|park";
        String name = "";
        String api_param_str = "?location=" + location + "&radius="
                + "500"  + "&types=" + types +"&name=" +name
                + "&sensor=false&key=" + "AIzaSyDp5GC80emtraC7Dm1j43wdGXbFJ9GXkAg";
        String api_bash_url = "https://maps.googleapis.com/maps/api/place/search/json";

         vec_lat.clear();
         vec_lng.clear();
         vec_name.clear();//api_bash_url+api_param_str
            System.out.println("searching in "+ api_bash_url+api_param_str);
         JsonObjectRequest jsonrequest = new JsonObjectRequest(api_bash_url+api_param_str,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {System.out.println("start parsing");
                            JSONArray jsonarray =response.getJSONArray("results");
                            System.out.println("size:"+jsonarray.length());
                            for(int i=0;i<jsonarray.length();i++)
                            {
                                JSONObject results = jsonarray.getJSONObject(i);

                                JSONObject geometry = results.getJSONObject("geometry");
                                JSONObject location =geometry.getJSONObject("location");

                                String st_lat = location.getString("lat");
                                String st_lng = location.getString("lng");
                                String name = results.getString("name");
                                System.out.println("add:"+st_lat+","+st_lng+":"+name);
                                vec_lat.add(st_lat);
                                vec_lng.add(st_lng);
                                vec_name.add(name);
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        finally {
                            if(vec_name.size() !=0)
                                for(int i = 0;i<vec_lat.size();i++)
                                {
                                    double lat =Double.parseDouble(vec_lat.get(i));
                                    double lng =Double.parseDouble(vec_lng.get(i));
                                    LatLng latlng = new LatLng(lat,lng);
                                    System.out.println("lat:"+vec_lat.get(i)+"lng:"+vec_lng.get(i) + "\nname:"+vec_name.get(i));
                                    mMap.addMarker(new MarkerOptions().position(latlng).title(vec_name.get(i)));
                                }

                        }
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                        Log.e("Volley","error");
                }
        });
        requestqueue.add(jsonrequest);
    }
}