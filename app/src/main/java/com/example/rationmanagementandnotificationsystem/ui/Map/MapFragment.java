package com.example.rationmanagementandnotificationsystem.ui.Map;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.rationmanagementandnotificationsystem.Home_Page;
import com.example.rationmanagementandnotificationsystem.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    GoogleMap mMap;
    MapView mapView;
    View root;

   // static final LatLng HAMBURG = new LatLng(53.558, 9.927);
   // static final LatLng KIEL = new LatLng(53.551, 9.993);

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_map, container, false);
        ((Home_Page)this.getActivity()).getSupportActionBar().setTitle("Geo Location");
        //isServiceOK();

        //map =  getChildFragmentManager().findFragmentById(R.id.map));

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap=googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear(); //clear old markers

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(8.463150,77.559659))
                        .zoom(16)
                        .bearing(0)
                        .tilt(45)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 2000, null);

                //mMap.addMarker(new MarkerOptions().position(new LatLng(8.463150, 77.559659)).title("Spider Man").icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_check_circle_black_24dp)));

                //mMap.addMarker(new MarkerOptions().position(new LatLng(8.463150,77.559659)).title("Iron Man").snippet("His Talent : Plenty of money"));

                mMap.addMarker(new MarkerOptions().position(new LatLng(8.463150,77.559659)).title("Ration Shop"));
            }
        });

        Log.d("maptag","onCreateView");


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*mapView = root.findViewById(R.id.map);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);

        }*/


    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        //MapsInitializer.initialize(getContext());

        map=googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.clear(); //clear old markers

        //map.addMarker(new MarkerOptions().position(new LatLng(40,74)).title("one").snippet("i hope"));
        //CameraPosition libert= CameraPosition.builder().target(new LatLng(40.689247,-74.044502)).zoom(16).bearing(0).tilt(45).build();

        //map.moveCamera(CameraUpdateFactory.newCameraPosition(libert));
        Log.d("maptag","onMapReady");

        map = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/

    public boolean isServiceOK(){

        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());

        if(available== ConnectionResult.SUCCESS){
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Toast.makeText(getContext(),"can't",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}