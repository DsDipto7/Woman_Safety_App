package com.example.security_project;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Stations extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    ArrayList<String>branch_names;
    ArrayList<Address>addresses;

    int position =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);



        branch_names=new ArrayList<>();
        addresses=new ArrayList<>();

        listView=findViewById(R.id.list);

        extractData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendInfo(i);
            }
        });
    }

    private void sendInfo(int i) {

        Address foundAddress= addresses.get(i);
        String Thana=foundAddress.getThana();

        String Mobile= foundAddress.getMobile();
        String RoadNo= foundAddress.getRoadNo();




        Intent intent=new Intent(Stations.this, StationsDetails.class);
        intent.putExtra("Thana",Thana);

        intent.putExtra("Mobile",Mobile);
        intent.putExtra("RoadNo", RoadNo);


        startActivity(intent);
    }

    private void extractData() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://api.myjson.online/v1/records/b606cdf0-6359-4d80-8231-f5e50aaf6316";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonParse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    private void jsonParse(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject jsonObject1=jsonObject.getJSONObject("data");
            JSONArray jsonArray=jsonObject1.getJSONArray("police_stations");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                String Name=jsonObject2.getString("name");

                JSONObject jsonObject3=jsonObject2.getJSONObject("address");

                String thana = jsonObject3.getString("thana");
                String roadNo = jsonObject3.getString("road_no");
                String contactNo = jsonObject3.getString("contact_no");





                Address address = new Address(thana,roadNo,contactNo);
                addresses.add(address);
                branch_names.add(Name);

            }
            ArrayAdapter<String> adapter=new ArrayAdapter<>(Stations.this,android.R.layout.simple_list_item_1,branch_names);
            listView.setAdapter(adapter);

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}