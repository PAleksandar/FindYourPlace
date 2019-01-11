package com.example.aca.findyourplace;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Message2 {
    private String text;
    private int sender;
    private int receiver;
    private int convers;
    private Date date;

    public Message2(){}
    public Message2(String tekst, int sender, int receiver, int convers, Date datum) {
        this.text=tekst;
        this.sender = sender;
        this.receiver = receiver;
        this.convers = convers;
        this.date=datum;
    }

    public Message2 LoadMessage(){

        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            text=gdt.execute("http://192.168.1.108:5000/user/1").get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Message2 data = new Message2();
        Gson gson = new Gson();
        data= gson.fromJson(text,Message2.class);

        return data;
    }
    public ArrayList<Message2> loadMessages(int convId)
    {
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            text=gdt.execute(RabbitMQ.mreza+ "message/conversation/" + convId).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("cccc", text);
        Type listType = new TypeToken<ArrayList<Message2>>(){}.getType();
        ArrayList<Message2> data = new ArrayList<Message2>();
        Gson gson = new Gson();
        data= gson.fromJson(text,listType);
        Integer size = data.size();
        Log.d("list",size.toString());

        return data;

    }

    public String getTekst() {
        return text;
    }

    public void setTekst(String tekst) {
        this.text = tekst;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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




