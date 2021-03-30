package com.jonburleson.utilitybelt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class NotesOpenedActivity extends AppCompatActivity implements DialogFragmentTwoBtn.Listener{

    LineEditText noteTextField;
    EditText noteTitleField;
    SharedPreferences sharedPref;
    TinyDB tinydb;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_notes_opened);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        tinydb = new TinyDB(this);
        ArrayList<Note> notes = tinydb.getListNote("Notes");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        noteTextField = findViewById(R.id.edit_text);
        noteTextField.setBackgroundColor(Color.parseColor("#f4dc72"));
        noteTitleField = findViewById(R.id.note_edit_title);

        Note note;
        if (sharedPref.contains("note")){
            position = tinydb.getInt("note");
            tinydb.remove("note");
            note = notes.get(position);
            noteTitleField.setText(note.getTitle());
            noteTextField.setText(note.getContent());
        }
        else {
            note = new Note();
            notes.add(0, note);
            position = 0;
        }

        FrameLayout textFieldBorder = findViewById(R.id.edit_text_border);
        textFieldBorder.setBackgroundColor(Color.parseColor("#f4dc72"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public String randomNameExtension(String name) {
        Random generator = new Random();
        int n = 99;
        n = generator.nextInt(n);
        return (name +"_"+ n);
    }

    public void updateDb(ArrayList<Note> notes, Note note) {
        notes.remove(position);
        notes.add(0, note);
        position = 0;
        tinydb.remove("Notes");
        tinydb.putListNote("Notes", notes);
    }

    public void deleteNoteFile(Note note) {
        File file = note.getFile();
        boolean didDelete = file.delete();
    }

    public void Save() {
        String noteText = Objects.requireNonNull(noteTextField.getText()).toString();
        String noteTitle = noteTitleField.getText().toString();
        ArrayList<Note> notes = tinydb.getListNote("Notes");
        Note note = notes.get(position);

        //set note title
        if (noteTitle.equals("")) {
            noteTitle = randomNameExtension("Untitled_Note");
        }
        note.setTitle(noteTitle);

        //rename temp/current file
        deleteNoteFile(note);
        String root = Environment.getExternalStorageDirectory().toString();
        File file = new File(root + "/notes", note.getTitle());
        boolean didDelete = file.delete();
        note.setFile(file);

        //set note content
        note.setContent(noteText);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter out = new OutputStreamWriter(fOut);

            out.write(noteText);
            out.close();
            //Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }

        //update notes list
        updateDb(notes, note);
    }

    @Override
    public void onBackPressed() {
        Save();
        Intent intent = new Intent(this, NotesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void askMethod(String resultCode) {
        try {
            if (resultCode.equals("RESULT_OK")) {
                ArrayList<Note> notes = tinydb.getListNote("Notes");
                Note note = notes.get(position);
                deleteNoteFile(note);
                updateDb(notes, note);

                Intent intent = new Intent(this, NotesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
            }
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_bookmark);
        ArrayList<Note> notes = tinydb.getListNote("Notes");
        Note note = notes.get(position);
        //ask the user for the opposite of current flag status
        if (note.isFlagged()) {
            item.setTitle("Unfavorite");
        } else {
            item.setTitle("Favorite");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_bookmark:
                ArrayList<Note> notes = tinydb.getListNote("Notes");
                Note note = notes.get(position);
                note.setFlagged(!note.isFlagged());
                updateDb(notes, note);
                return true;
            case R.id.action_thumbnail:
                //Intent intent = new Intent(this, SettingsActivity.class);
                //startActivity(intent);
                return true;
            case R.id.action_delete:
                DialogFragmentTwoBtn newFragment = new DialogFragmentTwoBtn(this);
                newFragment.setListener(this);
                Bundle b = new Bundle();
                b.putString(DialogFragmentTwoBtn.TITLE, "Warning: DELETE");
                b.putString(DialogFragmentTwoBtn.MESSAGE, "This note will be deleted. This cannot be undone.");
                newFragment.setArguments(b);
                newFragment.show(getSupportFragmentManager(), "delete");
                return true;
            case android.R.id.home:
                Save();
                Intent intent = new Intent(this, NotesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}