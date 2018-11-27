package com.example.tausi.everypad.callbacks;

import com.example.tausi.everypad.model.Note;

public interface NoteEventListner {
    /**
     * call wen note clicked.
     *
     * @param note: note item
     */
    void onNoteClick(Note note);

    /**
     * call when long pressed note.
     *
     * @param note : item
     */
    void onNoteLongClick(Note note);
}