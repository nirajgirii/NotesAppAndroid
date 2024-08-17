package com.personal_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NotesHomepageActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CREATE_NOTE = 1;
    private static final int REQUEST_CODE_EDIT_NOTE = 2;

    private LinearLayout notes_container;
    private Button createNoteButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_content);

        notes_container = findViewById(R.id.notes_container);
        createNoteButton = findViewById(R.id.create_note_button);
        databaseHelper = new DatabaseHelper(this);

        // Fetch notes from the database and display them
        displayNotes();

        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CreateNoteActivity for result
                Intent intent = new Intent(NotesHomepageActivity.this, CreateNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_NOTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE_NOTE) {
                // Refresh the note list when a note is created
                displayNotes();
            } else if (requestCode == REQUEST_CODE_EDIT_NOTE && data != null) {
                // Update the specific note in the list if an edit was performed
                int updatedNoteId = data.getIntExtra("noteId", -1);
                String updatedTitle = data.getStringExtra("updatedTitle");
                String updatedSubtitle = data.getStringExtra("updatedSubtitle");
                String updatedDescription = data.getStringExtra("updatedDescription");
                if (updatedNoteId != -1) {
                    updateNoteInList(updatedNoteId, updatedTitle, updatedSubtitle, updatedDescription);
                }
            }
        }
    }

    private void displayNotes() {
        List<NotesInformation> notesList = databaseHelper.getAllNotes();

        notes_container.removeAllViews(); // Clear previous views

        if (notesList.isEmpty()) {
            // Display a message if there are no notes
            TextView noNotesMessage = new TextView(this);
            noNotesMessage.setText("No notes available");
            noNotesMessage.setTextSize(18);
            notes_container.addView(noNotesMessage);
        } else {
            // Inflate and display notes
            for (final NotesInformation note : notesList) {
                View noteView = LayoutInflater.from(this).inflate(R.layout.note_single_content, notes_container, false);

                TextView noteTitle = noteView.findViewById(R.id.note_title);
                TextView noteSubtitle = noteView.findViewById(R.id.note_sub_title);
                ImageView editButton = noteView.findViewById(R.id.edit_button);
                ImageView deleteButton = noteView.findViewById(R.id.delete_button);

                noteTitle.setText(note.getNote_title());
                noteSubtitle.setText(note.getNote_sub_title());

                noteView.setTag(note.getNoteId());

                noteView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NotesHomepageActivity.this, EditPageActivity.class);
                        intent.putExtra("title", note.getNote_title());
                        intent.putExtra("subtitle", note.getNote_sub_title());
                        intent.putExtra("description", note.getNote_description());
                        intent.putExtra("noteId", note.getNoteId());
                        startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE);
                    }
                });

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NotesHomepageActivity.this, EditPageActivity.class);
                        intent.putExtra("title", note.getNote_title());
                        intent.putExtra("subtitle", note.getNote_sub_title());
                        intent.putExtra("description", note.getNote_description());
                        intent.putExtra("noteId", note.getNoteId());
                        startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE);
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentNoteId = (int) noteView.getTag();
                        if (databaseHelper.deleteNoteById(currentNoteId)) {
                            Toast.makeText(NotesHomepageActivity.this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                            displayNotes(); // Refresh the note list
                        } else {
                            Toast.makeText(NotesHomepageActivity.this, "Failed to delete note", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                notes_container.addView(noteView);
            }
        }
    }

    private void updateNoteInList(int noteId, String title, String subtitle, String description) {
        // Find and update the note view in the container
        for (int i = 0; i < notes_container.getChildCount(); i++) {
            View noteView = notes_container.getChildAt(i);
            if ((int) noteView.getTag() == noteId) {
                TextView noteTitle = noteView.findViewById(R.id.note_title);
                TextView noteSubtitle = noteView.findViewById(R.id.note_sub_title);

                noteTitle.setText(title);
                noteSubtitle.setText(subtitle);

                // Update note in the database
                databaseHelper.updateNoteDetails(noteId, title, subtitle, description);
                return;
            }
        }
    }
}
