package com.example.ahmedsharawy.gp;
import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getData {
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    public getData(){}
    public List<Map<String, String>> getdata(Context context) {
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try
        {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else {
                /*String query = "select * from Meeting";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("duration", rs.getString("MeetingDuration"));
                    datanum.put("date", rs.getString("MeetingDate"));
                    datanum.put("status", rs.getString("Status"));
                    data.add(datanum);
                }*/

                ConnectionResult = "Succeeded";
                isSuccess = true;
                connect.close();
            }
        } catch (Exception ex)
        {
            Toast.makeText(context,"er",Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();
        }

        return data;

    }
}
