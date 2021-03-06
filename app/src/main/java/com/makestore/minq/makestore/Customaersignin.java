package com.makestore.minq.makestore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Customaersignin extends AppCompatActivity{
    private WebView web;
    private WebSettings webss;
    String storename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customersignin);


        Intent i = getIntent();
        storename = i.getStringExtra("storename");

        web = (WebView)findViewById(R.id.cwebs);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);

        web.addJavascriptInterface(new Customaersignin.WebAppInterface(this),"Android");

        web.loadUrl("http://13.125.255.233:8887/clients/addSclient.php?storename="+storename);
        web.reload();
    }
    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void exitss() {
            finish();
        }
    }
}
