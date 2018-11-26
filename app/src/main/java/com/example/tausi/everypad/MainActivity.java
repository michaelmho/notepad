package com.example.tausi.everypad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tausi.everypad.adapters.NotesAdapter;
import com.example.tausi.everypad.callbacks.NoteEventListner;
import com.example.tausi.everypad.db.NoteDao;
import com.example.tausi.everypad.db.NotesDB;
import com.example.tausi.everypad.model.Note;
import com.example.tausi.everypad.utils.NoteUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.tausi.everypad.EditeNoteActivity.NOTE_EXTRA_Key;

public class MainActivity extends AppCompatActivity implements NoteEventListner {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private NoteDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // init recyclerView
        recyclerView = findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        

        // int fab Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 11/24/2018 add new note
                onAddNewNote();
            }
        });

        dao = NotesDB.getInstance(this).noteDao();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        List<Note> list = dao.getNotes();//get All notes from DataBases
        this.notes.addAll(list);
        this.adapter = new NotesAdapter(this, this.notes);
        // set listner to adapter
        this.adapter.setListner(this);
        this.recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    private void onAddNewNote() {
        // TODO: tomot
        startActivity(new Intent(this,EditeNoteActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {
        // TODO edit note
        Intent edit = new Intent (this, EditeNoteActivity.class);
        edit.putExtra(NOTE_EXTRA_Key,note.getId());
        startActivity(edit);

    }

    @Override
    public void onNoteLongClick(final Note note) {
        // TODO delete share

        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO delete Note
                        dao.deleteNote(note); // delete
                        loadNotes(); // refresh notes

                    }
                })
                .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO share note text
                        Intent share = new Intent(Intent.ACTION_SEND);
                        // logic to share
                        String text = note.getNoteText()+"\n Create on :"+
                                NoteUtils.dateFromLong(note.getNoteDate())+"By :"+getString(R.string.app_name);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT,text);
                        startActivity(share);
                    }
                })
                .create()
                .show();



    }
}
