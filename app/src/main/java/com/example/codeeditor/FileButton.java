package com.example.codeeditor;

import android.content.Context;
import android.view.ContentInfo;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.core.view.MenuCompat;

public class FileButton {

    public static Button getFileButton(MainActivity mainScreen){
        Button fileButton = mainScreen.findViewById(R.id.file_button);

        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(mainScreen, R.style.CustomPopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, v);
                popup.getMenuInflater().inflate(R.menu.menu_file, popup.getMenu());
                MenuCompat.setGroupDividerEnabled(popup.getMenu(), true);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });
                popup.show();
            }
        });

        return fileButton;
    }

    static public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_file:
                openFile();
                return true;
            case R.id.action_new_file:
                createNewFile();
                return true;
            case R.id.action_close_file:
                closeCurrentFile();
                return true;
            default:
                return false;
        }
    }

    static private void openFile() {
    }

    static private void createNewFile() {
    }

    static private void closeCurrentFile() {
    }
}
