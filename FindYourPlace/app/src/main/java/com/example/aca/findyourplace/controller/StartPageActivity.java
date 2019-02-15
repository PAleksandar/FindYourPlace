package com.example.aca.findyourplace.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aca.findyourplace.ChatFragment;
import com.example.aca.findyourplace.HomeFragment;
import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.model.User;

import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartPageActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    int userId;
    CircleImageView img;
    //private Fragment homeFragment=new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

       // img=(CircleImageView) findViewById(R.id.test_load_image_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.start_page_drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new HomeFragment()).commit();

        userId = (int) getIntent().getExtras().get("User");
        Log.d("Start page @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "user id: " + userId);




    /*
            try {
                User us = User.loadUser(userId);
                //////////////////////////////////
                try {
                    byte [] encodeByte=Base64.decode(us.getImage(),Base64.DEFAULT);
                    Bitmap image=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    img.setImageBitmap(image);
                } catch(Exception e) {
                    e.getMessage();

                }

                //img.setImageBitmap(image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    */


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView == null)
            Log.i("navigation bar", " null");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.chat) {
                   /*
                    Log.d("chat", "onNavigationItemSelected: ");
                    Intent chat = new Intent(StartPageActivity.this, ChatActivity.class);
                    startActivity(chat);cm
                    */
                   //otkomentarisati obavezno
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new ChatFragment(userId)).commit();

                }
                else if(id ==R.id.sign_out)
                {
                    try {
                        User.isActive(userId,false);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(StartPageActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.start_page_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    ////////////////


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







}
