package com.majinor.esportal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.majinor.esportal.adapter.NotifInviteAdapter;
import com.majinor.esportal.adapter.NotificationAdapter;
import com.majinor.esportal.model.NotifInviteModel;
import com.majinor.esportal.model.NotificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.majinor.esportal.Server.URL;
//import static com.majinor.esportal.LoginActivity.my_shared_preferences;

public class NotifInviteBackgroundTask extends AsyncTask<Void, NotifInviteModel, Void> {

    public static final String EXTRA_IDREGIS ="username" ;
    public static final String EXTRA_FOTO ="foto" ;
    public static final String EXTRA_NAMATOUR ="namatour" ;

    Context ctx;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private NotifInviteAdapter mNotificationAdapter;
    RecyclerView.LayoutManager layoutManager;
    LayoutInflater inflater;
    String username;
    SharedPreferences pref;
    ViewGroup container;

    ArrayList<NotifInviteModel> arrayList = new ArrayList<>();

    String json_url = URL+"shownotifinvite.php?username=";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        recyclerView = (RecyclerView) activity.findViewById(R.id.rv_met);
    }

    public NotifInviteBackgroundTask(Context ctxs, RecyclerView.Adapter adapter,
                                    ArrayList<NotifInviteModel> arrayList, String username){
        this.ctx = ctxs;
        this.adapter = adapter;
        this.arrayList = arrayList;
        this.username = username;
        activity = (Activity) ctx;

        pref = ctx.getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String usrnm=username;

        try {

            URL url = new URL(json_url+usrnm);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }

            httpURLConnection.disconnect();

            String json_url = stringBuilder.toString().trim();

            Log.d("JSON STRING", json_url);

            JSONObject jsonObject = new JSONObject(json_url);

            JSONArray jsonArray =  jsonObject.getJSONArray("result");
            int count = 0;

            while (count < jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                count++;

                NotifInviteModel notifInviteModel = new NotifInviteModel(
                        JO.getString("foto"),
                        JO.getInt("id"),
                        JO.getInt("idtour"),
                        JO.getInt("status"),
                        JO.getString("namatour"),
                        JO.getString("jenis"),
                        JO.getString("tgltour"),
                        JO.getString("tgldaftar"),
                        JO.getString("tgltm"),
                        JO.getInt("biaya"),
                         JO.getString("namatim"),
                        JO.getString("usrketua"),
                        JO.getString("usrang1"),
                        JO.getString("usrang2"),
                        JO.getString("usrang3"),
                        JO.getString("usrang4"),
                        JO.getString("ignketua"),
                        JO.getString("ignang1"),
                        JO.getString("ignang2"),
                        JO.getString("ignang3"),
                        JO.getString("ignang4"),
                        JO.getInt("accang1"),
                        JO.getInt("accang2"),
                        JO.getInt("accang3"),
                        JO.getInt("accang4"));

                publishProgress(notifInviteModel);
            }
            final Context ctxx=ctx;
//            mTournamentAdapter = new MyEventTourAdapter(ctxx, arrayList);
//            mTournamentAdapter.setOnItemClickListener(new MyEventTourAdapter.OnItemClickListener() {

            ;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(NotifInviteModel... values) {
        arrayList.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
