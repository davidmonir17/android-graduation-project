package com.example.ahmedsharawy.gp;

import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AgendaLayout extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    public afterlogin db;
    List<topicds> lis= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id=getIntent().getExtras().getInt("agendaid");

        int mid=getIntent().getExtras().getInt("meid");
        setContentView(R.layout.activity_agenda_layout);
    db=new afterlogin();
    lis=db.getmeetinginf(this,id);
    Boolean isr=db.expiervoute(this,mid);
global g=(global)getApplicationContext();
g.setSds(isr);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecyclerViewAdapter(lis,getApplicationContext());
        this.recyclerView.setAdapter(adapter);

    }
}
