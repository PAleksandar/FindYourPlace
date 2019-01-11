package com.example.aca.findyourplace;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



class GetDataTask extends AsyncTask<String, Void, String> {

    static String mResult;



    ProgressDialog progressDialog;
    @Override
    protected String doInBackground(String... params) {
        try
        {
            return  getData(params[0]);
        }
        catch (IOException ex)
        {
            return "Network error" + ex.toString();
        }

    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
       // progressDialog=new ProgressDialog(MainActivity.this);
        //progressDialog.setMessage("Loading data...");
       // progressDialog.show();
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);

        //set data response to textView
        mResult= result;

        //Log.d("user: ",mResult);

        //cancel progres dialog
        if(progressDialog !=null)
        {
            progressDialog.dismiss();
        }
    }

    private String getData(String urlPath) throws IOException
    {
        StringBuilder result=new StringBuilder();
        BufferedReader bufferedReader=null;
        try
        {

            //Initialize and config requiest, then connect to server
            URL url =new URL(urlPath);
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);//milisecond
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.connect();

            //Read data response from server
            InputStream inputStream=urlConnection.getInputStream();
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line=bufferedReader.readLine()) != null)
            {
                result.append(line).append("\n");
            }
        }
        finally {

            if(bufferedReader !=null)
            {
                bufferedReader.close();
            }
        }
        return result.toString();
    }
}
