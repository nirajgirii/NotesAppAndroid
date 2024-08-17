package com.personal_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditTitleActivity extends AppCompatActivity {

    private EditText newTitleEditText;
    private Button submitButton;
    private DatabaseHelper databaseHelper;
    private int noteId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit_title);

        newTitleEditText = findViewById(R.id.new_title);
        submitButton = findViewById(R.id.new_title_submit);
        databaseHelper = new DatabaseHelper(this);

        // Get current title and note ID from Intent
        Intent intent = getIntent();
        String currentTitle = intent.getStringExtra("title");
        noteId = intent.getIntExtra("noteId", -1);
        newTitleEditText.setText(currentTitle);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTitle();
            }
        });
    }

    private void updateTitle() {
        String newTitle = newTitleEditText.getText().toString().trim();

        if (newTitle.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.updateNoteTitle(noteId, newTitle)) {
            Toast.makeText(this, "Title updated successfully", Toast.LENGTH_SHORT).show();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("updatedTitle", newTitle);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            Toast.makeText(this, "Failed to update title", Toast.LENGTH_SHORT).show();
        }
    }
}
