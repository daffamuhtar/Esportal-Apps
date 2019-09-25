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

public class NotificationFragment extends Fragment {
    FrameLayout frgInvite, frgPoint;
    View view1, view2;
    TextView txtInvite, txtPoint;
    Intent intentmywait;
    public static String KEY_USERNAME = "msg_activity";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        //INIT VIEWS
        init(rootView);
        //SET TABS ONCLICK
        frgInvite.setOnClickListener(clik);
        frgPoint.setOnClickListener(clik);
        //LOAD PAGE FOR FIRST
        loadPage(new NotifInviteFragment());
        txtInvite.setTextColor(getActivity().getResources().getColor(R.color.white));

//        final Button setting = (Button) rootView.findViewById(R.id.btn_my_waiting);
//        setting.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), v);
//                popupMenu.inflate(R.menu.option_menu_myevent);
//                popupMenu.show();
//
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.waiting:
//                                Intent intdn = new Intent(getActivity(), MyEventWaitingActivity.class); // Your nxt activity name instead of List_Activity
//                                intdn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                getActivity().startActivity(intdn);
//                                break;
//
//                            case R.id.history:
//                                Intent intdn2 = new Intent(getActivity(), MyEventTourHistoryActivity.class); // Your nxt activity name instead of List_Activity
//                                intdn2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                getActivity().startActivity(intdn2);
//                                break;
//
//
//                            default:
//                                break;
//
//                        }
//                        return true;
//                    }
//                });
//                popupMenu.show();
//
//            }
//        });



        return rootView;
    }
    public void init(View v){
        frgInvite = v.findViewById(R.id.tabInvite);
        frgPoint = v.findViewById(R.id.tabPoint);
        view1 = v.findViewById(R.id.view1);
        view2 = v.findViewById(R.id.view2);
        txtInvite = v.findViewById(R.id.txtInvite);
        txtPoint = v.findViewById(R.id.txtPoint);
    }
    //ONCLICK LISTENER
    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.tabPoint:
                    //ONBUYER CLICK
                    //LOAD BUYER FRAGMENT CLASS
                    loadPage(new NotifPointFragment());

                    //WHEN CLICK TEXT COLOR CHANGED
                    txtInvite.setTextColor(getActivity().getResources().getColor(R.color.gray));
                    txtPoint.setTextColor(getActivity().getResources().getColor(R.color.white));
                    //VIEW VISIBILITY WHEN CLICKED
                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    break;
                case R.id.tabInvite:
                    //ON TOUR CLICK
                    //LOAD TOUR FRAGMENT CLASS
                    loadPage(new NotifInviteFragment());
                    //WHEN CLICK TEXT COLOR CHANGED
                    txtInvite.setTextColor(getActivity().getResources().getColor(R.color.white));
                    txtPoint.setTextColor(getActivity().getResources().getColor(R.color.gray));
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