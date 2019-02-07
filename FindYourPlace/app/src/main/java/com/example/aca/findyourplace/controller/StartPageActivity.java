package com.example.aca.findyourplace.controller;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aca.findyourplace.HomeFragment;
import com.example.aca.findyourplace.R;

public class StartPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    int userId;
    //private Fragment homeFragment=new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        mDrawerLayout=(DrawerLayout) findViewById(R.id.start_page_drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new HomeFragment()).commit();

        userId= (int) getIntent().getExtras().get("User");
        Log.d("Start page", "user id: "+userId);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView==null)
            Log.i("navigation bar" ," null");
        navigationView.setNavigationItemSelectedListener(this);
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.chat) {
            //Toast.makeText(CustomerMapsActivity.this,"chat", Toast.LENGTH_LONG).show();
            // Handle the camera action
            Log.d("chat", "onNavigationItemSelected: ");
            Intent chat = new Intent(this, ChatActivity.class);
            startActivity(chat);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.start_page_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
