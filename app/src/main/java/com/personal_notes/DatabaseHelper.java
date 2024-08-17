package com.personal_notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseInformation.DATABASE_NAME, null, DatabaseInformation.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + DatabaseInformation.NOTES_TABLE + " (" +
                DatabaseInformation.NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseInformation.NOTES_TITLE + " TEXT, " +
                DatabaseInformation.NOTES_SUB_TITLE + " TEXT, " +
                DatabaseInformation.NOTES_DESCRIPTION + " TEXT);";
        db.execSQL(createTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseInformation.NOTES_TABLE);
        onCreate(db);
    }


    public long addNotes(NotesInformation notesInformation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseInformation.NOTES_TITLE, notesInformation.getNote_title());
        values.put(DatabaseInformation.NOTES_SUB_TITLE, notesInformation.getNote_sub_title());
        values.put(DatabaseInformation.NOTES_DESCRIPTION, notesInformation.getNote_description());

        // Insert the note and get the row ID of the newly inserted row
        long result = db.insert(DatabaseInformation.NOTES_TABLE, null, values);
        db.close();
        return result;
    }


    public List<NotesInformation> getAllNotes() {
        List<NotesInformation> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseInformation.NOTES_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                NotesInformation note = new NotesInformation(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseInformation.NOTES_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseInformation.NOTES_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseInformation.NOTES_SUB_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseInformation.NOTES_DESCRIPTION))
                );
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notesList;
    }

    public boolean deleteNoteById(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(DatabaseInformation.NOTES_TABLE, DatabaseInformation.NOTES_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
        return rowsAffected > 0; // Returns true if a note was deleted
    }

    public boolean updateNoteTitle(int noteId, String newTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseInformation.NOTES_TITLE, newTitle);

        int rowsAffected = db.update(DatabaseInformation.NOTES_TABLE, values, DatabaseInformation.NOTES_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
        return rowsAffected > 0; // Returns true if the title was updated
    }

    public boolean updateNoteSubtitle(int noteId, String newSubtitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseInformation.NOTES_SUB_TITLE, newSubtitle);
        int rowsAffected = db.update(DatabaseInformation.NOTES_TABLE, values, DatabaseInformation.NOTES_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
        return rowsAffected > 0; // Returns true if a subtitle was updated
    }

    public boolean updateNoteDescription(int noteId, String newDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseInformation.NOTES_DESCRIPTION, newDescription);
        int rowsAffected = db.update(DatabaseInformation.NOTES_TABLE, values, DatabaseInformation.NOTES_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
        return rowsAffected > 0; // Returns true if a description was updated
    }

    public boolean updateNoteDetails(int noteId, String title, String subtitle, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseInformation.NOTES_TITLE, title);
        values.put(DatabaseInformation.NOTES_SUB_TITLE, subtitle);
        values.put(DatabaseInformation.NOTES_DESCRIPTION, description);

        int rowsAffected = db.update(DatabaseInformation.NOTES_TABLE, values, DatabaseInformation.NOTES_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();

        return rowsAffected > 0;
    }
}
