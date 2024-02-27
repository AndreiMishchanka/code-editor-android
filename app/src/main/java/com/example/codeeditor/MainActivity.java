package com.example.codeeditor;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amrdeveloper.codeview.CodeView;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button fileButton;
    private Button gitButton;
    private CodeView codeView;
    private EditText openFileHint;
    private HashMap<Object, Boolean> canBeEnabled;
    private View openFileTextInput;
    private String currentFileName;
    private String currentProjectPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        OpenProjectController.setDisabled(this);
        OpenProjectController.openProjectInitialization(this);

        currentFileName = null;
        currentProjectPath = null;
        FilesController.createProjectsDirectory(this);

        GitController.gitClone("https://github.com/zendesk-mikalaiNavitski/GitTestRepository.git", this);
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

    public void setCurrentProjectPath(String projectPath) throws Exception{
        currentProjectPath = projectPath;
        DirectoryTreeController.setEnabled(this);
    }

    public String getCurrentFileName(){
        return currentFileName;
    }
    public String getCurrentProjectPath(){
        return currentProjectPath;
    }
}