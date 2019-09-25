package com.majinor.esportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class MyEventFragment extends Fragment {
    FrameLayout frgTour, frgGath;
    View view1, view2;
    TextView txttour, txtgath;
    Intent intentmywait;
    public static String KEY_USERNAME = "msg_activity";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_event, container, false);
        //INIT VIEWS
        init(rootView);
        //SET TABS ONCLICK
        frgTour.setOnClickListener(clik);
        frgGath.setOnClickListener(clik);
        //LOAD PAGE FOR FIRST
        loadPage(new MyEventTourFragment());
        txttour.setTextColor(getActivity().getResources().getColor(R.color.white));

        final Button setting = (Button) rootView.findViewById(R.id.btn_my_waiting);
        setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), v);
                popupMenu.inflate(R.menu.option_menu_myevent);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.waiting:
                                Intent intdn = new Intent(getActivity(), MyEventWaitingActivity.class); // Your nxt activity name instead of List_Activity
                                intdn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getActivity().startActivity(intdn);
                                break;

                            case R.id.history:
                                Intent intdn2 = new Intent(getActivity(), MyEventTourHistoryActivity.class); // Your nxt activity name instead of List_Activity
                                intdn2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                getActivity().startActivity(intdn2);
                                break;


                            default:
                                break;

                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });



        return rootView;
    }
    public void init(View v){
        frgTour = v.findViewById(R.id.tabTour);
        frgGath = v.findViewById(R.id.tabGath);
        view1 = v.findViewById(R.id.view1);
        view2 = v.findViewById(R.id.view2);
        txttour = v.findViewById(R.id.txtTour);
        txtgath = v.findViewById(R.id.txtGath);
    }
    //ONCLICK LISTENER
    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.tabGath:
                    //ONBUYER CLICK
                    //LOAD BUYER FRAGMENT CLASS
                    loadPage(new MyEventGathFragment());

                    //WHEN CLICK TEXT COLOR CHANGED
                    txttour.setTextColor(getActivity().getResources().getColor(R.color.gray));
                    txtgath.setTextColor(getActivity().getResources().getColor(R.color.white));
                    //VIEW VISIBILITY WHEN CLICKED
                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    break;
                case R.id.tabTour:
                    //ON TOUR CLICK
                    //LOAD TOUR FRAGMENT CLASS
                    loadPage(new MyEventTourFragment());
                    //WHEN CLICK TEXT COLOR CHANGED
                    txttour.setTextColor(getActivity().getResources().getColor(R.color.white));
                    txtgath.setTextColor(getActivity().getResources().getColor(R.color.gray));
                    //VIEW VISIBILITY WHEN CLICKED
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };
    //LOAD PAGE FRAGMENT METHOD
    private boolean loadPage(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerpage, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}