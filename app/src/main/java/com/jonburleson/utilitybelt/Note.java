package com.jonburleson.utilitybelt;

import java.io.Serializable;

public class Note implements Serializable {

    private String Title;
    private String Content;
    private int Thumbnail;
    private boolean Flagged;

    public Note() {
    }

    public Note(String title, String content, int thumbnail, boolean flagged) {
        setTitle(title);
        setContent(content);
        setThumbnail(thumbnail);
        setFlagged(flagged);
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
}
