package com.personal_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditSubTitleActivity extends AppCompatActivity {

    private EditText newSubtitleEditText;
    private Button submitButton;
    private DatabaseHelper databaseHelper;
    private int noteId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit_sub_title);

        newSubtitleEditText = findViewById(R.id.new_subtitle);
        submitButton = findViewById(R.id.new_subtitle_submit);
        databaseHelper = new DatabaseHelper(this);

        // Get current subtitle and note ID from Intent
        Intent intent = getIntent();
        String currentSubtitle = intent.getStringExtra("subtitle");
        noteId = intent.getIntExtra("noteId", -1);
        newSubtitleEditText.setText(currentSubtitle);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSubtitle();
            }
        });
    }

    private void updateSubtitle() {
        String newSubtitle = newSubtitleEditText.getText().toString().trim();

        if (newSubtitle.isEmpty()) {
            Toast.makeText(this, "Subtitle cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.updateNoteSubtitle(noteId, newSubtitle)) {
            Toast.makeText(this, "Subtitle updated successfully", Toast.LENGTH_SHORT).show();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("updatedSubtitle", newSubtitle);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            Toast.makeText(this, "Failed to update subtitle", Toast.LENGTH_SHORT).show();
        }
    }
}
