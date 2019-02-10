package com.example.aca.findyourplace.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.RabbitMQ;
import com.example.aca.findyourplace.model.PostDataTask;
import com.example.aca.findyourplace.model.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    MaterialEditText mEmail, mPassword, mName, mLastName;
    Button mImage, mFinish, mDate;
    Date date;
    private int mYear, mMonth, mDay;
    private final static int Gallery_Pick = 1;
    Bitmap image;
    CircleImageView slika;
    byte[] imageBytes;
    Blob imageBlob;
    ByteArrayOutputStream byteArrayOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        mPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        mName = (MaterialEditText) findViewById(R.id.edtName);
        mLastName = (MaterialEditText) findViewById(R.id.lastName);

        mImage = (Button) findViewById(R.id.buttonAddImage);
        mFinish = (Button) findViewById(R.id.button);
        mDate=(Button) findViewById(R.id.dateButton);
        slika=(CircleImageView) findViewById(R.id.test_image_view);
        date=new Date(System.currentTimeMillis());


        mFinish.setOnClickListener((view)->{

            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String name = mName.getText().toString();
            String lastName = mLastName.getText().toString();
            String us=null;

            //int id, String email, String password, String firstName, String lastName, boolean isActive, Date birthday
            User user=new User(0,email,password,name,lastName,true,date,byteArrayOutputStream);

            try {
                us=user.saveUser();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           /* PostDataTask pdt = new PostDataTask();
            // pdt.SetJSONMessage(et.getText().toString(),1,2,1);
            pdt.SetJsonObject(user);
            try {
                us=pdt.execute(RabbitMQ.mreza+"user").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
*/

            JsonObject jsonObject = new JsonParser().parse(us).getAsJsonObject();
            int userId=jsonObject.get("id").getAsInt();

            Intent intent = new Intent(this, StartPageActivity.class);
            intent.putExtra("User",userId);
            startActivity(intent);

        });


        mDate.setOnClickListener((view)->{

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {


                          //  Log.d("Datum", "godina: "+year+", mesec: "+monthOfYear+", dan: "+dayOfMonth);
                            date=new Date(year-1900,monthOfYear,dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        });


        mImage.setOnClickListener((view)->{

            try{
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }catch(Exception exp){
                Log.i("Error",exp.toString());
            }


        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            try {
                final InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                image = BitmapFactory.decodeStream(imageStream);
                slika.setImageBitmap(image);

                imageBytes=IOUtils.toByteArray(imageStream);

                 byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
               // imageBlob=imageBytes;
                imageBlob=new Blob() {
                    @Override
                    public long length() throws SQLException {
                        return 0;
                    }

                    @Override
                    public byte[] getBytes(long pos, int length) throws SQLException {
                        return new byte[0];
                    }

                    @Override
                    public InputStream getBinaryStream() throws SQLException {
                        return null;
                    }

                    @Override
                    public long position(byte[] pattern, long start) throws SQLException {
                        return 0;
                    }

                    @Override
                    public long position(Blob pattern, long start) throws SQLException {
                        return 0;
                    }

                    @Override
                    public int setBytes(long pos, byte[] bytes) throws SQLException {
                        return 0;
                    }

                    @Override
                    public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
                        return 0;
                    }

                    @Override
                    public OutputStream setBinaryStream(long pos) throws SQLException {
                        return null;
                    }

                    @Override
                    public void truncate(long len) throws SQLException {

                    }

                    @Override
                    public void free() throws SQLException {

                    }

                    @Override
                    public InputStream getBinaryStream(long pos, long length) throws SQLException {
                        return null;
                    }
                };
                imageBlob.setBytes(0,imageBytes);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //image_view.setImageBitmap(bmp);

            //to know about the selected image width and height
          //  Toast.makeText(MainActivity.this, image_view.getDrawable().getIntrinsicWidth()+" & "+image_view.getDrawable().getIntrinsicHeight(), Toast.LENGTH_SHORT).show();
        }

    }

}