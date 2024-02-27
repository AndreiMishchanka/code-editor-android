package com.example.codeeditor;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;
import com.amrdeveloper.codeview.CodeView;

import java.util.HashMap;

import java.io.*;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;



public class MainActivity extends AppCompatActivity {

    // private Button newFileButton;
    // private Button openFileButton;
    // private Button exitButton;
    // private Button fileExplorerButton;
    // private Button openInternalStorageButton;

    // private static final int READ_REQUEST_CODE = 42;
    // private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private Button fileButton;
    private Button gitButton;
    private CodeView codeView;
    private EditText openFileHint;
    private HashMap<Object, Boolean> canBeEnabled;
    private View openFileTextInput;
    private String currentFileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // createRequiredFolders();

        // newFileButton = findViewById(R.id.new_file_button);
        // newFileButton.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         Intent intent = new Intent(MainActivity.this, NewFileActivity.class);
        //         startActivity(intent);
        fileButton = FileButton.getFileButton(this);
        gitButton = GitButton.getGitButton(this);
        codeView = CodeEditorController.getCodeEditor(this);

        openFileHint = this.findViewById(R.id.openFileHint);
        openFileHint.setEnabled(false);
        CodeEditorController.DisableCodeEditor(this);

        NewFileController.setDisabled(this);
        NewFileController.newFileInitialization(this);
        OpenFileController.setDisabled(this);
        OpenFileController.openFileInitialization(this);

        currentFileName = null;
    }

    protected void disableOpenFileHint() {
        openFileHint.setVisibility(View.INVISIBLE);
    }

    protected void enableOpenFileHint() {
        openFileHint.setVisibility(View.VISIBLE);
    }

    protected void disableMainLayout(){
        canBeEnabled = new HashMap<>();
        disableLayout(this.findViewById(R.id.subMainLayout));
    }
    private void disableLayout(ViewGroup layout){
        if(layout.isEnabled()) canBeEnabled.put(layout, true);
        layout.setEnabled(false);
        layout.setAlpha(0.9F);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if(child.isEnabled()) canBeEnabled.put(child, true);
            child.setEnabled(false);
            child.setAlpha(0.9F);
            if (child instanceof ViewGroup) {
                disableLayout((ViewGroup) child);
            }
        }
    }

    protected void enableMainLayout(){
        enableLayout(this.findViewById(R.id.subMainLayout));
    }

    private void enableLayout(ViewGroup layout){
        if(Boolean.TRUE.equals(canBeEnabled.get(layout))) {
            layout.setEnabled(true);
            layout.setAlpha(1f);
        }
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if(Boolean.TRUE.equals(canBeEnabled.get(child))) {
                child.setEnabled(true);
                child.setAlpha(1f);
            }
            if (child instanceof ViewGroup) {
                enableLayout((ViewGroup) child);
            }
        }
    }

        // fileExplorerButton = findViewById(R.id.file_explorer);
        // fileExplorerButton.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         openFileExplorer();
        //     }
        // });

        // openInternalStorageButton = findViewById(R.id.openInternalStorageButton);
        // openInternalStorageButton.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         Intent intent = new Intent(MainActivity.this, InternalStorageActivity.class);
        //         startActivity(intent);
        //     }
        // });
    protected boolean getEditorEnabled(){
        return !(openFileHint.getVisibility() == View.VISIBLE);
    }

    public void setCurrentFileName(String fileName) throws Exception{
        if(fileName == null){
            currentFileName = null;
            enableOpenFileHint();
            CodeEditorController.setCode("", this);
            CodeEditorController.DisableCodeEditor(this);
            return;
        }
        currentFileName = fileName;
        disableOpenFileHint();
        CodeEditorController.setCode(FilesController.getFilesInside(fileName, this), this);
        CodeEditorController.EnableCodeEditor(this);
    }

    public String getCurrentFileName(){
        return currentFileName;
    }

    private void createRequiredFolders() {
        File localFilesFolder = new File(getFilesDir(), "Local Files");
        File projectsFolder = new File(getFilesDir(), "Projects");

        if (!localFilesFolder.exists()) {
            if (!localFilesFolder.mkdir()) {
                Log.e("MainActivity", "Failed to create Local Files folder");
            }
        }
        if (!projectsFolder.exists()) {
            if (!projectsFolder.mkdir()) {
                Log.e("MainActivity", "Failed to create Projects folder");
            }
        }
    }

    private void openFileExplorer() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                copyFileToInternalStorage(uri);
            }
        }
    }

    private void copyFileToInternalStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            String fileName = getFileNameFromUri(uri);

            File localFilesFolder = new File(getFilesDir(), "Local Files");
            if (!localFilesFolder.exists()) {
                localFilesFolder.mkdir();
            }
            File internalFile = new File(localFilesFolder.getPath(), fileName);
            OutputStream outputStream = new FileOutputStream(internalFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
            Toast.makeText(this, "File copied to internal storage", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error copying file", Toast.LENGTH_SHORT).show();
        }
    }


    private String getFileNameFromUri(Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

}
