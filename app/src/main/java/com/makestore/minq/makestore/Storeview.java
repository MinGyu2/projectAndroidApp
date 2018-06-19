package com.makestore.minq.makestore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Storeview extends AppCompatActivity {
    final static int ACT_EDIT = 0;
    boolean loginable = false;
    WebView web;
    WebSettings webss;
    Button itemplus;
    Button logins;
    String sname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeview);

        //start action bar left button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.viewmenu);
        actionBar.setHomeButtonEnabled(true);
        //end

        Intent i = getIntent();
        sname = i.getStringExtra("storename");
        String ssss;
        ssss =i.getStringExtra("logss");
        Toast.makeText(this, ssss, Toast.LENGTH_SHORT).show();


        web = (WebView)findViewById(R.id.storeitems);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);
        web.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //Toast.makeText(Storeview.this, "wwws", Toast.LENGTH_SHORT).show();
                        itemplus.setVisibility(View.GONE);
                        logins.setVisibility(View.GONE);
                    break;
                }
                return false;
            }
        });
        //webss.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //web.loadUrl("http://13.125.255.233:8888/clients/storelist.php?store="+sname);
        web.loadUrl("http://13.125.255.233:8887/clients/storelist.php?store="+sname);
        web.reload();

        itemplus = (Button)findViewById(R.id.itemplus);
        logins = (Button)findViewById(R.id.logins);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();
        if(itemid == android.R.id.home){//action bar left button == home button
            Toast.makeText(this,"hihi",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void mk(View v){
        switch (v.getId()){
            case R.id.threeball:
                if(web.canGoBack())
                    return;
                //가게 주인만 봄
                if(itemplus.getVisibility() == View.GONE)
                    itemplus.setVisibility(View.VISIBLE);
                else
                    itemplus.setVisibility(View.GONE);

                //모두 볼수 있음
                if(logins.getVisibility() == View.GONE)
                    logins.setVisibility(View.VISIBLE);
                else
                    logins.setVisibility(View.GONE);
                //Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemplus://가게 주인만 등록 할수 있음
                Intent i = new Intent(this,Saveitem.class);
                i.putExtra("storename",sname);
                i.putExtra("idss","");//가게 주인 ID
                i.putExtra("pwds","");//가게 주인 psw
                startActivity(i);
                break;
            case R.id.logins:

                //web.destroyDrawingCache();
                //web.loadUrl("about:blank");
                //web.loadUrl("http://13.125.255.233:8888/clients/addStore.html");
                //web.reload();
                Intent i2 = new Intent(this,Loginstage.class);
                startActivityForResult(i2,ACT_EDIT);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACT_EDIT && resultCode == RESULT_OK){
            String iddd = data.getStringExtra("idzz");
            Toast.makeText(Storeview.this,iddd+" : "+data.getStringExtra("pwdzz"), Toast.LENGTH_SHORT).show();
            //finish();
            String str = null;
            try {
                str = "ids=" + URLEncoder.encode(iddd, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //web.postUrl("http://13.125.255.233:8888/clients/storelist.php?store="+sname, str.getBytes());
            web.postUrl("http://13.125.255.233:8887/clients/storelist.php?store="+sname, str.getBytes());

            itemplus.setVisibility(View.GONE);
            logins.setVisibility(View.GONE);

            //Intent i2 = new Intent(this,Storeview.class);
            //i2.putExtra("storename",sname);
            //i2.putExtra("logss","1");
            //i2.putExtra("idsss",iddd);
            //startActivity(i2);
        }
    }
}
