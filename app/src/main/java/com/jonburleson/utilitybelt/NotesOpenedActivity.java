package com.jonburleson.utilitybelt;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class NotesOpenedActivity extends AppCompatActivity {

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

    public void Save() {
        String noteText = Objects.requireNonNull(noteTextField.getText()).toString();
        String noteTitle = noteTitleField.getText().toString();

        if (noteTitle.equals("")) {
            noteTitle = "Untitled_Note";
        }
        try {
            FileOutputStream fOut = new FileOutputStream(noteTitle, true);
            OutputStreamWriter out = new OutputStreamWriter(fOut);

            out.write(noteText);
            out.close();
            //Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Save();
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
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
                DialogFragment newFragment = new DialogFragmentTwoBtn(this);
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