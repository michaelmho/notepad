package com.example.tausi.everypad;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tausi.everypad.db.NoteDao;
import com.example.tausi.everypad.db.NotesDB;
import com.example.tausi.everypad.model.Note;


import java.util.Date;

public class EditeNoteActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
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
        if (id==R.id.add_image)
            addImageNote();
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
                temp.setNoteText(text);
                temp.setNoteDate(date);
                dao.updateNote(temp); //change text and date and update note on DB
            }


            if (temp.getId()==-1)
                dao.insertNote(temp); // insert and save
            else dao.updateNote(temp);

            finish(); //return to the MainActivity
        }
    }
    private void addImageNote(){
        openGallery();

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK  && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}
//else temp = new Note();


