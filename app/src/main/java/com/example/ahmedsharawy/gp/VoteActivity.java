package com.example.ahmedsharawy.gp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends AppCompatActivity {
    public afterlogin db;
    public int pos;
    public List<voteclas> votesnew = new ArrayList<>();
  public  List<optionclas> options = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        db=new afterlogin();
        int id=getIntent().getExtras().getInt("topicid");
        Spinner spinner=findViewById(R.id.spinner);
        Spinner spinner2=findViewById(R.id.spinner2);

        Button b1=findViewById(R.id.button4);
        final ArrayAdapter<String> votenames=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        spinner.setAdapter(votenames);
        votesnew=db.getvotes(getApplicationContext(),id);
        int x=0;
        final ArrayAdapter<String> optionnames=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        spinner2.setAdapter(optionnames);
        while (x<votesnew.size())
        {
            votenames.add(votesnew.get(x).votename);
            x++;
        }


spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        optionnames.clear();
        int votids=votesnew.get(position).voteid;
        options=db.getoptions(votids);
        int y=0;
        while (y<options.size()) {
            optionnames.add(options.get(y).optionname);
            y++;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});

        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updatecount(getApplicationContext(),options.get(pos).optinid,  options.get(pos).optincount+1);
                Toast.makeText(getApplicationContext(), "Voted Successfully", Toast.LENGTH_LONG).show();

            }
        });
    }
}
