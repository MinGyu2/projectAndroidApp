package com.makestore.minq.makestore;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Spinner ss;
    String aa[] = new String[50];
    ArrayAdapter<String> arrAdapt;
    int Ns;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ss = (Spinner)findViewById(R.id.stores);
        title = (TextView)findViewById(R.id.title);


        String[] facilityList = {
                "MakeStore"
        };

        /*
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.spinernode,
                facilityList);

        ss.setAdapter(adapter);
        ss.setSelection(0);
        */



        ArrayList<String> entries =  new ArrayList<String>();//Arrays.asList("List Item A", "List Item B"));
        arrAdapt = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinernode, entries);

        InsertData in = new InsertData();
        in.execute();

        //*/
        //ss.getSelectedItem().toString();
        ss.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"" +parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                title.setText(""+parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void mk(View v){
        switch (v.getId()){
            case R.id.connects:
                Intent i=new Intent(this,Storesignin.class);
                startActivity(i);
                break;
            case R.id.access:
                Intent i2 = new Intent(this,Storeview.class);
                i2.putExtra("storename",ss.getSelectedItem().toString());
                //i2.putExtra("logss","0");
                startActivity(i2);
                //Toast.makeText(this,""+ss.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.refreshs:
                ArrayList<String> entries =  new ArrayList<String>();//Arrays.asList("List Item A", "List Item B"));
                arrAdapt = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinernode, entries);
                InsertData in = new InsertData();
                in.execute();
                break;
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
            //arrAdapt.add("lis");
            arrAdapt.notifyDataSetChanged();

            ss.setAdapter(arrAdapt);
            ss.setSelection(0);
            Toast.makeText(getApplicationContext(),r,Toast.LENGTH_LONG).show();
        }
        @Override
        protected String doInBackground(String... strings) {
            /*
            String img = (String)strings[0];
            String name = (String)strings[1];
            String text = (String)strings[2];*/


            //String serverURL = "http://13.125.255.233:8888/view.php";
            String serverURL = "http://13.125.255.233:8887/clients/storenames.php";

            StringBuffer buffer = new StringBuffer();
            /*
            try {
                buffer.append("img").append("=").append(URLEncoder.encode(img,"UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            buffer.append("imgname").append("=").append(name).append("&");
            buffer.append("name").append("=").append(name).append("&");
            buffer.append("text").append("=").append(text);*/

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
                Ns=0;
                while((line = bufferedReader.readLine())!=null){
                    sb.append(line);

                    arrAdapt.add(line);
                    aa[Ns] = line;
                    Ns++;
                    //adapter.add(line);
                }

                bufferedReader.close();
                return sb.toString();

            }catch (Exception e){
                return new String("Error"+e.getMessage());
            }
        }
    }
}
