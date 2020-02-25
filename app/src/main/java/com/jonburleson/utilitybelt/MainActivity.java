package com.jonburleson.utilitybelt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref = null;
    SharedPreferences.OnSharedPreferenceChangeListener spListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        updateNightMode();
        spListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPref,
                                                          String key) {
                        switch (key) {
                            case "darkMode":
                                //Toast.makeText(MainActivity.this, "Restart needed to take effect", Toast.LENGTH_SHORT).show();
                                updateNightMode();
                                break;
                            default :
                                break;
                        }
                    }
                };
        sharedPref.registerOnSharedPreferenceChangeListener(spListener);
    }

    public void updateNightMode() {
        boolean darkValue = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DARK_MODE, false);
        if (darkValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    //@Override
    //public void onStart() {
    //    super.onStart();
    //}

    //@Override
    //public void onResume() {
    //    super.onResume();
    //    sharedPref.registerOnSharedPreferenceChangeListener(spListener);
    //}

    //@Override
    //public void onPause() {
    //    super.onPause();
    //    sharedPref.unregisterOnSharedPreferenceChangeListener(spListener);
    //}

    //@Override
    //public void onStop() {
    //    super.onStop();
    //}

    //@Override
    //public void onRestart() {
    //    super.onRestart();
    //}

    //@Override
    //public void onDestroy() {
    //    super.onDestroy();
    //    sharedPref.unregisterOnSharedPreferenceChangeListener(spListener);
    //}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
