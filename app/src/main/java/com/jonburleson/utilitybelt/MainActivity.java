package com.jonburleson.utilitybelt;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref = null;
    SharedPreferences.OnSharedPreferenceChangeListener spListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CardView button_flash_cards = (CardView)findViewById(R.id.button_flash_cards);
        final CardView button_notes = (CardView)findViewById(R.id.button_notes);
        final CardView button_calender = (CardView)findViewById(R.id.button_calender);
        final CardView button_to_do_list = (CardView)findViewById(R.id.button_to_do_list);
        final CardView button_iou = (CardView)findViewById(R.id.button_iou);
        final CardView button_travel = (CardView)findViewById(R.id.button_travel);

        button_calender.setEnabled(false);
        button_to_do_list.setEnabled(false);
        button_iou.setEnabled(false);
        button_travel.setEnabled(false);

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

        button_flash_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlashCardsActivity.class);
                startActivity(intent);
            }
        });

        button_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showSettingsPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());
        popup.setGravity(Gravity.END);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle popup menu item clicks here.
                int id = item.getItemId();

                if (id == R.id.action_settings) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });

        popup.show();
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
}
