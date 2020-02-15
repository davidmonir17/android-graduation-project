package com.example.ahmedsharawy.gp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Register extends AppCompatActivity {
    private static final String TAG="Register";
    private EditText dat;
      Date date1;
    private DatePickerDialog.OnDateSetListener mdaatasetlesiner;

    LoginRigester db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dat=(EditText)findViewById(R.id.birthdate);
        dat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(Register.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mdaatasetlesiner,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mdaatasetlesiner=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                Log.d(TAG, "onDateSet: dd/mm/year:"+month+"/"+day+"/"+year);
                String date= month+"-"+day+"-"+year;
                dat.setText(date);

            }
        };
        final String date=dat.getText().toString();
        try {
         date1=new SimpleDateFormat("mm-dd-yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       final TextView v1 =(TextView)findViewById(R.id.textView);
        final TextView v2 =(TextView)findViewById(R.id.pas);
        final TextView v3 =(TextView)findViewById(R.id.con);
        final  TextView v4 =(TextView)findViewById(R.id.na);
        final TextView v5 =(TextView)findViewById(R.id.bir);
        final TextView v6 =(TextView)findViewById(R.id.Ge);
        final  TextView v7 =(TextView)findViewById(R.id.Ph);

        db=new LoginRigester();
        final EditText u = (EditText) findViewById(R.id.username);
        final EditText p = (EditText) findViewById(R.id.paswordd);
        final EditText p1 = (EditText) findViewById(R.id.editText6);
        final ImageButton cont=(ImageButton)findViewById(R.id.continue11);
        final ImageButton bak=(ImageButton)findViewById(R.id.back11);
        final EditText n = (EditText) findViewById(R.id.name);
        final EditText pho = (EditText) findViewById(R.id.phone);
        final RadioButton mal=(RadioButton)findViewById(R.id.radioButton);
        final RadioButton fe=(RadioButton)findViewById(R.id.radioButton2);
        final Button done=(Button)findViewById(R.id.button2);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x=0;
                if ((u.getText().toString().equals("") && p.getText().toString().equals("") && p1.getText().toString().equals("") && n.getText().toString().equals("")&& pho.getText().toString().equals(""))&&(!mal.isChecked()||!fe.isChecked()))
                {
                    Toast.makeText(getApplicationContext(),"please fill the requirement ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if((p.getText().toString()).equals(p1.getText().toString()))
                    {
                        if(mal.isChecked())
                        {
                            x=1;
                        }
                        int fd=db.cheackEmail(getApplicationContext(),u.getText().toString());
                        if(fd==0) {
                          // String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(p.getText().toString());


                            db.Register(getApplicationContext(), n.getText().toString(), x, u.getText().toString(), p.getText().toString(), dat.getText().toString(), pho.getText().toString());
                           Intent i = new Intent(Register.this, MainActivity.class);
                            startActivity(i);
                        }else
                            //(java.sql.Date) date1
                        {
                            Toast.makeText(getApplicationContext(), "Email aready existed", Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Password Not Matched", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);
                cont.setVisibility(View.VISIBLE);
                done.setVisibility(View.GONE);
                bak.setVisibility(View.GONE);
                pho.setVisibility(View.GONE);
                mal.setVisibility(View.GONE);
                fe.setVisibility(View.GONE);
                dat.setVisibility(View.GONE);
                u.setVisibility(View.VISIBLE);
                p.setVisibility(View.VISIBLE);
                p1.setVisibility(View.VISIBLE);
                n.setVisibility(View.VISIBLE);
            }
        });


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.VISIBLE);
                v6.setVisibility(View.VISIBLE);
                v7.setVisibility(View.VISIBLE);
                cont.setVisibility(View.GONE);
                done.setVisibility(View.VISIBLE);
                bak.setVisibility(View.VISIBLE);
                pho.setVisibility(View.VISIBLE);
                mal.setVisibility(View.VISIBLE);
                fe.setVisibility(View.VISIBLE);
                dat.setVisibility(View.VISIBLE);
                u.setVisibility(View.GONE);
                p.setVisibility(View.GONE);
                p1.setVisibility(View.GONE);
                n.setVisibility(View.GONE);
            }
        });

    }
}
