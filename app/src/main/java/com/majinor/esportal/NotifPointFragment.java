package com.majinor.esportal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.majinor.esportal.adapter.NotificationAdapter;
import com.majinor.esportal.model.NotificationModel;

import java.util.ArrayList;

import static com.majinor.esportal.LoginActivity.TAG_ID;
import static com.majinor.esportal.LoginActivity.TAG_USERNAME;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifPointFragment extends Fragment {

    public static final String EXTRA_IDREGIS ="idregis" ;
    public static final String EXTRA_FOTO ="foto" ;
    public static final String EXTRA_NAMATOUR ="namatour" ;
    public static final String EXTRA_JENIS ="jenis" ;
    public static final String EXTRA_TEMPAT ="tempat" ;
    public static final String EXTRA_TGLTM ="tgltm" ;
    public static final String EXTRA_TGLTOUR ="tgltour" ;
    public static final String EXTRA_TGLDAFTAR ="tgldaftar" ;
    public static final String EXTRA_BIAYA ="biaya" ;
    public static final String EXTRA_NAMATIM ="namatim" ;
    public static final String EXTRA_USRKETUA ="usrketua" ;
    public static final String EXTRA_USRANG1 ="usrang1" ;
    public static final String EXTRA_USRANG2 ="usrang2" ;
    public static final String EXTRA_USRANG3 ="usrang3" ;
    public static final String EXTRA_USRANG4 ="usrang4" ;
    public static final String EXTRA_IGNKETUA ="ignketua" ;
    public static final String EXTRA_IGNANG1 ="ignusr1" ;
    public static final String EXTRA_IGNANG2 ="ignusr1" ;
    public static final String EXTRA_IGNANG3 ="ignusr1" ;
    public static final String EXTRA_IGNANG4 ="ignusr4" ;

    Context ctx;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<NotificationModel> arrayList = new ArrayList<>();
    String id,username;

    public NotifPointFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_notif_point, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_notif);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() ));
        recyclerView.setHasFixedSize(true);

        adapter = new NotificationAdapter(arrayList);

        recyclerView.setAdapter(adapter);

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id= sharedpreferences.getString(TAG_ID,"null");
        username= sharedpreferences.getString(TAG_USERNAME,"null");

        NotifPointBackgroundTask NotifPointBackgroundTask = new NotifPointBackgroundTask
                (getActivity(),adapter,arrayList, id);
        NotifPointBackgroundTask.execute();

        return rootView;
    }




}
