package com.NativeTech.rehla.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.activities.FindRide;
import com.NativeTech.rehla.activities.OfferRide;


public class Home extends Fragment {



    private Button find_ride;
    private Button offer_ride;






    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);


        find_ride                       = view.findViewById(R.id.find_ride);
        offer_ride                      = view.findViewById(R.id.offer_ride);


        find_ride.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),FindRide.class);
            startActivity(intent);
        });
        offer_ride.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),OfferRide.class);
            startActivity(intent);
        });


        return view;
    }

}
