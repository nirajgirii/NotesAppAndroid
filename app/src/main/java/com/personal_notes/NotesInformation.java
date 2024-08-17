package com.personal_notes;

public class NotesInformation {
    private int noteId;
    private String note_title;
    private String note_sub_title;
    private String note_description;

    // Constructor
    public NotesInformation(int noteId, String note_title, String note_sub_title, String note_description) {
        this.noteId = noteId;
        this.note_title = note_title;
        this.note_sub_title = note_sub_title;
        this.note_description = note_description;
    }

    // Getter and Setter for noteId
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    // Getter and Setter for note_title
    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    // Getter and Setter for note_sub_title
    public String getNote_sub_title() {
        return note_sub_title;
    }

    public void setNote_sub_title(String note_sub_title) {
        this.note_sub_title = note_sub_title;
    }

    // Getter and Setter for note_description
    public String getNote_description() {
        return note_description;
    }

    public void setNote_description(String note_description) {
        this.note_description = note_description;
    }
}
