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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewFileActivity extends AppCompatActivity {

    Button backButton;
    Button CreateFileButton;

    EditText fileNameEditText;

    private void createFile(String fileName) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyLocalFiles", MODE_PRIVATE);
        if (sharedPreferences.contains(fileName)) {
            Toast.makeText(NewFileActivity.this, "File already exists", Toast.LENGTH_SHORT).show();
        } else {
            String templateText = "";
            if (fileName.endsWith(".cpp")) {
                templateText = "#include<iostream>\n\nint main(){\n\tstd::cout<<\"Hello World!\\n\";\n\treturn 0;\n}";
            }
            try {
                FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
                fileOutputStream.write(templateText.getBytes());
                fileOutputStream.close();
                Toast.makeText(NewFileActivity.this, "File created", Toast.LENGTH_SHORT).show();

                String filePath = new File(getFilesDir(), fileName).getAbsolutePath();
                Log.d("Debug", filePath);
//                /data/user/0/com.example.codeeditor/files/auf
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(NewFileActivity.this, "Error creating file", Toast.LENGTH_SHORT).show();
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(fileName, "");
            editor.apply();
            Toast.makeText(NewFileActivity.this, "File created", Toast.LENGTH_SHORT).show();
        }
    }

    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_file);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                
                finish();
            }
        });

        fileNameEditText = (EditText) findViewById(R.id.FileNameEditText);

        CreateFileButton = (Button) findViewById(R.id.CreateFileButton);
        CreateFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = fileNameEditText.getText().toString();                
                createFile(fileName);
            }
        });
    }
}
