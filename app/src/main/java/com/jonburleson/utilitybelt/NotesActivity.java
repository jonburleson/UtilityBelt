package com.jonburleson.utilitybelt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class NotesActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.add_fab);
        fab.setAlpha(0.80f);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, NotesOpenedActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createNotesPreference() {
        TinyDB tinydb = new TinyDB(this);
        notes = new ArrayList<>();
        String root = Environment.getExternalStorageDirectory().toString();
        File noteDir = new File(root + "/notes");
        if(noteDir.exists()) {
            File[] files = noteDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    StringBuilder text = new StringBuilder();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null) {
                            text.append(line);
                            text.append('\n');
                        }
                        br.close();
                    } catch (Throwable t) {
                        Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
                    }
                    notes.add(new Note(file.getName(), text.toString(), R.drawable.ic_empty_thumbnail, false, file));
                }
            }
        }
        tinydb.putListNote("Notes", notes);
    }

    public void updateDb() {
        super.onStart();
        TinyDB tinydb = new TinyDB(this);

        if (sharedPref.contains("Notes")) {
            notes = tinydb.getListNote("Notes");
        }
        else {
            createNotesPreference();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        updateDb();

        RecyclerView rView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter rAdapter = new RecyclerViewAdapter(this, notes);
        rView.setLayoutManager(new GridLayoutManager(this, 3));
        rView.setAdapter(rAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
