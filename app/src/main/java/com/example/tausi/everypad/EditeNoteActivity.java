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
    private Note temp;
    public static final String NOTE_EXTRA_Key="note_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_note);
        inputNote = findViewById(R.id.input_note);
        dao = NotesDB.getInstance(this).noteDao();
        if (getIntent().getExtras() != null){
            int id = getIntent().getExtras().getInt(NOTE_EXTRA_Key, 0);
            temp = dao.getNoteById(id);
            inputNote.setText(temp.getNoteText());
        }

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

            // if note exist update else craete new
            if (temp == null){
                temp = new Note(text,date);
                dao.insertNote(temp); //create new note and insert to database
            }else {
                temp.setNoteDate(date);
                temp.setNoteText(text);
                dao.updateNote(temp); //change text and date and update note on DB
            }


            if (temp.getId()==-1)
                dao.insertNote(temp); // insert and save
            else dao.updateNote(temp);

            finish(); //return to the MainActivity
        }
    }
}
//else temp = new Note();
