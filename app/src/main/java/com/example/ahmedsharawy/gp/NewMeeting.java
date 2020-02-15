package com.example.ahmedsharawy.gp;

import android.content.Context;
import android.widget.Toast;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.Duration;

public class NewMeeting {
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    public NewMeeting(){}
    public  ArrayList<String> getUser(Context context)
    {
        ArrayList<String> arr = new ArrayList<String>();
        String q, query;
        Statement st;
        ResultSet rs;
        try
        {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";

            }
            else
            {
                q = "SELECT Email FROM \"User\"";
                st = connect.createStatement();
                rs = st.executeQuery(q);
                while (rs.next())
                {
                    arr.add(rs.getString("Email").toString());
                }
                connect.close();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
        }
        return arr;
    }
    public List<Map<String, String>> newMeeting(Context context, String title, String date, String duration, String address, ArrayList<String> usersEmail, ArrayList<String> topicsName, ArrayList<String> topicsDescription, ArrayList<Integer> topicsTime, ArrayList<String> topicsPriority ) {
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        String q, query;
        Statement st;
        ResultSet rs;
        try
        {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else {

                if (!address.equals(""))
                {
                    q = "select * from Location";
                    st = connect.createStatement();
                    rs = st.executeQuery(q);

                    int locID = 0;
                    while (rs.next()) {
                        if (address.equals(rs.getString("Address")))
                        {
                            locID = rs.getInt("LocationID");

                            query = "insert into Meeting (MeetingDuration,MeetingDate,Status,locationID) " +  "values('" + duration + "','" + date + "'," + "'In progress'" + "," + locID + ")" ;
                            st = connect.createStatement();
                            st.executeUpdate(query);

                            break;
                        }
                    }
                    if (locID == 0)
                    {
                        query = "insert into Location (Address) " +  "values('" + address + "')" ;
                        st = connect.createStatement();
                        st.executeUpdate(query);

                        q = "SELECT TOP(1) * FROM Location ORDER BY LocationID DESC";
                        st = connect.createStatement();
                        rs = st.executeQuery(q);
                        rs.next();

                        query = "insert into Meeting (MeetingDuration,MeetingDate,Status,locationID) " +  "values('" + duration + "','" + date + "'," + "'In progress'" + "," + Integer.parseInt(rs.getString("LocationID")) + ")" ;
                        st = connect.createStatement();
                        st.executeUpdate(query);
                    }
                }
                else
                {
                    query = "insert into Meeting (MeetingDuration,MeetingDate,Status,locationID) " +  "values('" + duration + "','" + date + "'," + "'In progress'" + "," + null + ")" ;
                    st = connect.createStatement();
                    st.executeUpdate(query);
                }

                q = "SELECT TOP(1) * FROM Meeting ORDER BY MeetingID DESC";
                st = connect.createStatement();
                rs = st.executeQuery(q);
                rs.next();

                int meetingID = Integer.parseInt(rs.getString("MeetingID"));
                query = "insert into Agenda (Title,meetingID) " +  "values('" + title + "'," + meetingID + ")" ;
                st = connect.createStatement();
                st.executeUpdate(query);

                q = "SELECT TOP(1) * FROM Agenda ORDER BY AgendaID DESC";
                st = connect.createStatement();
                rs = st.executeQuery(q);
                rs.next();

                for (int i = 0; i < topicsName.size(); i++)
                {
                    query = "insert into Topic (TopicName,TopicDescription,TopicTime,TopicPriority,agendaId) " +  "values('" + topicsName.get(i) + "','" + topicsDescription.get(i) + "'," + topicsTime.get(i) + ",'" + topicsPriority.get(i) + "'," + Integer.parseInt(rs.getString("AgendaID")) + ")" ;
                    st = connect.createStatement();
                    st.executeUpdate(query);
                }

                for (int i = 0; i < usersEmail.size(); i++)
                {
                    q = "SELECT * FROM \"User\" WHERE Email = '" + usersEmail.get(i) + "'";
                    st = connect.createStatement();
                    rs = st.executeQuery(q);
                    rs.next();

                    query = "insert into UserMeeting (userID,meetingID,Attended) " +  "values(" + rs.getInt("UserID") + "," + meetingID + "," + 0 + ")" ;
                    st = connect.createStatement();
                    st.executeUpdate(query);
                }

                ConnectionResult = "Succeeded";
                isSuccess = true;
                connect.close();
            }
        } catch (Exception ex)
        {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();
        }

        return data;

    }
}
