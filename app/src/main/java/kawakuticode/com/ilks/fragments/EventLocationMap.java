package kawakuticode.com.ilks.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import kawakuticode.com.ilks.Beans.Place;
import kawakuticode.com.ilks.R;

/**
 * Created by russeliusernestius on 20/11/17.
 */

public class EventLocationMap extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;

    private Marker mEvent;
    private LatLng mEventLatLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_activity_eventlocationmap);
        Intent location = getIntent();
        Place place = location.getParcelableExtra("location");
        mEventLatLng = new LatLng(Double.valueOf(place.getLocation().getLatitude()), Double.valueOf(place.getLocation().getLongitude()));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();


        if (clickCount != null) {

            Toast.makeText(this,
                    marker.getTitle() +
                            "your party place",
                    Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add some markers to the map, and add a data object to each marker.
        mEvent = mMap.addMarker(new MarkerOptions()
                .position(mEventLatLng)
                .title("event place"));
        mEvent.setTag(0);
    }
}
