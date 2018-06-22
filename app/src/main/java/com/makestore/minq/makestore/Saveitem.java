package com.makestore.minq.makestore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Saveitem extends AppCompatActivity {
    final int REQ_CODE_SELECT_IMAGE=100;
    ImageView imgs;
    Uri imguri;
    String imgen= "";
    String storename,idss,pswss;
    EditText nameet;
    EditText textet;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savezone);



        Intent i = getIntent();
        storename = i.getStringExtra("storename");
        idss = i.getStringExtra("idss");
        pswss = i.getStringExtra("pwds");


        imgs = (ImageView)findViewById(R.id.imgviews);
        nameet = (EditText)findViewById(R.id.nameet);
        textet = (EditText)findViewById(R.id.textet);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)){
            Intent i = getIntent();
            setResult(RESULT_OK, i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void mks(View v){
        switch (v.getId()){
            case R.id.imgviews:
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,REQ_CODE_SELECT_IMAGE);
                break;
            case R.id.savebt:
                InsertData in = new InsertData();
                if(!nameet.getText().toString().equals("") && !textet.getText().toString().equals("") && !imgen.equals("")) {
                    in.execute(imgen, nameet.getText().toString(), textet.getText().toString());
                }else{
                    Toast.makeText(this,"사진 선택 또는 name 또는 text 입력 하시오.",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delbt:
                break;
        }
    }
    public String getEncode(Bitmap bit){
        ByteArrayOutputStream sss = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG,100,sss);
        byte[] dada =sss.toByteArray();
        return Base64.encodeToString(dada, Base64.DEFAULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getBaseContext(), "resultCode : "+resultCode, Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    ///*
                    //Uri에서 이미지 이름을 얻어온다.
                    imguri = data.getData();
                    //imgname = ""  + imguri;
                    //nameet.setText(imgname);

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    //ImageView image = (ImageView)findViewById(R.id.imgs);
                    Bitmap n = Bitmap.createScaledBitmap(image_bitmap,400,400,true);
                    imgen = getEncode(n);
                    imgs.setImageBitmap(n);
                    //InsertData i = new InsertData();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    class InsertData extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String r){
            super.onPostExecute(r);
            Toast.makeText(getApplicationContext(),r,Toast.LENGTH_LONG).show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String img = (String)strings[0];
            String name = (String)strings[1];
            String text = (String)strings[2];


            //String serverURL = "http://13.125.255.233:8888/clients/test2.php";
            String serverURL = "http://13.125.255.233:8887/clients/saveitem.php";
            StringBuffer buffer = new StringBuffer();
            try {
                buffer.append("img").append("=").append(URLEncoder.encode(img,"UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            buffer.append("name").append("=").append(storename).append("&");
            //buffer.append("name").append("=").append("root").append("&");
            buffer.append("imgname").append("=").append(name).append("&");
            buffer.append("text").append("=").append(text);

            try{
                URL url = new URL(serverURL);
                HttpURLConnection httpUrlC = (HttpURLConnection)url.openConnection();

                httpUrlC.setReadTimeout(5000);
                httpUrlC.setConnectTimeout(5000);
                httpUrlC.setRequestMethod("POST");

                httpUrlC.setDoInput(true);
                httpUrlC.connect();

                OutputStream outputStream = httpUrlC.getOutputStream();
                outputStream.write(buffer.toString().getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStateCode = httpUrlC.getResponseCode();

                InputStream inputStream;
                if(responseStateCode ==httpUrlC.HTTP_OK){
                    inputStream = httpUrlC.getInputStream();
                }else{
                    inputStream = httpUrlC.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = bufferedReader.readLine())!=null){
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString();

            }catch (Exception e){
                return new String("Error"+e.getMessage());
            }
        }
    }
}
