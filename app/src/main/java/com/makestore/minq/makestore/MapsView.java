package com.makestore.minq.makestore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MapsView extends AppCompatActivity{
    private WebView web;
    private WebSettings webss;

    String addrs= "" ;
    Intent i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapsview);

        web = (WebView)findViewById(R.id.mapview);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);
        web.addJavascriptInterface(new MapsView.WebAppInterface(this),"Android");

        web.loadUrl("http://13.125.255.233:8887/clients/map2.html");
        web.reload();

        i = getIntent();
        //storename = i.getStringExtra("storename");
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
        public void setAddr(String addr){
            addrs = addr;
            i.putExtra("addrs", addr);
            setResult(RESULT_OK, i);
            finish();
        }

/*
        @JavascriptInterface
        public String testsss(String t){
            //Toast.makeText(mContext, t, Toast.LENGTH_SHORT).show();
            return t+"안녕ㅇㅇㅇㅇㅇ";
        }//*/
    }
}
