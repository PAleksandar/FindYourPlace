package com.example.aca.findyourplace.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.aca.findyourplace.Invoker;
import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.StartChatFragment;
import com.example.aca.findyourplace.StartLoginActivity;
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
    Invoker invoker;
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

        userId = (int) getIntent().getExtras().get("User");

        invoker=new Invoker();
        invoker.addCommand(R.id.chat,new StartChatFragment(userId,getSupportFragmentManager()));
        invoker.addCommand(R.id.sign_out, new StartLoginActivity(userId,StartPageActivity.this));

        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new HomeFragment()).commit();


        Log.d("Start page @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" +
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "user id: " + userId);





        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView == null)
            Log.i("navigation bar", " null");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                invoker.getCommand(id).execute();
               // if (id == R.id.chat) {
                   /*
                    Log.d("chat", "onNavigationItemSelected: ");
                    Intent chat = new Intent(StartPageActivity.this, ChatActivity.class);
                    startActivity(chat);cm
                    */
                   //otkomentarisati obavezno
                    //FragmentManager m=getSupportFragmentManager();
                   //  getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new ChatFragment(userId)).commit();

               // }

                /*
                if(id ==R.id.sign_out)
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

*/
/*
//prebaciti u command
                else if (id == R.id.event2)
                {
                    Intent intent = new Intent(StartPageActivity.this, EventMapsActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.eventDodaj)
                {
                    Intent intent = new Intent(StartPageActivity.this, AddEventActivity.class);
                    startActivity(intent);
                }
*/

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

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_fragment_menu, menu);

        return true;
    }
    */
}
