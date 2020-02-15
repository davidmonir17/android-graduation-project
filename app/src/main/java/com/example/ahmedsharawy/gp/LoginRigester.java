package com.example.ahmedsharawy.gp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginRigester {
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;

    public LoginRigester() {
    }

    public int cheackEmail(Context context,String email)
    {
        String q;
        Statement st ;
        ResultSet rs;
        int count=0;
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else
            {
                q="SELECT Email FROM \"User\" where Email='"+email+"'";
                st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs=st.executeQuery(q)       ;
                rs.last();
                 count = rs.getRow();
                rs.beforeFirst();
            //    Toast.makeText(context,SetTe count,Toast.LENGTH_LONG).show();

            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();

        }
        return count;
    }

    public  void  Register(Context context, String nam, int Gender, String email, String pas, String birth, String phone)
    { String q;
        Statement st;

        try
        {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else
                {
                    q= "insert into \"User\"(Name,Gender,Email,Password,Birthdate,Phone,ActivationCode,IsEmailVerified)"+"values('"+nam+"','"+Gender+"','"+email+"','"+pas+"','"+birth+"','"+phone+"',"+"'850cf96a-75e0-47d8-af0b-496ab1b6261b'"+","+1+")";
                    st = connect.createStatement();
                    st.executeUpdate(q);
                    Toast.makeText(context,"Created Done User",Toast.LENGTH_LONG).show();

                }


        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();

        }

    }
    public int login(Context context, String email, String pass)
    {
        String q;
        Statement st ;
        ResultSet rs;
        int count=0;
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else
            {
                q="select Email,\"Password\"from \"User\" where Email='"+email+"' And \"Password\"='"+pass+"'";
                st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs=st.executeQuery(q)       ;
                rs.last();
                count = rs.getRow();
                rs.beforeFirst();
                //    Toast.makeText(context,SetTe count,Toast.LENGTH_LONG).show();

            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();

        }
        return count;
    }






    public int getidlogin(Context context, String email)
    {
        String q;
        Statement st ;
        ResultSet rs;
        int count=0;
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper(context);
            connect = connectionHelper.connections();
            if (connect == null) {
                ConnectionResult = "Check your internet connection";
            }
            else
            {
                q="select USERID from [GP].[dbo].[User] where Email ='"+email+"' ";
                st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs=st.executeQuery(q)       ;
                rs.next();
                count = rs.getInt(1);
                rs.beforeFirst();
                //    Toast.makeText(context,SetTe count,Toast.LENGTH_LONG).show();

            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
            isSuccess = false;
            ConnectionResult = ex.getMessage();

        }
        return count;
    }
}
