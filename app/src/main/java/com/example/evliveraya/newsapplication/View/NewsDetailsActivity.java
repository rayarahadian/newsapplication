package com.example.evliveraya.newsapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.evliveraya.newsapplication.R;

public class NewsDetailsActivity extends AppCompatActivity {

    private Bundle bundle;
    private String url;
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        progressBar = (ProgressBar) findViewById(R.id.news_details_progressbar);
        webView = (WebView) findViewById(R.id.news_details_webview);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.loadUrl(url);
            webView.setWebChromeClient(new WebChromeClient(){
                public void onProgressChanged(WebView view, int progress) {
                    if (progress != 100) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

        } else {
            url = "null";
            Toast toast = Toast.makeText(getApplicationContext(), "Sorry news is not available", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
