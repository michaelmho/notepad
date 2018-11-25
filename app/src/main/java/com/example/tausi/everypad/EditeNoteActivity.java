package com.example.tausi.everypad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.tausi.everypad.db.NoteDao;
import com.example.tausi.everypad.db.NotesDB;
import com.example.tausi.everypad.model.Note;

import java.util.Date;

public class EditeNoteActivity extends AppCompatActivity {
private EditText inputNote;
private NoteDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_note);
        inputNote=findViewById(R.id.input_note);
        dao = NotesDB.getInstance(this).noteDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edite_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.save_note)
            onSaveNote();
        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {
        // TODO: save
        String text = inputNote.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime(); // get current system time
            Note note = new Note(text, date); // create new note
            dao.insertNote(note); // insert and save

            finish(); //return to the MainActivity
        }
    }
}

