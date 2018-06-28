package com.makestore.minq.makestore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CustomList extends AppCompatActivity {
    WebView web;
    WebSettings webss;

    String sname,idss,pwdd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customlist);

        Intent i = getIntent();
        sname = i.getStringExtra("sname");
        idss = i.getStringExtra("id");
        pwdd = i.getStringExtra("pwd");

        web = (WebView)findViewById(R.id.customls);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);
        web.addJavascriptInterface(new CustomList.WebAppInterface(this),"Android");

        String str = null;
        try {
            str = "sname=" + URLEncoder.encode(sname, "UTF-8") + "&id=" + URLEncoder.encode(idss, "UTF-8")+"&pwd="+URLEncoder.encode(pwdd, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        web.postUrl("http://13.125.255.233:8887/clients/customlist.php",str.getBytes());
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
        public void customOrder(String ssname,String ids){
            Toast.makeText(mContext, ssname+ids, Toast.LENGTH_SHORT).show();
            Intent i4 = new Intent(mContext,CustomOrder.class);
            i4.putExtra("sname",ssname);
            i4.putExtra("id",ids);

            startActivity(i4);
        }
    }
    //뒤로가기
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()){
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
