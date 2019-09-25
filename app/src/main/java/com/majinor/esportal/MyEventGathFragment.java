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

import com.majinor.esportal.adapter.MyEventGathAdapter;
import com.majinor.esportal.adapter.MyEventTourAdapter;
import com.majinor.esportal.model.MyEventGathModel;
import com.majinor.esportal.model.MyEventTourModel;
import com.majinor.esportal.model.TournamentModel;

import java.util.ArrayList;

import static com.majinor.esportal.HomeFragment.KEY_USERNAME;
import static com.majinor.esportal.LoginActivity.TAG_ID;
import static com.majinor.esportal.LoginActivity.TAG_USERNAME;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEventGathFragment extends Fragment {

    public static final String EXTRA_IDFOLLOW ="idfollow" ;
    public static final String EXTRA_IDGATH ="idgath" ;
    public static final String EXTRA_FOTO ="foto" ;
    public static final String EXTRA_NAMAGATH ="namagath" ;
    public static final String EXTRA_DESK ="desk" ;
    public static final String EXTRA_JENIS ="jenis" ;
    public static final String EXTRA_TEMPAT ="tempat" ;
    public static final String EXTRA_TGLTM ="tgltm" ;
    public static final String EXTRA_TGLGATH ="tglgath" ;
    public static final String EXTRA_TGLDAFTAR ="tgldaftar" ;
    public static final String EXTRA_BIAYA ="biaya" ;
    public static final String EXTRA_NAMATIM ="namatim" ;
    public static final String EXTRA_NICK ="nick" ;
    public static final String EXTRA_NOHP ="nohp" ;

    Context ctx;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<MyEventGathModel> arrayList = new ArrayList<>();
    String id,username;

    public MyEventGathFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_my_event_gath, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_meg);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() ));
        recyclerView.setHasFixedSize(true);

        adapter = new MyEventGathAdapter(arrayList);

        recyclerView.setAdapter(adapter);

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id= sharedpreferences.getString(TAG_ID,"null");
        username= sharedpreferences.getString(TAG_USERNAME,"null");

        MyEventGathBackgroundTask myEventGathBackgroundTask = new MyEventGathBackgroundTask
                (getActivity(),adapter,arrayList, id);
        myEventGathBackgroundTask.execute();

        return rootView;
    }




}
