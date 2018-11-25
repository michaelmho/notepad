package com.example.tausi.everypad.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.tausi.everypad.model.Note;

@Database(entities = Note.class, version = 1)
public abstract class NotesDB extends RoomDatabase {
    public abstract NoteDao noteDao();

    public static final String DATABASE_NAME = "notesDB";
    public static NotesDB instance;

    public static NotesDB getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, NotesDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}