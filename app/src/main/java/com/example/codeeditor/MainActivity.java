package com.example.codeeditor;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openMenuButton = findViewById(R.id.file_button);
        openMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(MainActivity.this, R.style.CustomPopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, v);
                // Inflate the PopupMenu with the menu XML
                popup.getMenuInflater().inflate(R.menu.menu_file, popup.getMenu());
                MenuCompat.setGroupDividerEnabled(popup.getMenu(), true);
                // Set a click listener for menu item clicks
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        // Delegate to the onOptionsItemSelected method
                        return onOptionsItemSelected(item);
                    }
                });
                // Show the popup menu
                popup.show();
            }
        });
    }

    // Handle action bar item clicks here.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_open_file:
                // Code to open a file
                openFile();
                return true;
            case R.id.action_new_file:
                // Code to create a new file
                createNewFile();
                return true;
            case R.id.action_close_file:
                // Code to close the current file
                closeCurrentFile();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    // Methods for menu item actions
    private void openFile() {
        // Implementation for opening a file
    }

    private void createNewFile() {
        // Implementation for creating a new file
    }

    private void closeCurrentFile() {
        // Implementation for closing the current file
    }
}