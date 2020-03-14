package com.jonburleson.utilitybelt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    List<Note> Notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Notes = new ArrayList<>();
        Notes.add(new Note("test note","Lorem ipsum", R.drawable.ic_empty_thumbnail,true));

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        RecyclerViewAdapter rAdapter = new RecyclerViewAdapter(this, Notes);
        rView.setLayoutManager(new GridLayoutManager(this, 3));
        rView.setAdapter(rAdapter);
    }
}
