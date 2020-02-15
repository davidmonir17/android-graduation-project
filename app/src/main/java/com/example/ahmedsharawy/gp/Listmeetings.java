package com.example.ahmedsharawy.gp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Listmeetings extends AppCompatActivity {
public afterlogin db;
    ArrayAdapter<String> adapter;
    ListView list;
    List<mixlist> lis= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmeetings);
        ImageButton imageButton=(ImageButton)findViewById(R.id.imageButton);
        db=new afterlogin();
        list=(ListView)findViewById(R.id.listmeet);
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        global g=(global)getApplicationContext();
        int xs=g.getIds();
     // lis=db.getmeeting(getApplicationContext(),getIntent().getExtras().getInt("userid"));
        lis=db.getmeeting(getApplicationContext(),xs);
      int x=0;
      while (x<lis.size())
      {
          adapter.add(lis.get(x).meettitle);
          x++;
      }
      list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent i=new Intent(Listmeetings.this,AgendaLayout.class);
              i.putExtra("meid",lis.get(position).getMeetid());
              i.putExtra("agendaid",lis.get(position).getAgendaid());
              startActivity(i);
          }
      });


imageButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(Listmeetings.this,CreateMeeting.class);
        startActivity(i);

    }
});
    }
}

