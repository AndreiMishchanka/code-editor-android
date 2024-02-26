package com.example.codeeditor;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class FilesController {

    protected static void createFile(String fileName, MainActivity mainScreen) throws Exception {
        SharedPreferences sharedPreferences = mainScreen.getApplicationContext().getSharedPreferences("MyLocalFiles", MODE_PRIVATE);
        if (sharedPreferences.contains(fileName)) {
            throw new Exception("File " + fileName + " already exists");
        } else {
            String templateText = "";
            if (fileName.endsWith(".cpp")) {
                templateText = "#include<iostream>\n\nint main(){\n\tstd::cout<<\"Hello World!\\n\";\n\treturn 0;\n}";
            }
            try {
                FileOutputStream fileOutputStream = mainScreen.openFileOutput(fileName, MODE_PRIVATE);
                fileOutputStream.write(templateText.getBytes());
                fileOutputStream.close();
                Toast.makeText(mainScreen, "File created", Toast.LENGTH_SHORT).show();

                String filePath = new File(mainScreen.getFilesDir(), fileName).getAbsolutePath();
                Log.d("Debug", filePath);
//                /data/user/0/com.example.codeeditor/files/auf
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(mainScreen, "Error creating file", Toast.LENGTH_SHORT).show();
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(fileName, "");
            editor.apply();
            Toast.makeText(mainScreen, "File created", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getFilesInside(String fileName, MainActivity mainScreen) throws Exception {
        SharedPreferences sharedPreferences = mainScreen.getApplicationContext().getSharedPreferences("MyLocalFiles", MODE_PRIVATE);
        if (!sharedPreferences.contains(fileName)) {
            throw new Exception("File " + fileName + " do not exists");
        } else
        {
            StringBuilder stringBuilder = new StringBuilder();
            FileInputStream fileInputStream = mainScreen.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            fileInputStream.close();
            return stringBuilder.toString();
        }
    }

    public static void saveFile(String fileName, String content, MainActivity mainScreen) {
        try {
            FileOutputStream fileOutputStream = mainScreen.openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
            Toast.makeText(mainScreen, "File saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mainScreen, "Error saving file", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isFileExist(String fileName, MainActivity mainScreen) {
        SharedPreferences sharedPreferences = mainScreen.getApplicationContext().getSharedPreferences("MyLocalFiles", MODE_PRIVATE);
        return sharedPreferences.contains(fileName);
    }

    public static Map<String, ?> getAllLocalFiles(MainActivity mainScreen) {
        SharedPreferences sharedPreferences = mainScreen.getApplicationContext().getSharedPreferences("MyLocalFiles", MODE_PRIVATE);
        return sharedPreferences.getAll();
    }
}
