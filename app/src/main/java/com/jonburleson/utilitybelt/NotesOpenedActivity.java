package com.jonburleson.utilitybelt;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class NotesOpenedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_opened);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LineEditText textField = findViewById(R.id.edit_text);
        FrameLayout textFieldBorder = findViewById(R.id.edit_text_border);
        textField.setBackgroundColor(Color.parseColor("#f4dc72"));
        textFieldBorder.setBackgroundColor(Color.parseColor("#f4dc72"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes_edit, menu);
        return super.onCreateOptionsMenu(menu);
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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}