package com.example.sira.projectapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class SiraMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int position = -1;
    String locname = null;
    LatLng myLocation;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sira_maps);
        Intent intentxtra = getIntent();
        Bundle bundle = intentxtra.getExtras();

        final EditText siratext = (EditText)findViewById(R.id.editText);
        siratext.setText("");
        siratext.setFocusableInTouchMode(true);
        siratext.requestFocus();


        if (bundle!=null) {
            Log.d("Bundle is not null","confirmed" );
            final String finalname[] = bundle.getStringArray("finalname");
         final   String finallat[] = bundle.getStringArray("finalLat");
           final String finallong[] = bundle.getStringArray("finallong");
            value = bundle.getString("value");

            for(int i=0; i<finalname.length; i++)
            {
                if(value.equals(finalname[i])) { position = i; break; }
            }


            double latitude = Double.parseDouble(finallat[position]);
            double longitude = Double.parseDouble(finallong[position]);
            locname = finalname[position];
            myLocation = new LatLng(latitude,longitude);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            ///value not from bundle
           if(siratext.getText().toString() != "")
            siratext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
              @Override
              public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                        value = siratext.getText().toString();

                            for(int i=0; i<finalname.length; i++)
                            {
                                if(value.equalsIgnoreCase(finalname[i])) { position = i; break; }
                            }

                        double latitude = Double.parseDouble(finallat[position]);
                        double longitude = Double.parseDouble(finallong[position]);
                        locname = finalname[position];
                        myLocation = new LatLng(latitude,longitude);

                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(SiraMapsActivity.this);



                        Log.d("siratext", value);
                        return true;
                    }
                    return false;
                }
            });











            } // end of bundle check
// location manager in oncreate


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    } // end of onCreate



//LOCATION FINDER END.

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().isCompassEnabled();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions().position(myLocation).title(" This is " + locname).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));


    }




}
