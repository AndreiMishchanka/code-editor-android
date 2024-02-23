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

    private Button fileButton;
    private Button gitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileButton = FileButton.getFileButton(this);
        gitButton = GitButton.getGitButton(this);
    }

}