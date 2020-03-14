package com.jonburleson.utilitybelt;

public class Note {

    private String Title;
    private String Content;
    private int Thumbnail;
    private boolean Flagged;

    public Note() {
    }

    public Note(String title, String content, int thumbnail, boolean flagged) {
        Title = title;
        Content = content;
        Thumbnail = thumbnail;
        Flagged = flagged;
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
