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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class FileEditorActivity extends AppCompatActivity {

    Button backButton;               
    Button saveButton; 
    String fileName;
    EditText codeTextView;
    String fileContent = "";

    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_file_editor);

        fileName = getIntent().getStringExtra("fileName");

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                
                finish();
            }
        });

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                saveFile();                     
            }
        });

        readFileContent(fileName);

        codeTextView = (EditText) findViewById(R.id.codeTextView);
        codeTextView.setText(fileContent);
    }

    private void saveFile() {
        String text = codeTextView.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();
            Toast.makeText(FileEditorActivity.this, "File saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(FileEditorActivity.this, "Error saving file", Toast.LENGTH_SHORT).show();
        }
    }

    private void readFileContent(String fileName) {
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent += line + "\n";
            }
            fileInputStream.close();
            Toast.makeText(FileEditorActivity.this, "File has been saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(FileEditorActivity.this, "Error reading file", Toast.LENGTH_SHORT).show();
        }
    }
}
