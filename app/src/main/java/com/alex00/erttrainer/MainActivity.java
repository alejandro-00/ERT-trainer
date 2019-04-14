package com.alex00.erttrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> p=new ArrayList<>();
    private  List<String> r1=new ArrayList<>();
    private  List<String> r2=new ArrayList<>();
    private  List<String> r3=new ArrayList<>();
    private  List<String> r4=new ArrayList<>();
    private List<Integer> a=new ArrayList<>();
    private RequestQueue mq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView welcome,warning;
        setContentView(R.layout.activity_main);
        welcome=findViewById(R.id.wlm);
        warning=findViewById(R.id.wrn);
        Button t=findViewById(R.id.test);
        Button p=findViewById(R.id.practice);

        //mq= Volley.newRequestQueue(this);
        //rdJSON();
        //readJSON();

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,QuizActivity.class);
                startActivity(intent);
                //readJSON();
            }
        });
    }



    private void readJSON(){
        String url ="https://raw.githubusercontent.com/alejandro-00/Test_git_sis/master/opt/questionario.json";

        JsonObjectRequest r=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ja=response.getJSONArray("cuestionario");
                    for (int i=0; i < ja.length(); i++){
                        JSONObject pregunta = ja.getJSONObject(i);
                        p.add(pregunta.getString("PREGUNTA"));
                        r1.add(pregunta.getString("OPCION_1"));
                        r2.add(pregunta.getString("OPCION_2"));
                        r3.add(pregunta.getString("OPCION_3"));
                        r4.add(pregunta.getString("OPCION_4"));
                        a.add(pregunta.getInt("CORRECTA"));
                        //text.setText(p.get(i)+" "+r1.get(i)+" "+r2.get(i)+" "+r3.get(i)+" "+r4.get(i)+" "+a.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mq.add(r);
    }


}
