package com.jonburleson.utilitybelt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;
import java.util.Random;

public class NotesOpenedActivity extends AppCompatActivity implements DialogFragmentTwoBtn.Listener{

    LineEditText noteTextField;
    EditText noteTitleField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_opened);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        noteTextField = findViewById(R.id.edit_text);
        noteTextField.setBackgroundColor(Color.parseColor("#f4dc72"));

        noteTitleField = findViewById(R.id.note_edit_title);

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
        int n = 10000;
        n = generator.nextInt(n);
        return (name +"_"+ n);
    }

    public void Save() {
        String noteText = Objects.requireNonNull(noteTextField.getText()).toString();
        String noteTitle = noteTitleField.getText().toString();

        String root = Environment.getExternalStorageDirectory().toString();
        File noteDir = new File(root + "/notes");
        if (!noteDir.exists()) {
            boolean madeDir = noteDir.mkdirs();
        }

        if (noteTitle.equals("")) {
            noteTitle = "Untitled_Note";
            noteTitle = randomNameExtension(noteTitle);
        }
        File file = new File (noteDir, noteTitle);
        if (file.exists ()) {
            boolean didDelete = file.delete();
        }
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter out = new OutputStreamWriter(fOut);

            out.write(noteText);
            out.close();
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Save();
        finish();
    }

    @Override
    public void askMethod(String resultCode) {
        try {
            if (resultCode.equals("RESULT_OK")) {
                String noteTitle = noteTitleField.getText().toString();
                String root = Environment.getExternalStorageDirectory().toString();
                File noteDir = new File(root + "/notes");
                if (!noteDir.exists()) {
                    boolean madeDir = noteDir.mkdirs();
                }
                File file = new File(noteDir, noteTitle);
                if (file.exists()) {
                    boolean didDelete = file.delete();
                }

                Intent intent = new Intent(this, NotesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                finish();
            }
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_bookmark:
                //Intent intent = new Intent(this, SettingsActivity.class);
                //startActivity(intent);
                Save();
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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}