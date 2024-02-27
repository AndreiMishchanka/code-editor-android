package com.example.codeeditor;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.TagOpt;

import java.io.File;

public class GitController {

    public static void gitClone(String gitLink, MainActivity mainScreen){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                File directory = mainScreen.getFilesDir();
                File newDirectory = new File(directory, "Projects");
                String gitName = gitLink.substring(gitLink.lastIndexOf('/') + 1, gitLink.lastIndexOf('.'));
                File newProjectDirectory = new File(newDirectory, gitName);
                try {
                    Git git = Git.cloneRepository()
                            .setDirectory(newProjectDirectory)
                            .setURI(gitLink)
                            .setCloneAllBranches(true)
                            .setTagOption(TagOpt.FETCH_TAGS)
                            .call();
                    return "Git Clone Succeeded";
                } catch (Exception e) { // Catch more general exceptions
                    Log.e("GitController", "Git clone error", e);
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(mainScreen, result, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public static void gitFetch(String gitLink, MainActivity mainScreen){

    }
}
