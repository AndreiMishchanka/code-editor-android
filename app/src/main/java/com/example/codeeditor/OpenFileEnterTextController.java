package com.example.codeeditor;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class OpenFileEnterTextController {

    public static void openFileEnterTextInitialize(MainActivity mainScreen) {
        mainScreen.findViewById(R.id.OpenFileButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = OpenFileEnterTextController.getEnteredText(mainScreen);
                try{
                    FilesController.createFile(fileName, mainScreen);
                } catch (Exception e) {
                    setError(e.getMessage(), mainScreen);
                    return;
                }
                try{
                    mainScreen.setCurrentFileName(fileName);
                } catch (Exception e) {
                    setError(e.getMessage(), mainScreen);
                    return;
                }
                OpenFileEnterTextController.setDisabled(mainScreen);
                mainScreen.enableMainLayout();
            }
        });
    }

    public static void setDisabled(MainActivity mainScreen){
        mainScreen.findViewById(R.id.EnterFileLayout).setEnabled(false);
        mainScreen.findViewById(R.id.EnterFileLayout).setVisibility(View.INVISIBLE);

        mainScreen.findViewById(R.id.EnterOpenFileFieldBackground).setEnabled(false);
        mainScreen.findViewById(R.id.EnterOpenFileFieldBackground).setVisibility(View.INVISIBLE);

        mainScreen.findViewById(R.id.EnterOpenFileField).setEnabled(false);
        mainScreen.findViewById(R.id.EnterOpenFileField).setVisibility(View.INVISIBLE);

        mainScreen.findViewById(R.id.OpenFileButton).setEnabled(false);
        mainScreen.findViewById(R.id.OpenFileButton).setVisibility(View.INVISIBLE);

        mainScreen.findViewById(R.id.ErrorTextInput).setEnabled(false);
        mainScreen.findViewById(R.id.ErrorTextInput).setVisibility(View.INVISIBLE);
    }

    public static void setEnabled(MainActivity mainScreen){
        mainScreen.findViewById(R.id.EnterFileLayout).setEnabled(true);
        mainScreen.findViewById(R.id.EnterFileLayout).setVisibility(View.VISIBLE);

        mainScreen.findViewById(R.id.EnterOpenFileFieldBackground).setEnabled(true);
        mainScreen.findViewById(R.id.EnterOpenFileFieldBackground).setVisibility(View.VISIBLE);

        mainScreen.findViewById(R.id.EnterOpenFileField).setEnabled(true);
        mainScreen.findViewById(R.id.EnterOpenFileField).setVisibility(View.VISIBLE);

        mainScreen.findViewById(R.id.OpenFileButton).setEnabled(true);
        mainScreen.findViewById(R.id.OpenFileButton).setVisibility(View.VISIBLE);

        mainScreen.findViewById(R.id.ErrorTextInput).setEnabled(true);
        mainScreen.findViewById(R.id.ErrorTextInput).setVisibility(View.VISIBLE);
    }

    public static void setError(String error, MainActivity mainScreen) {
        TextView errorText = mainScreen.findViewById(R.id.ErrorTextInput);
        errorText.setText(error);
    }

    public static String getEnteredText(MainActivity mainScreen){
        TextInputEditText textInputer = mainScreen.findViewById(R.id.EnterOpenFileField);
        return Objects.requireNonNull(textInputer.getText()).toString();
    }
}
