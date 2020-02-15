package com.example.ahmedsharawy.gp;

import android.widget.EditText;
import android.widget.Spinner;

public class Topic {

    EditText tpcName,tpcDsc,tpcTime,hiddenInput,hiddenpriority;
    Spinner tpcPriority;

    public Topic(EditText TopicName,EditText TopicDsc,EditText TopicTime,Spinner TopicPriority,EditText TopicHidden,EditText hidden2)
    {
        tpcName = TopicName;
        tpcDsc = TopicDsc;
        tpcTime = TopicTime;
        tpcPriority  = TopicPriority;
        hiddenInput = TopicHidden;
        hiddenpriority = hidden2;
    }

}
