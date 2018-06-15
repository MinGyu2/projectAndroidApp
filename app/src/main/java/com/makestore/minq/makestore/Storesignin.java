package com.makestore.minq.makestore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Storesignin extends AppCompatActivity {
    private WebView mWebs;
    private WebSettings mWebset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storesignin);

        mWebs =(WebView)findViewById(R.id.webs);
        mWebs.setWebViewClient(new WebViewClient());
        mWebset = mWebs.getSettings();
        mWebset.setJavaScriptEnabled(true);

        mWebs.loadUrl("http://13.125.255.233:8888/clients/addStore.html");

        /*//start -> php post 형식으로 만듬 이것으로 로그인하여 들어가는것을 구현 하면 됨
        String str = null;
        try {
            str = "q=" + URLEncoder.encode("12", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mWebs.postUrl("http://13.125.255.233:8888/clients/test2.php?aa=asdasdasd", str.getBytes());
        //end*/

        mWebs.reload();
    }
}
