package com.makestore.minq.makestore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Customaersignin extends AppCompatActivity{
    private WebView web;
    private WebSettings webss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customersignin);

        web = (WebView)findViewById(R.id.cwebs);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);

        web.loadUrl("http://13.125.255.233:8888/clients/addSclient.html");
        web.reload();
    }
}
