package com.example.ahmedsharawy.gp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.text.TextUtils.isEmpty;

@SuppressLint("Registered")
public class CreateMeeting extends AppCompatActivity {

    NewMeeting meet;
    TextView leader;
    EditText Title, StartDate, Duration, Location, TopicName, TopicDescription, TopicTime;
    Spinner TopicPriority;
    String topicpriority;
    Button submit;

    String Date,Time;

    EditText participant;
    ImageButton Addparticipant;
    ListView list;

    LinearLayout mlayout;
    ArrayList<Topic> arr;
    ImageButton addTopic;
    int cnt;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        leader = findViewById(R.id.leader);
        meet = new NewMeeting();
        final ArrayList<String> users = meet.getUser(getApplicationContext());

        global g=(global)getApplicationContext();
        String Userlogin1=g.getUs();
        leader.setText("Leader :\n" + "               "+ Userlogin1);
        StartDate = findViewById(R.id.startDate);
        final Calendar calendar = Calendar.getInstance();
        final int Day = calendar.get(Calendar.DAY_OF_MONTH);
        final int Month = calendar.get(Calendar.MONTH);
        final int Year = calendar.get(Calendar.YEAR);
        final int Hour = calendar.get(Calendar.HOUR_OF_DAY);
        final  int Minutes = calendar.get(calendar.MINUTE);
        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dp =  new DatePickerDialog(CreateMeeting.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date = year + "-" + String.valueOf(month + 1) + "-" + dayOfMonth ;
                        if(Time != null)
                        {
                            StartDate.setText(Date + " " + Time);
                        }
                        else
                        {
                            StartDate.setText(Date);
                        }
                    }
                },Year,Month,Day);
                dp.show();
                TimePickerDialog t = new TimePickerDialog(CreateMeeting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Time = hourOfDay + " : " + minute;
                        if(Date != null)
                        {
                            StartDate.setText(Date + " " + Time);
                        }
                    }
                },Hour,Minutes,true);
                t.show();
            }
        });

        list = findViewById(R.id.ListView);
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        list.setAdapter(listAdapter);
        Addparticipant = findViewById(R.id.addParticipant);
        participant = findViewById(R.id.participant);
        Addparticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                for (int i=0;i<users.size();i++)
                {
                    if(users.get(i).equals(participant.getText().toString()))
                    {
                        flag = 1;
                        break;
                    }
                }
                if(flag == 1)
                {
                    int check=0;
                    for(int i=0;i<listAdapter.getCount();i++)
                    {
                        if(listAdapter.getItem(i).toString().equals(participant.getText().toString()))
                        {
                            Toast.makeText(getApplicationContext(),"Please enter a another Email",Toast.LENGTH_LONG).show();
                            check = 1;
                            break;
                        }
                    }
                    if(check == 0)
                    {
                        listAdapter.add(participant.getText().toString());
                    }
                }

                if(flag == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please enter a correct Email",Toast.LENGTH_LONG).show();
                }
                participant.setText("");
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapter.remove(((TextView)view).getText().toString());
                return false;
            }
        });

        cnt = 0;
        mlayout = findViewById(R.id.linearLayout);
        arr = new ArrayList<Topic>();
        addTopic = findViewById(R.id.addTopic);
        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.topics,null);
                mlayout.addView(view);

                EditText name = view.findViewById(R.id.topicName);
                EditText dsc = view.findViewById(R.id.topicDescription);
                EditText time = view.findViewById(R.id.topicTime);
                final Spinner priority = view.findViewById(R.id.topicPriority);
                EditText hidden = view.findViewById(R.id.hidden);
                final EditText hidden2 = view.findViewById(R.id.priorityValue);
                hidden.setText(String.valueOf(cnt));
                Topic tpc = new Topic(name,dsc,time,priority,hidden,hidden2);
                arr.add(tpc);
                cnt++;
                ImageButton deleteTopic = view.findViewById(R.id.deleteTopic);
                deleteTopic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CardView card = (CardView) v.getParent().getParent();
                        EditText hidd = card.findViewById(R.id.hidden);
                        for (int i = 0;i<arr.size();i++)
                        {
                            if(arr.get(i).hiddenInput.getText().toString().equals(hidd.getText().toString()))
                            {
                                arr.remove(i);
                                break;
                            }
                        }
                        mlayout.removeView((View) v.getParent().getParent());
                    }
                });

                String[] item = new String[]{"Urgent","High","Medium","Low"};
                ArrayAdapter<String> adapt = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,item);
                priority.setAdapter(adapt);

                priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        hidden2.setText(priority.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        Title = findViewById(R.id.meetingTitle);
        Duration = findViewById(R.id.duration);
        Location = findViewById(R.id.location);
        TopicName = findViewById(R.id.topicName);
        TopicDescription = findViewById(R.id.topicDescription);
        TopicTime = findViewById(R.id.topicTime);
        TopicPriority = findViewById(R.id.topicPriority);
        String[] items = new String[]{"Urgent","High","Medium","Low"};
        ArrayAdapter<String> ad = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        TopicPriority.setAdapter(ad);
        TopicPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                topicpriority = TopicPriority.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(Title.getText()))
                {
                    Toast.makeText(getApplicationContext(), "Please fill the Meeting Title field !", Toast.LENGTH_LONG).show();
                    return;
                }
                if (isEmpty(StartDate.getText()))
                {
                    Toast.makeText(getApplicationContext(), "Please fill the StartDate field !", Toast.LENGTH_LONG).show();
                    return;
                }
                //TextView Leader = findViewById(R.id.leader);
                ArrayList<String> participants = new ArrayList<String>();
                for (int i = 0; i < listAdapter.getCount(); i++)
                {
                    participants.add(listAdapter.getItem(i));
                }
                if (participants.size() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please enter at least one participant !", Toast.LENGTH_LONG).show();
                    return;
                }
                ArrayList<String> topicsName = new ArrayList<String>();
                if (isEmpty(TopicName.getText()))
                {
                    Toast.makeText(getApplicationContext(), "Please fill the Topic Name field !", Toast.LENGTH_LONG).show();
                    return;
                }
                topicsName.add(TopicName.getText().toString());
                ArrayList<String> topicsDescription = new ArrayList<String>();
                topicsDescription.add(TopicDescription.getText().toString());
                ArrayList<Integer> topicsTime = new ArrayList<Integer>();
                if (!isEmpty(TopicTime.getText()))
                {
                    topicsTime.add(Integer.parseInt(TopicTime.getText().toString()));
                }
                else
                {
                    topicsTime.add(null);
                }
                ArrayList<String> topicsPriority = new ArrayList<String>();
                topicsPriority.add(topicpriority);
                for (int i = 0; i < arr.size(); i++)
                {
                    if (isEmpty(arr.get(i).tpcName.getText()))
                    {
                        Toast.makeText(getApplicationContext(), "Please fill the Topic Name field !", Toast.LENGTH_LONG).show();
                        return;
                    }
                    topicsName.add(arr.get(i).tpcName.getText().toString());
                    topicsDescription.add(arr.get(i).tpcDsc.getText().toString());
                    if (!isEmpty(arr.get(i).tpcTime.getText()))
                    {
                        topicsTime.add(Integer.parseInt(arr.get(i).tpcTime.getText().toString()));
                    }
                    else
                    {
                        topicsTime.add(null);
                    }
                    topicsPriority.add(arr.get(i).hiddenpriority.getText().toString());
                }
                NewMeeting MeetIn = new NewMeeting();
                global g=(global)getApplicationContext();
                String Userlogin=g.getUs();

                participants.add(Userlogin);
                MeetIn.newMeeting(getApplicationContext(), Title.getText().toString(), StartDate.getText().toString(), Duration.getText().toString(), Location.getText().toString(), participants, topicsName, topicsDescription, topicsTime, topicsPriority);
                Intent i=new Intent(CreateMeeting.this,Listmeetings.class);
                startActivity(i);
            }
        });

    }
}
