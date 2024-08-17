package com.personal_notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditPageActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_EDIT_TITLE = 1;
    private static final int REQUEST_CODE_EDIT_SUBTITLE = 2;
    private static final int REQUEST_CODE_EDIT_DESCRIPTION = 3;

    private TextView title, subtitle, description;
    private int noteId; // Store the note ID

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_editpage);

        title = findViewById(R.id.note_title);
        subtitle = findViewById(R.id.note_sub_title);
        description = findViewById(R.id.note_description);

        // Populate the TextViews with data from the selected note
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        subtitle.setText(intent.getStringExtra("subtitle"));
        description.setText(intent.getStringExtra("description"));
        noteId = intent.getIntExtra("noteId", -1); // Get the note ID

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editTitleIntent = new Intent(EditPageActivity.this, EditTitleActivity.class);
                editTitleIntent.putExtra("title", title.getText().toString());
                editTitleIntent.putExtra("noteId", noteId); // Pass the note ID
                startActivityForResult(editTitleIntent, REQUEST_CODE_EDIT_TITLE);
            }
        });

        subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editSubtitleIntent = new Intent(EditPageActivity.this, EditSubTitleActivity.class);
                editSubtitleIntent.putExtra("subtitle", subtitle.getText().toString());
                editSubtitleIntent.putExtra("noteId", noteId); // Pass the note ID
                startActivityForResult(editSubtitleIntent, REQUEST_CODE_EDIT_SUBTITLE);
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editDescriptionIntent = new Intent(EditPageActivity.this, EditDescriptionActivity.class);
                editDescriptionIntent.putExtra("description", description.getText().toString());
                editDescriptionIntent.putExtra("noteId", noteId); // Pass the note ID
                startActivityForResult(editDescriptionIntent, REQUEST_CODE_EDIT_DESCRIPTION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_EDIT_TITLE:
                    String updatedTitle = data.getStringExtra("updatedTitle");
                    if (updatedTitle != null) {
                        title.setText(updatedTitle);
                    }
                    break;

                case REQUEST_CODE_EDIT_SUBTITLE:
                    String updatedSubtitle = data.getStringExtra("updatedSubtitle");
                    if (updatedSubtitle != null) {
                        subtitle.setText(updatedSubtitle);
                    }
                    break;

                case REQUEST_CODE_EDIT_DESCRIPTION:
                    String updatedDescription = data.getStringExtra("updatedDescription");
                    if (updatedDescription != null) {
                        description.setText(updatedDescription);
                    }
                    break;
            }
        }
    }
}
