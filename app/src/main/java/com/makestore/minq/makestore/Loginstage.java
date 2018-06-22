package com.makestore.minq.makestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Loginstage extends AppCompatActivity {
    EditText ids;
    EditText pwds;
    String storename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginstage);

        ids = (EditText)findViewById(R.id.IDpart);
        pwds = (EditText)findViewById(R.id.pwdpart);

        Intent i = getIntent();
        storename = i.getStringExtra("storename");
    }

    public void mk(View v){
        switch (v.getId()){
            case R.id.loginBt:
                Intent i = new Intent();
                if(ids.getText().toString().equals("") || pwds.getText().toString().equals("") ){
                    Toast.makeText(this, "빈칸 존재", Toast.LENGTH_SHORT).show();
                }else {
                    i.putExtra("idzz", ids.getText().toString());
                    i.putExtra("pwdzz", pwds.getText().toString());
                    setResult(RESULT_OK, i);
                    finish();
                }
                break;
            case R.id.customingin:
                Intent i2 = new Intent(this,Customaersignin.class);
                i2.putExtra("storename",storename);
                startActivity(i2);
                break;
        }
    }
}
