package com.example.ahmedsharawy.gp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<topicds>topic;
private Context context;

    public RecyclerViewAdapter(List<topicds> topic,Context context) {
        this.topic = topic;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowagenda, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
      final  topicds city = topic.get(i);
        viewHolder.topid.setText(String.valueOf(city.getTopicid()));
        viewHolder.name.setText(city.getTopicname());
        viewHolder.description.setText(city.getTopicdescribtion());
         final boolean[] x={true};
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(x[0]==true)
                {
                    viewHolder.description.setVisibility(View.VISIBLE);
                    x[0]=false;
                }
                else{
                    viewHolder.description.setVisibility(View.GONE);
                    x[0]=true;
                }
            }
        });
        /*viewHolder.hid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.description.setVisibility(View.INVISIBLE);
            }
        });
        viewHolder.sho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.description.setVisibility(View.VISIBLE);
            }
        });*/



        global g=(global)context;

        if (g.getSds()==true) {
            viewHolder.vote.setVisibility(View.INVISIBLE);
        }
        viewHolder.vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,VoteActivity.class);

                i.putExtra("topicid",city.getTopicid());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(topic!=null)
        {
            return topic.size();
        }else
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public  View view;
        public TextView name;
        public  TextView description;
        public ImageButton vote,sho,hid;
        public TextView topid;

        ConstraintLayout constraintLayout;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            name = view.findViewById(R.id.names);
            description = view.findViewById(R.id.des);
            topid=view.findViewById(R.id.topid);
            vote=view.findViewById(R.id.votebutton);
            constraintLayout=view.findViewById(R.id.carid);
           // sho=view.findViewById(R.id.imageButton3);
          //  hid=view.findViewById(R.id.imageButton5);

        }




    }
}


