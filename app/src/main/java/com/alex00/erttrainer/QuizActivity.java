package com.alex00.erttrainer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    private Adapter adapter;
    private RecyclerView Rv;
    private ScrollView ScView;
    private TextView Pre_view,Pre_con,Con,Bool,CtDn,u;
    private Button Btconfirm;
    private RadioGroup Rg;
    private RadioButton o1,o2,o3,o4;
    private RequestQueue rq;
    private CountDownTimer countDownTimer;

    private long backPress,timeleft;
    private ArrayList<Preguntas> preguntasList;
    private byte ConPreg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        preguntasList =new ArrayList<>();

        u=findViewById(R.id.url);

        Rv=findViewById(R.id.rv);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new LinearLayoutManager(this));

        Con=findViewById(R.id.Cont);
        CtDn=findViewById(R.id.CountDown);
        Bool=findViewById(R.id.bool);
        ScView=findViewById(R.id.Scview);
        Pre_view=findViewById(R.id.PregView);
        Pre_con=findViewById(R.id.Pre_Con);
        Btconfirm=findViewById(R.id.boton);
        Rg=findViewById(R.id.RG);
        o1=findViewById(R.id.b1);
        o2=findViewById(R.id.b2);
        o3=findViewById(R.id.b3);
        o4=findViewById(R.id.b4);
        rq= Volley.newRequestQueue(this);

        showNext();

        Btconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (o1.isChecked() || o2.isChecked() || o3.isChecked() || o4.isChecked()) {
                    ConPreg++;
                    checkAnswer();
                    showNext();
                } else {
                    Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void readJSON(){
        final String url ="https://raw.githubusercontent.com/alejandro-00/Test_git_sis/master/opt/questionario.json";

        JsonObjectRequest r=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ja=response.getJSONArray("cuestionario");
                    JSONObject pregunta = ja.getJSONObject(ConPreg);
                    int a=pregunta.getInt("CORRECTA");
                    Pre_view.setText(pregunta.getString("PREGUNTA"));
                    o1.setText(pregunta.getString("OPCION_1"));
                    o2.setText(pregunta.getString("OPCION_2"));
                    o3.setText(pregunta.getString("OPCION_3"));
                    o4.setText(pregunta.getString("OPCION_4"));
                    Con.setText(""+a);
                    if (o3.getText().equals("null")){
                        o3.setVisibility(View.GONE);
                    } else o3.setVisibility(View.VISIBLE);
                    if (o4.getText().equals("null")){
                        o4.setVisibility(View.GONE);
                    } else o4.setVisibility(View.VISIBLE);
                    Pre_con.setText((ConPreg+1)+"/100");
                    u.setText(pregunta.getString("URL"));

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
        rq.add(r);
    }

    private void checkAnswer(){
        countDownTimer.cancel();
        RadioButton selected = findViewById(Rg.getCheckedRadioButtonId());
        int select = Rg.indexOfChild(selected)+1;
        if (select == Integer.parseInt(String.valueOf(Con.getText())) ){
            boolean f=true;
            switch ((select-1)){
                case 0: preguntasList.add(new Preguntas(ConPreg,String.valueOf(Pre_view.getText()),String.valueOf(o1.getText()),String.valueOf(o1.getText()) , f,String.valueOf(u.getText()))); break;
                case 1: preguntasList.add(new Preguntas(ConPreg,String.valueOf(Pre_view.getText()),String.valueOf(o2.getText()),String.valueOf(o2.getText()) ,f,String.valueOf(u.getText()))); break;
                case 2: preguntasList.add(new Preguntas(ConPreg,String.valueOf(Pre_view.getText()),String.valueOf(o3.getText()),String.valueOf(o3.getText()) , f,String.valueOf(u.getText()))); break;
                case 3: preguntasList.add(new Preguntas(ConPreg,String.valueOf(Pre_view.getText()),String.valueOf(o4.getText()),String.valueOf(o4.getText()) , f,String.valueOf(u.getText()))); break;
            }
        }
        else {
            boolean f=false;
            String answer="";

            switch ((select-1)){
                case 0: answer=String.valueOf(o1.getText()); break;
                case 1: answer=String.valueOf(o2.getText()); break;
                case 2: answer=String.valueOf(o3.getText()); break;
                case 3: answer=String.valueOf(o4.getText()); break;
            }

            switch ((Integer.parseInt(String.valueOf(Con.getText()))-1)){
                case 0: preguntasList.add(new Preguntas(ConPreg,String.valueOf(Pre_view.getText()),answer,String.valueOf(o1.getText()), f,String.valueOf(u.getText()))); break;
                case 1: preguntasList.add(new Preguntas(ConPreg,String.valueOf(Pre_view.getText()),answer,String.valueOf(o2.getText()), f,String.valueOf(u.getText()))); break;
                case 2: preguntasList.add(new Preguntas(ConPreg,String.valueOf(Pre_view.getText()),answer,String.valueOf(o3.getText()), f,String.valueOf(u.getText()))); break;
                case 3: preguntasList.add(new Preguntas(ConPreg,String.valueOf(Pre_view.getText()),answer,String.valueOf(o4.getText()), f,String.valueOf(u.getText()))); break;
            }
        }
    }

    private void showNext(){
        readJSON();
        Rg.clearCheck();
        if (ConPreg!=100){
            timeleft=31000;
            startCountDown();
            Btconfirm.setText("Confirm");
        }
        else {
            //finish();
            countDownTimer.cancel();
            CtDn.setVisibility(View.GONE);
            ScView.setVisibility(View.GONE);
            Rv.setVisibility(View.VISIBLE);
            Rg.setVisibility(View.GONE);
            Pre_view.setVisibility(View.GONE);
            Pre_con.setVisibility(View.GONE);
            Btconfirm.setVisibility(View.GONE);
            adapter=new Adapter(QuizActivity.this,preguntasList);
            Rv.setAdapter(adapter);
            adapter.setOnItemClickListener(QuizActivity.this);
        }
    }

    public void startCountDown(){
        countDownTimer=new CountDownTimer(timeleft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleft=millisUntilFinished;
                updateCountdownView();
            }

            @Override
            public void onFinish() {
                timeleft=0;
                updateCountdownView();
                ConPreg++;
                checkAnswer();
                showNext();
            }
        }.start();
    }

    public void updateCountdownView(){
        byte minutes = (byte) ((timeleft/1000)/60);
        byte seconds = (byte) ((timeleft/1000)%60);
        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        CtDn.setText((timeFormatted));
    }

    public void onBackPressed() {
        if (backPress+2000>System.currentTimeMillis()){
            finishQuiz();
        }
        else {Toast.makeText(this,"Press back again to exit",Toast.LENGTH_LONG).show();}
        backPress=System.currentTimeMillis();
    }

    /*protected void onDestroy() {
        super.onDestroy();
        finishQuiz();
    }

    protected void onPause(){
        super.onPause();
        finishQuiz();
    }

    protected void onStop(){
        super.onStop();
        finishQuiz();
    }*/

    public void finishQuiz(){
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
        finish();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this,DetailQuestion.class);
        Preguntas clickedItem = preguntasList.get(position);
        intent.putExtra("pregunta",clickedItem.getP());
        intent.putExtra("a",clickedItem.getO1());
        intent.putExtra("c",clickedItem.getC());
        intent.putExtra("b",clickedItem.getBool());
        intent.putExtra("url",clickedItem.getUrl());
        startActivity(intent);
    }
}
