package com.majinor.esportal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.majinor.esportal.adapter.HomeResultAdapter;
import com.majinor.esportal.model.HomeResultModel;

import java.util.ArrayList;

import static com.majinor.esportal.LoginActivity.TAG_ID;
import static com.majinor.esportal.LoginActivity.TAG_USERNAME;


public class HomeFragment extends Fragment {

    Context ctx;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<HomeResultModel> arrayList = new ArrayList<>();
    String id,username;
    Intent intentT, intentG, intentS, intentA;

    public static String KEY_USERNAME = "message";


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_homeresult);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() ));
        recyclerView.setHasFixedSize(true);

        adapter = new HomeResultAdapter(arrayList);

        recyclerView.setAdapter(adapter);

        HomeResultBackgroundTask homeResultBackgroundTask = new HomeResultBackgroundTask
                (getActivity(),adapter,arrayList);
        homeResultBackgroundTask.execute();



        final Button buttont = (Button) rootView.findViewById(R.id.btnTour);
        final Button buttons = (Button) rootView.findViewById(R.id.btnStream);
        final Button buttong = (Button) rootView.findViewById(R.id.btnGath);
        final Button buttona = (Button) rootView.findViewById(R.id.btneEsp);
//        final TextView buttona = (TextView) rootView.findViewById(R.id.btnEsp);

        buttont.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intentT = new Intent(getActivity(), TournamentActivity.class);
                startActivity(intentT);
            }
        });
        buttong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intentG = new Intent(getActivity(), GatheringActivity.class);
                startActivity(intentG);
            }
        });
        buttons.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intentS = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC1hJktBAAiuZqiwxKEHB3ww?view_as=subscriber"));
                startActivity(intentS);
            }
        });
        buttona.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intentA = new Intent(getActivity(), AboutActivity.class);
                startActivity(intentA);
                getActivity().overridePendingTransition(R.anim.faded_in,R.anim.faded_out);

            }
        });

        return rootView;




    }
}

