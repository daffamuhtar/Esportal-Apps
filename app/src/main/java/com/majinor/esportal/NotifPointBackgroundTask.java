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

import com.majinor.esportal.adapter.NotificationAdapter;
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

public class NotifPointBackgroundTask extends AsyncTask<Void, NotificationModel, Void> {

    public static final String EXTRA_IDREGIS ="username" ;
    public static final String EXTRA_FOTO ="foto" ;
    public static final String EXTRA_NAMATOUR ="namatour" ;

    Context ctx;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private NotificationAdapter mNotificationAdapter;
    RecyclerView.LayoutManager layoutManager;
    LayoutInflater inflater;
    String iduser;
    SharedPreferences pref;
    ViewGroup container;

    ArrayList<NotificationModel> arrayList = new ArrayList<>();

    String json_url = URL+"shownotifpoint.php?iduser=";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        recyclerView = (RecyclerView) activity.findViewById(R.id.rv_met);
    }

    public NotifPointBackgroundTask(Context ctxs, RecyclerView.Adapter adapter,
                                    ArrayList<NotificationModel> arrayList, String id){
        this.ctx = ctxs;
        this.adapter = adapter;
        this.arrayList = arrayList;
        this.iduser = id;
        activity = (Activity) ctx;

        pref = ctx.getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String id=iduser;

        try {

            URL url = new URL(json_url+id);
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

                NotificationModel leaderboardModel = new NotificationModel(
                        JO.getString("tglupdate"),
                        JO.getInt("point"));

                publishProgress(leaderboardModel);
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
    protected void onProgressUpdate(NotificationModel... values) {
        arrayList.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
