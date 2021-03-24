package com.example.countriesinfo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.countriesinfo.Adapter.MainAdapter;
import com.example.countriesinfo.Model.MainData;
import com.example.countriesinfo.Model.RoomDB;
import com.example.countriesinfo.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<MainData> list;
    MainAdapter mainAdapter;
    ProgressBar progressBar;
    RoomDB database;
    ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressbar);
        button = findViewById(R.id.deleteBtn);
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        list = new ArrayList<MainData>();
        database = RoomDB.getInstance(this);
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            progressBar.setVisibility(View.INVISIBLE);
            list = (ArrayList<MainData>) database.mainDao().getAll();
            mainAdapter = new MainAdapter(MainActivity.this, list);
            recyclerView.setAdapter(mainAdapter);
            Toast.makeText(MainActivity.this, "Network Not Connected!", Toast.LENGTH_SHORT).show();
        }else {
            String url = "https://restcountries.eu/rest/v2/region/asia";

            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response != null) {
                        try {
                            progressBar.setVisibility(View.INVISIBLE);
                            JSONArray jsonArray = new JSONArray(response);
                            parseArray(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(request);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().reset(list);
                list.clear();
                list.addAll(database.mainDao().getAll());
                mainAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Data delete Successfully!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void parseArray(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                MainData mainData = new MainData();
                mainData.setName(object.getString("name"));
                mainData.setCapital(object.getString("capital"));
                mainData.setRegion(object.getString("region"));
                mainData.setSubregion(object.getString("subregion"));
                mainData.setPopulation(object.getInt("population"));
                mainData.setFlag(object.getString("flag"));
                String languages = object.getString("languages");
                JSONArray jsonArray1 = new JSONArray(languages);
                StringBuffer lang = new StringBuffer();
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject object1 = jsonArray1.getJSONObject(j);
                    lang.append(object1.getString(("name")));
                    if(j<jsonArray1.length()-1) {
                        lang.append(",");
                    }
                }
                mainData.setLanguages(new String(lang));
                String border = object.getString("borders");
                mainData.setBorders(border.substring(1, border.length() - 1));
                list.add(mainData);
                database.mainDao().insert(mainData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mainAdapter = new MainAdapter(MainActivity.this, list);
        }
        recyclerView.setAdapter(mainAdapter);
    }

}

