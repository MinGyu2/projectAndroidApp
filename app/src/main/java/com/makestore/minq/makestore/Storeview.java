package com.makestore.minq.makestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
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
import android.webkit.JavascriptInterface;
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
    int loginable = 0;
    WebView web;
    WebSettings webss;
    Button itemplus;
    Button logins;
    Button logouts;
    Button sMaps;
    Button orderlist;
    String sname;

    String iddd = "";
    String pwdss ="";
    String cusorpro = "";


    Thread th;
    Handler han;
    boolean tt=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeview);

        //start action bar left button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.drawable.viewmenu);
        actionBar.setHomeAsUpIndicator(R.drawable.refreshball);
        actionBar.setHomeButtonEnabled(true);
        //end

        Intent i = getIntent();
        sname = i.getStringExtra("storename");
        String ssss;


        web = (WebView)findViewById(R.id.storeitems);
        web.setWebViewClient(new WebViewClient());
        webss = web.getSettings();
        webss.setJavaScriptEnabled(true);
        web.addJavascriptInterface(new WebAppInterface(this),"Android");
        web.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //Toast.makeText(Storeview.this, "wwws", Toast.LENGTH_SHORT).show();
                        itemplus.setVisibility(View.GONE);
                        logins.setVisibility(View.GONE);
                        sMaps.setVisibility(View.GONE);
                        logouts.setVisibility(View.GONE);
                        orderlist.setVisibility(View.GONE);
                    break;
                }
                return false;
            }
        });
        web.loadUrl("http://13.125.255.233:8887/clients/storelist.php?store="+sname);
        web.reload();

        itemplus = (Button)findViewById(R.id.itemplus);
        logins = (Button)findViewById(R.id.logins);
        logouts = (Button)findViewById(R.id.logouts);
        sMaps = (Button)findViewById(R.id.sMaps);
        orderlist = (Button)findViewById(R.id.orderlist);
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
        public void loginGos() {
            Intent i2 = new Intent(mContext,Loginstage.class);
            i2.putExtra("storename",sname);
            startActivityForResult(i2,ACT_EDIT);
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public String Loginable(){
            return (""+loginable);
        }
        @JavascriptInterface
        public String getIDs(){
            return iddd;
        }
        @JavascriptInterface
        public String getPWDs(){
            return pwdss;
        }
        @JavascriptInterface
        public String getConfirm(){ return cusorpro; }//가게 주인인지 확인
/*web.reload();
        @JavascriptInterface
        public String testsss(String t){
            //Toast.makeText(mContext, t, Toast.LENGTH_SHORT).show();
            return t+"안녕ㅇㅇㅇㅇㅇ";
        }//*/
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
            //Toast.makeText(this,"hihi",Toast.LENGTH_SHORT).show();
            web.reload();
        }
        return super.onOptionsItemSelected(item);
    }

    public void mk(View v){
        switch (v.getId()){
            case R.id.threeball:
                if(web.canGoBack())
                    return;
                //가게 주인만 봄
                if(itemplus.getVisibility() == View.GONE && cusorpro.equals("1"))
                    itemplus.setVisibility(View.VISIBLE);
                else
                    itemplus.setVisibility(View.GONE);

                //모두 볼수 있음
                if(logins.getVisibility() == View.GONE && loginable==0)
                    logins.setVisibility(View.VISIBLE);
                else
                    logins.setVisibility(View.GONE);

                if(orderlist.getVisibility() == View.GONE && loginable ==1 && cusorpro.equals("0"))
                    orderlist.setVisibility(View.VISIBLE);
                else
                    orderlist.setVisibility(View.GONE);

                if(logouts.getVisibility() == View.GONE && loginable==1)
                    logouts.setVisibility(View.VISIBLE);
                else
                    logouts.setVisibility(View.GONE);

                if(sMaps.getVisibility() == View.GONE)
                    sMaps.setVisibility(View.VISIBLE);
                else
                    sMaps.setVisibility(View.GONE);
                //Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemplus://가게 주인만 등록 할수 있음
                Intent i = new Intent(this,Saveitem.class);
                i.putExtra("storename",sname);
                i.putExtra("idss",iddd);//가게 주인 ID
                i.putExtra("pwds",pwdss);//가게 주인 psw
                //startActivity(i);
                startActivityForResult(i,2);
                break;
            case R.id.logins:
                Intent i2 = new Intent(this,Loginstage.class);
                i2.putExtra("storename",sname);
                startActivityForResult(i2,ACT_EDIT);
                break;
            case R.id.sMaps:
                Intent i3 = new Intent(this,StoreMapView.class);
                i3.putExtra("storename",sname);
                startActivity(i3);
                break;
            case R.id.logouts:
                Toast.makeText(this, "logouts", Toast.LENGTH_SHORT).show();
                loginable = 0;
                logouts.setVisibility(View.GONE);
                logins.setVisibility(View.VISIBLE);
                itemplus.setVisibility(View.GONE);
                orderlist.setVisibility(View.GONE);
                cusorpro = "0";
                break;
            case R.id.orderlist:
                Intent i4 = new Intent(this,OrderList.class);
                i4.putExtra("sname",sname);
                i4.putExtra("id",iddd);
                startActivity(i4);
                Toast.makeText(getApplicationContext(),"sss",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACT_EDIT && resultCode == RESULT_OK){
            iddd = data.getStringExtra("idzz");
            pwdss = data.getStringExtra("pwdzz");
            cusorpro = data.getStringExtra("cusorpro");
            if(itemplus.getVisibility() == View.GONE && cusorpro.equals("1"))
                itemplus.setVisibility(View.VISIBLE);
            else
                itemplus.setVisibility(View.GONE);
            loginable = 1;
            itemplus.setVisibility(View.GONE);
            logins.setVisibility(View.GONE);
            sMaps.setVisibility(View.GONE);
            orderlist.setVisibility(View.GONE);

            CheckTypesTask task = new CheckTypesTask();

            task.execute();
        }else if(requestCode == 2 && resultCode == RESULT_OK){
            web.reload();
        }
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(Storeview.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setCancelable(false);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 5; i++) {
                    //asyncDialog.setProgress(i * 30);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }

}
