package com.example.ahmedsharawy.gp;

import android.content.Context;
import android.database.Cursor;
import android.text.StaticLayout;
import android.widget.AdapterView;
import android.widget.Toast;

import net.sourceforge.jtds.jdbc.DateTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import 	java.time.format.DateTimeFormatter;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class afterlogin {
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;

    public afterlogin()
    {}

    public List<mixlist>getmeeting(Context context, int id)
    {
        String q;
        Statement st,st1 ;
        ResultSet rs,r;
        int [] arrid= new int [50];
        int [] arragendaid= new int [50];
        String [] arrstr= new String[50];
        List<mixlist> mapoflist=new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else
            {
                q=" select meetingID from UserMeeting where userID='"+id+"'";
                st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs=st.executeQuery(q);
                int i=0;
                while (rs.next())
                {
                    arrid[i]=rs.getInt(1);
                    q="select Title,AgendaID from Agenda where meetingID='"+arrid[i]+"'";
                    st1 = connect.createStatement();
                    r=st1.executeQuery(q);
                    r.next();
                    arrstr[i]=r.getString(1);
                    arragendaid[i]=r.getInt(2);
                    mixlist x=new mixlist();
                    x.setMeetid(arrid[i]);
                    x.setMeettitle(arrstr[i]);
                    x.setAgendaid(arragendaid[i]);
                    mapoflist.add(x);
                    i++;
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();
        }
        return mapoflist;
    }

    public  List<topicds> getmeetinginf(Context context,int id ) {
        String q;
        Statement st;
        ResultSet rs;
        List<topicds> lis=new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else {

                q=" select TopicName, TopicDescription,TopicID from Topic where agendaId='"+id+"'";
                st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs=st.executeQuery(q);

                while (rs.next())
                {

                    topicds x=new topicds();
                    x.setTopicname(rs.getString(1));
                    x.setTopicdescribtion(rs.getString(2));
                    x.setTopicid(rs.getInt(3));
                    lis.add(x);
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();

        }
        return lis;
    }
public  boolean expiervoute(Context context,int id ) {
    String q;
    Statement st;
    ResultSet rs;
  Date meetdate;
    int duiration;

     Date curentime=Calendar.getInstance().getTime();
    try {
        ConnectionHelper connectionHelper = new ConnectionHelper(context);
        connect = connectionHelper.connections();
        if (connect == null) {
            ConnectionResult = "Check your internet connection";
        }
        else {
            q="select MeetingDate , MeetingDuration from Meeting where MeetingID='"+id+"'";
            st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(q);
            rs.next();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            meetdate=sdf.parse(rs.getString(1));
            duiration=rs.getInt(2);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(meetdate);
            calendar.add(calendar.HOUR,duiration);
            if (curentime.compareTo(meetdate) < 0 ||curentime.compareTo(calendar.getTime())>0)
            {
                return true;
            }

        }
    }
    catch (Exception ex)
    {
        Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
        isSuccess = false;
        ConnectionResult = ex.getMessage();

    }
    return false;
}
    /////////////////////////////////////////////////////////////////////////

    public  List<voteclas> getvotes(Context context,int id ) {
        String q;
        Statement st;
        ResultSet rs;
        List<voteclas> lis=new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else {

                q=" select VoteName,VoteID from Vote where topicid='"+id+"';";
                st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs=st.executeQuery(q);

                while (rs.next())
                {

                    voteclas x=new voteclas();
                    x.setVotename(rs.getString(1));
                    x.setVoteid(rs.getInt(2));
                    lis.add(x);
                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();

        }
        return lis;
    }

    public  List<optionclas> getoptions( int id ) {
        String q;
        Statement st;
        ResultSet rs;
        List<optionclas> lis=new ArrayList<>();
        try {


                q=" select OptionID,OptionDescription,OptionCount from Options where voteID='"+id+"'";
                st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs=st.executeQuery(q);

                while (rs.next())
                {

                    optionclas x=new optionclas();
                    x.setOptinid(rs.getInt(1));
                    x.setOptionname(rs.getString(2));
                    x.setOptincount(rs.getInt(3));
                    lis.add(x);
                }

        }
        catch (Exception ex)
        {


        }
        return lis;
    }

    public  void updatecount(Context context, int id,int count)
    {
        String q;
        Statement st;
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else {

                q = "update Options set OptionCount='"+count+"' where OptionID='"+id+"'";
                st = connect.createStatement();
                st.executeUpdate(q);
            }

        }
        catch(Exception ex)
        {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();

        }
    }
}
