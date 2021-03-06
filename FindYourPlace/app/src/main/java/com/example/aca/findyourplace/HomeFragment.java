package com.example.aca.findyourplace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.aca.findyourplace.model.Conversation;
import com.example.aca.findyourplace.model.Event;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{


    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private int userId;
    private Thread subscribeThread;
    private RabbitMQ eventRabbit = new RabbitMQ();
    Context mContext;

    @SuppressLint("ValidFragment")
    public HomeFragment(int userId)
    {
        this.userId=userId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        eventList=new ArrayList<>();
        readEvents();
        setHasOptionsMenu(true);
        eventRabbit.setupConnectionFactory();
        final Handler incomingMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = msg.getData().getString("msg");

                //tv.append(ft.format(now) + ' ' + message + "\n");
                //tv.append(message + "\n");
                Log.d("Stiglo", message);

                Gson gson = new Gson();
                Event e = gson.fromJson(message,Event.class);

                eventList.add(e);
                eventAdapter.update(eventList);


            }
        };
        eventRabbit.subscribe(incomingMessageHandler,subscribeThread,"event");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);


        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        eventAdapter=new EventAdapter(getContext(),eventList,userId);
        recyclerView.setAdapter(eventAdapter);

        return view;
    }

    private void readEvents()
    {
        eventList.addAll(Event.loadEvents());

        // int id, String name, String tag,/* ArrayList<Byte> image,*/ String description, int like
        // , Date date, int placeId, int ownerUserId
         //eventList.add(new Event(1,"A event", "Tag 1", "Opis 1", 7, new Date(),3,1));
        // eventList.add(new Event(2,"B event", "Tag 2", "Opis 2", 3, new Date(),3,2));
        //eventList.add(new Event(3,"C event", "Tag 3", "", 5, new Date(),3,3));
    }

    ///////////////////////////////

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
       /* if(newText ==null || newText.trim().isEmpty())
        {
            resetSearch();
            return true;
        } */

        List<Event> filteredValues=new ArrayList<>(eventList);
        for(Event value:eventList)
        {
            if(!value.getName().toLowerCase().contains(newText.toLowerCase()))
            {
                filteredValues.remove(value);
            }
        }

       // eventAdapter=new EventAdapter (mContext,eventList);
        //eventAdapter.notifyDataSetChanged();
        //recyclerView.setAdapter(eventAdapter);
        eventAdapter.update(filteredValues);
        //recyclerView.notify();


        return true;
    }

    public void resetSearch()
    {
        eventAdapter=new EventAdapter (mContext,eventList,userId);
        recyclerView.setAdapter(eventAdapter);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chat_fragment_menu, menu);
        MenuItem item = menu.findItem(R.id.chat_fragment_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        //searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu,inflater);

    }
}
