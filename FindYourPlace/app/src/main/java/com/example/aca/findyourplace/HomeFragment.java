package com.example.aca.findyourplace;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aca.findyourplace.model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

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
        eventList=new ArrayList<>();
        readEvents();
        eventAdapter=new EventAdapter(getContext(),eventList);
        recyclerView.setAdapter(eventAdapter);

        return view;
    }

    private void readEvents()
    {

        // int id, String name, String tag,/* ArrayList<Byte> image,*/ String description, int like
        // , Date date, int placeId, int ownerUserId
        eventList.add(new Event(1,"Event 1", "Tag 1", "Opis", 3, new Date(),3,3));
        eventList.add(new Event(2,"Event 2", "Tag 2", "Opis", 3, new Date(),3,3));
        eventList.add(new Event(3,"Event 3", "Tag 3", "Opis", 3, new Date(),3,3));
    }
}
