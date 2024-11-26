package com.alex00.erttrainer;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailQuestion extends AppCompatActivity {

    private WebView webView;
    private TextView pregunta;
    private String p;
    private Button button;

    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_question);

        button=findViewById(R.id.DB);
        pregunta= findViewById(R.id.DQ);
        TextView a= findViewById(R.id.DA);
        TextView c = findViewById(R.id.DC);

        Intent intent = getIntent();

        p = intent.getStringExtra("pregunta");
        String u = intent.getStringExtra("url");
        String aa = intent.getStringExtra("a");
        String cc = intent.getStringExtra("c");
        ImageView imageView=findViewById(R.id.DI);
        pregunta.setText(p);
        a.setText("Your answer: "+aa);
        c.setText("Correct answer: "+cc);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @SuppressLint("SetTextI18n")
            public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {
                pregunta.setText("Oh no! " + description);
                button.setVisibility(View.VISIBLE);
                button.setText("Retry");
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view1){
                        pregunta.setText(p);
                        webView.reload();
                        button.setVisibility(View.GONE);
                    }
                });
            }
        });
        if(!u.equals("null")){
            webView.loadUrl(u);
        }else{
            u="https://cataas.com/cat/says/this%20part%20doesn't%20have%20a%20practice%20link";
            webView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(this).load(u).into(imageView);
        }

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
