package com.makestore.minq.makestore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class Loginstage extends AppCompatActivity {
    WebView web;
    WebSettings webss;

    String storename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginstage);

        Intent i = getIntent();
        storename = i.getStringExtra("storename");

        web = (WebView)findViewById(R.id.loginWebs);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);
        web.addJavascriptInterface(new Loginstage.WebAppInterface(this),"Android");
        web.loadUrl("http://13.125.255.233:8887/clients/login.php?store="+storename);
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
        public void loginSuccess(String ids,String pwds,String sup){
            Intent i = new Intent();
            i.putExtra("idzz", ids);
            i.putExtra("pwdzz", pwds);
            i.putExtra("cusorpro",sup);//가게 주인인지 아니면 마스터 인지 구분
            setResult(RESULT_OK, i);
            finish();
        }
        @JavascriptInterface
        public void signs(){
            Intent i2 = new Intent(mContext,Customaersignin.class);
            i2.putExtra("storename",storename);
            startActivity(i2);
        }
    }
}
