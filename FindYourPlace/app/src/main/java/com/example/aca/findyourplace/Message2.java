package com.example.aca.findyourplace;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Message2 {

    private String tekst;
    private String Data;
    private int senderId;
    private int receiverId;

    public Message2 LoadMessage(){

        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            tekst=gdt.execute("http://192.168.1.108:5000/user/1").get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Message2 data = new Message2();
        Gson gson = new Gson();
        data= gson.fromJson(tekst,Message2.class);

        return data;
    }
    public ArrayList<Message2> loadMessages(int convId)
    {
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            tekst=gdt.execute("http://192.168.1.108:5000/conversation/" + convId).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Type listType = new TypeToken<ArrayList<Message2>>(){}.getType();
        ArrayList<Message2> data = new ArrayList<Message2>();
        Gson gson = new Gson();
        data= gson.fromJson(tekst,listType);

        return data;

    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

//    public static String executePost(String targetURL, String urlParameters) {
//        HttpURLConnection connection = null;
//
//        try {
//            //Create connection
//            URL url = new URL(targetURL);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
//
//            connection.setRequestProperty("Content-Length",
//                    Integer.toString(urlParameters.getBytes().length));
//            connection.setRequestProperty("Content-Language", "en-US");
//
//            connection.setUseCaches(false);
//            connection.setDoOutput(true);
//
//            //Send request
//            DataOutputStream wr = new DataOutputStream (
//                    connection.getOutputStream());
//            wr.writeBytes(urlParameters);
//            wr.close();
//
//            //Get Response
//            InputStream is = connection.getInputStream();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
//            String line;
//            while ((line = rd.readLine()) != null) {
//                response.append(line);
//                response.append('\r');
//            }
//            rd.close();
//            return response.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
    }




