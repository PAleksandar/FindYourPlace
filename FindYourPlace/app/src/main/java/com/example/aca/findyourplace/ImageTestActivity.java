package com.example.aca.findyourplace;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import com.example.aca.findyourplace.model.User;

import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageTestActivity extends AppCompatActivity {

    CircleImageView img;
    Bitmap image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);

        img= (CircleImageView) findViewById(R.id.test);

        int userId = (int) getIntent().getExtras().get("User");

       // AsyncTaskRunner runner = new AsyncTaskRunner();
        // String sleepTime = Integer.toString(userId);
       // runner.execute(sleepTime);



        try {
            User us = User.loadUser(userId);
            //////////////////////////////////
            try {
                byte [] encodeByte=Base64.decode(us.getImage(),Base64.DEFAULT);
                image=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                img.setImageBitmap(image);
            } catch(Exception e) {
                e.getMessage();

            }

            //img.setImageBitmap(image);
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0]);

                /////
                try {
                    User us = User.loadUser(time);
                    //////////////////////////////////
                    try {
                        byte [] encodeByte=Base64.decode(us.getImage(),Base64.DEFAULT);
                        image=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    } catch(Exception e) {
                        e.getMessage();
                        return null;
                    }
                    //////////////////////////////////
                   /* final ByteArrayOutputStream imageStream=us.getProfileImage();
                    byte[] byteArray = imageStream .toByteArray();
                    ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
                    image = BitmapFactory.decodeStream(arrayInputStream);
                    */
                    //img.setImageBitmap(image);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /////
               // Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            img.setImageBitmap(image);
            // finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ImageTestActivity.this,
                    "ProgressDialog",
                    "Wait for  seconds");
        }


        @Override
        protected void onProgressUpdate(String... text) {
           // finalResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}
