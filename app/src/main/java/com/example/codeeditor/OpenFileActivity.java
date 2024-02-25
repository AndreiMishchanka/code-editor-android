package com.example.codeeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class OpenFileActivity extends AppCompatActivity {

    Button backButton;        
    private LinearLayout filesLayout;

    private void addButtonToLayout(Button button) {
        filesLayout.addView(button);
    }

    private Button createFileButton(String fileName) {
        Button button = new Button(this);
        button.setText(fileName);                    
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                
                Intent intent = new Intent(OpenFileActivity.this, FileEditorActivity.class);
                intent.putExtra("fileName", fileName);
                startActivity(intent);
                Toast.makeText(OpenFileActivity.this, "TODO", Toast.LENGTH_SHORT).show();
            }
        });
        return button;
    }

    public void generateAvailableFiles() {    
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyLocalFiles", MODE_PRIVATE);        
        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            addButtonToLayout(createFileButton(key));            
        }           
    }

    @Override 
    protected void onCreate(Bundle savedInstanceState) {
/*        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_file);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                
                finish();
            }
        });

        filesLayout = findViewById(R.id.filesLayout);
        
        generateAvailableFiles();*/
    }
}
