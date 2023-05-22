package com.example.maestro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {
    private MyOrientationEventListener mOrientationEventListener;
    private EditText mAuthor, mTitle;
    DbHelper db;
    NotesModel model;
    Button btn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(UploadActivity.this);
        setContentView(R.layout.activity_upload);
        mAuthor = findViewById(R.id.author);
        mTitle = findViewById(R.id.title);

        btn= findViewById(R.id.btn_add);
        if (getIntent().getExtras() != null && getIntent().hasExtra("id")) {
            model = db.getNModelByID(getIntent().getIntExtra("id", 0));
            if (model != null) {
                mAuthor.setText(model.getAuthor());
                mTitle.setText(model.getTitle() + "");


            } else {
                Toast.makeText(this, "Napotkano problem", Toast.LENGTH_SHORT).show();
            }
        } else {
            model = new NotesModel();
        }
        btn.setOnClickListener(this::saveNotes);

        mOrientationEventListener = new MyOrientationEventListener(this);

    }
    protected void onResume() {
        super.onResume();

        // Rejestrowanie MyOrientationEventListener
        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Wyrejestrowywanie MyOrientationEventListener
        mOrientationEventListener.disable();
    }
    public void saveNotes(View view) {
        String author = mAuthor.getText().toString();
        String title = mTitle.getText().toString();

        if (author.isEmpty() || title.isEmpty()) {
            Toast.makeText(this, "pola Autor i tytuł są wymagane", Toast.LENGTH_SHORT).show();
            return;
        }

        if (model.getId() == 0)
            model.setAuthor(mAuthor.getText().toString());

        model.setTitle(mTitle.getText().toString());

        if (model.getId() > 0) {
            if (db.updateNotes(model)) {
                Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (db.addNotes(model)) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        backLoginAcivity(view);
    }

    public void backLoginAcivity(View v) {
        Intent intent;
        if (model != null && model.getId() > 0) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, UploadActivity.class);
        }
        startActivity(intent);
    }
}
