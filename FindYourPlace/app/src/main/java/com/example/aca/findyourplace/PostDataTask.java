package com.example.aca.findyourplace;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class PostDataTask extends AsyncTask<String, Void, String> {

    String mResult;

    ProgressDialog progressDialog;
    @Override
    protected String doInBackground(String... params) {
        try
        {
            return  postData(params[0]);
        }
        catch (IOException ex)
        {
            return "Network error";
        }
        catch (JSONException exj)
        {
            return "Data invalid...";
        }


    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
       // progressDialog=new ProgressDialog(MainActivity.this);
        //progressDialog.setMessage("Inserting data...");
        //progressDialog.show();
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);

        //set data response to textView
        mResult=result;

        //cancel progres dialog
        if(progressDialog !=null)
        {
            progressDialog.dismiss();
        }
    }

    private String postData(String urlPath) throws IOException, JSONException
    {
        StringBuilder result=new StringBuilder();
        BufferedWriter bufferedWriter=null;
        BufferedReader bufferedReader=null;
        try {
            //create data to send

            JSONObject dataToSend = new JSONObject();
            dataToSend.put("email", "Think twice code once");


            //initialize and config request, then connect to server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);//milisecond
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); //enable output (body data)
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            //write data into server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(dataToSend.toString());
            bufferedWriter.flush();

            //read data response from server
            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                result.append(line).append("\n");

            }
        }
        finally {
            if(bufferedReader!=null)
            {
                bufferedReader.close();
            }
            if(bufferedWriter!=null)
            {
                bufferedWriter.close();
            }
        }
        return result.toString();
    }
}
