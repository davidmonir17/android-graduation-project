package com.example.ahmedsharawy.gp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    TextView t ;
    List<Map<String,String>> l = null;
    Button butt ;
    Button but2 ;
    Intent i;
    CheckBox cheack;
    Boolean savelogin;
    LoginRigester db;
    private SharedPreferences loginpref;
    private SharedPreferences.Editor loginprefeditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = findViewById(R.id.textView);
        cheack=(CheckBox)findViewById(R.id.checkBox);
        but2=findViewById(R.id.button3);
        getData g= new getData();
        l = g.getdata(getApplicationContext());

        final EditText u = (EditText) findViewById(R.id.loginuser);
        final EditText p = (EditText) findViewById(R.id.loginpass);
        db=new LoginRigester();
        //////////////////////////////////////////////////////////
        loginpref=getSharedPreferences("loginPref",MODE_PRIVATE);
        loginprefeditor=loginpref.edit();
        savelogin=loginpref.getBoolean("savelogin",false);
        if(savelogin==true)
        {
            u.setText(loginpref.getString("username",""));
            p.setText(loginpref.getString("password",""));
            cheack.setChecked(true);

        }


        butt = findViewById(R.id.button);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(u.getWindowToken(),0);
                String user=u.getText().toString();
                String pas=p.getText().toString();
                int zz=0;
                zz = db.login(MainActivity.this,u.getText().toString(),p.getText().toString());
                if(zz!=0) {
                    if(cheack.isChecked())
                    {
                        loginprefeditor.putBoolean("savelogin",true);
                        loginprefeditor.putString("username",user);
                        loginprefeditor.putString("password",pas);
                        loginprefeditor.commit();

                    }
                    else {
                        loginprefeditor.clear();
                        loginprefeditor.commit();


                    }
                    //Toast.makeText(getApplicationContext(),l.get(0).get("status").toString(),Toast.LENGTH_LONG).show();
                    i = new Intent(MainActivity.this, Listmeetings.class);
                   int x= db.getidlogin(getApplicationContext(),user);
                    global g=(global)getApplicationContext();
                    g.setIds(x);
                    g.setUs(user);
                  // i.putExtra("userid",x);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"invalid Username Or Password ",Toast.LENGTH_LONG).show();
                }

            }
        });


        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),l.get(0).get("status").toString(),Toast.LENGTH_LONG).show();
                i = new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });

    }
}
