package com.majinor.esportal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.majinor.esportal.adapter.LeaderboardAdapter;
import com.majinor.esportal.model.LeaderboardModel;

import java.util.ArrayList;

import static com.majinor.esportal.HomeFragment.KEY_USERNAME;
import static com.majinor.esportal.LoginActivity.TAG_ID;
import static com.majinor.esportal.LoginActivity.TAG_NAMA;
import static com.majinor.esportal.LoginActivity.TAG_POINT;
import static com.majinor.esportal.LoginActivity.TAG_USERNAME;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {


    Context ctx;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<LeaderboardModel> arrayList = new ArrayList<>();
    String id,username, name,point;

    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_leaderboard, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_lead);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() ));
        recyclerView.setHasFixedSize(true);

        adapter = new LeaderboardAdapter(arrayList);

        recyclerView.setAdapter(adapter);

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id= sharedpreferences.getString(TAG_ID,"null");
        username= sharedpreferences.getString(TAG_USERNAME,"null");
        name= sharedpreferences.getString(TAG_NAMA,"null");
        point= sharedpreferences.getString(TAG_POINT,"0");

        LeaderboardBackgroundTask leaderboardBackgroundTask = new LeaderboardBackgroundTask
                (getActivity(),adapter,arrayList, username);
        leaderboardBackgroundTask.execute();

        TextView myrank =rootView.findViewById(R.id.tv_lead_myno);
        TextView mynick =rootView.findViewById(R.id.tv_lead_mynama);
        TextView mypoint =rootView.findViewById(R.id.tv_lead_mypoint);

        mynick.setText(name);
        mypoint.setText("ESP. " +point);

        return rootView;
    }




}
