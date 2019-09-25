package com.majinor.esportal.model;

public class DataNote {
    String text;
    String comment;
    String date;

    public DataNote(String text, String comment, String date) {
        this.text = text;
        this.comment = comment;
        this.date = date;
    }

    public String getTextss() {
        return text;
    }

    public String getCommentss() {
        return comment;
    }

    public String getDatess() {
        return date;
    }
}