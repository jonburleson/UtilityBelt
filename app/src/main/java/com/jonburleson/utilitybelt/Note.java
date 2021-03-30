package com.jonburleson.utilitybelt;

import android.os.Environment;

import java.io.File;
import java.io.Serializable;

public class Note implements Serializable {

    private String Title;
    private String Content;
    private int Thumbnail;
    private boolean Flagged;
    private File file;

    public Note() {
        setTitle("temp");
        setContent("");
        setThumbnail(R.drawable.ic_empty_thumbnail);
        setFlagged(false);

        String root = Environment.getExternalStorageDirectory().toString();
        File noteDir = new File(root + "/notes");
        if (!noteDir.exists()) {
            boolean madeDir = noteDir.mkdirs();
        }
        File tempFile = new File(noteDir, "temp");
        boolean didDelete = tempFile.delete();
        setFile(tempFile);
    }

    public Note(String title, String content, int thumbnail, boolean flagged, File nFile) {
        setTitle(title);
        setContent(content);
        setThumbnail(thumbnail);
        setFlagged(flagged);
        setFile(nFile);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public boolean isFlagged() {
        return Flagged;
    }

    public void setFlagged(boolean flagged) {
        Flagged = flagged;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File nFile) {
        file = nFile;
    }
}
