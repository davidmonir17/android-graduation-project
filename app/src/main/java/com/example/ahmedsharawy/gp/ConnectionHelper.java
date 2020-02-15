package com.example.ahmedsharawy.gp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    String IP,DB,DBUserName,DPPassword;
    Context c;
    public  ConnectionHelper(Context context)
    {
        c = context;
    }
    @SuppressLint("NewApi")
    public Connection connections(){
        IP = "192.168.137.188";
        DB = "GP";
        DBUserName = "david";
        DPPassword = "123456789";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        java.sql.Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://"  + IP + ";databaseName=" + DB + ";user=" + DBUserName + ";password=" +  DPPassword + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }catch (SQLException se){
            Log.e("error from SQL",se.getMessage());
            Toast.makeText(c,se.toString(),Toast.LENGTH_LONG).show();
        }
        catch (ClassNotFoundException e){
            Log.e("Error from Class",e.getMessage());
            Toast.makeText(c,e.toString(),Toast.LENGTH_LONG).show();
        }
        catch (Exception ex){
            Log.e("Error from Exceptio",ex.getMessage());
            Toast.makeText(c,ex.toString(), Toast.LENGTH_LONG).show();
        }
        return connection;
    }
}
