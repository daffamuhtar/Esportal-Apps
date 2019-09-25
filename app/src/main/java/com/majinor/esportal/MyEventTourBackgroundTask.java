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

import com.majinor.esportal.adapter.MyEventTourAdapter;
import com.majinor.esportal.model.MyEventTourModel;

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

public class MyEventTourBackgroundTask extends AsyncTask<Void, MyEventTourModel, Void> {

    public static final String EXTRA_IDREGIS ="username" ;
    public static final String EXTRA_FOTO ="foto" ;
    public static final String EXTRA_NAMATOUR ="namatour" ;
    public static final String EXTRA_JENIS ="jenis" ;
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
    private MyEventTourAdapter mTournamentAdapter;
    RecyclerView.LayoutManager layoutManager;
    LayoutInflater inflater;
    String user;
    SharedPreferences pref;
    ViewGroup container;

    ArrayList<MyEventTourModel> arrayList = new ArrayList<>();

    String json_url = URL+"showmyregistedtour.php?username=";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        recyclerView = (RecyclerView) activity.findViewById(R.id.rv_met);
    }

    public MyEventTourBackgroundTask(Context ctxs, RecyclerView.Adapter adapter,
                                     ArrayList<MyEventTourModel> arrayList, String username){
        this.ctx = ctxs;
        this.adapter = adapter;
        this.arrayList = arrayList;
        this.user = username;
        activity = (Activity) ctx;

        pref = ctx.getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String username=user;
//        String json_url = URL+"showmyregistedtour.php?username=muhtarelo"+username;


        try {

            URL url = new URL(json_url+username);
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

                MyEventTourModel myEventTourModel = new MyEventTourModel(
                        JO.getString("foto"),
                        JO.getInt("id"),
                        JO.getInt("idtour"),
                        JO.getInt("idgame"),
                        JO.getInt("status"),
                        JO.getString("namatour"),
                        JO.getString("jenis"),
                        JO.getString("tempat"),
                        JO.getString("tgltour"),
                        JO.getString("tgldaftar"),
                        JO.getString("tgltm"),
                        JO.getInt("biaya"),
                        JO.getInt("hadiah"),
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
                        JO.getString("ignang4"));

                publishProgress(myEventTourModel);
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
    protected void onProgressUpdate(MyEventTourModel... values) {
        arrayList.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
