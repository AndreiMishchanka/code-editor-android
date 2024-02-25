package com.example.codeeditor;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.view.ContentInfo;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.MenuCompat;

import java.util.Objects;

public class FileButton {

    public static Button getFileButton(MainActivity mainScreen){
        Button fileButton = mainScreen.findViewById(R.id.file_button);

        fileButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(mainScreen, R.style.CustomPopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, v);
                popup.getMenuInflater().inflate(R.menu.menu_file, popup.getMenu());
                popup.getMenu().setGroupVisible(3, false);
                if(mainScreen.getCurrentFileName() == null) {
                    popup.getMenu().findItem(R.id.action_save_file).setEnabled(false);
                    //popup.getMenu().findItem(R.id.action_save_file).setVisible(false);
                    popup.getMenu().setGroupVisible(R.id.group3, false);
                    popup.getMenu().setGroupDividerEnabled(true);
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item, mainScreen);
                    }
                });
                popup.show();
            }
        });

        return fileButton;
    }

    static public boolean onOptionsItemSelected(MenuItem item, MainActivity mainScreen) {
        switch (item.getItemId()) {
            case R.id.action_open_file:
                openFile(mainScreen);
                return true;
            case R.id.action_new_file:
                createNewFile(mainScreen);
                return true;
            case R.id.action_close_file:
                closeCurrentFile();
                return true;
            case R.id.action_save_file:
                saveFile(mainScreen);
                return true;
            default:
                return false;
        }
    }

    static private void openFile(MainActivity mainScreen) {
    }

    static private void createNewFile(MainActivity mainScreen) {
        mainScreen.disableMainLayout();
        OpenFileEnterTextController.setEnabled(mainScreen);
    }

    static private void closeCurrentFile() {
    }

    static private void saveFile(MainActivity mainScreen){
        String fileName = mainScreen.getCurrentFileName();
        String content = CodeEditorController.getCode(mainScreen);
        FilesController.saveFile(fileName, content, mainScreen);
    }
}
