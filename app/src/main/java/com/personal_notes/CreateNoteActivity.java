package com.personal_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText editNoteTitle, editNoteSubtitle, editNoteDescription;
    private Button saveNoteButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        // Initialize UI components
        editNoteTitle = findViewById(R.id.edit_note_title);
        editNoteSubtitle = findViewById(R.id.edit_note_subtitle);
        editNoteDescription = findViewById(R.id.edit_note_description);
        saveNoteButton = findViewById(R.id.save_note_button);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Set click listener for the save button
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        // Get user input
        String title = editNoteTitle.getText().toString().trim();
        String subtitle = editNoteSubtitle.getText().toString().trim();
        String description = editNoteDescription.getText().toString().trim();

        // Validate input
        if (title.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.isEmpty()) {
            Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new NotesInformation object
        NotesInformation note = new NotesInformation(0, title, subtitle, description);

        // Save the note to the database
        long result = databaseHelper.addNotes(note);
        if (result > 0) {
            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();

            // Return a result to the NotesHomepageActivity
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish(); // Close the activity and return to NotesHomepageActivity
        } else {
            Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show();
        }
    }
}
