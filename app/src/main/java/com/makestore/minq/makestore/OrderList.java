package com.makestore.minq.makestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OrderList extends AppCompatActivity {
    WebView web;
    WebSettings webss;

    String sname,idss;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist);

        Intent i = getIntent();
        sname = i.getStringExtra("sname");
        idss = i.getStringExtra("id");

        web = (WebView)findViewById(R.id.orderls);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);
        //web.addJavascriptInterface(new Storeview.WebAppInterface(this),"Android");

        String str = null;
        try {
            str = "sname=" + URLEncoder.encode(sname, "UTF-8") + "&id=" + URLEncoder.encode(idss, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        web.postUrl("http://13.125.255.233:8887/clients/orderlist.php",str.getBytes());
        web.reload();
    }
}
