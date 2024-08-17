package com.personal_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditDescriptionActivity extends AppCompatActivity {

    private EditText newDescriptionEditText;
    private Button submitButton;
    private DatabaseHelper databaseHelper;
    private int noteId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit_description);

        newDescriptionEditText = findViewById(R.id.new_description);
        submitButton = findViewById(R.id.new_description_submit);
        databaseHelper = new DatabaseHelper(this);

        // Get current description and note ID from Intent
        Intent intent = getIntent();
        String currentDescription = intent.getStringExtra("description");
        noteId = intent.getIntExtra("noteId", -1);
        newDescriptionEditText.setText(currentDescription);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDescription();
            }
        });
    }

    private void updateDescription() {
        String newDescription = newDescriptionEditText.getText().toString().trim();

        if (newDescription.isEmpty()) {
            Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.updateNoteDescription(noteId, newDescription)) {
            Toast.makeText(this, "Description updated successfully", Toast.LENGTH_SHORT).show();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("updatedDescription", newDescription);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            Toast.makeText(this, "Failed to update description", Toast.LENGTH_SHORT).show();
        }
    }
}
