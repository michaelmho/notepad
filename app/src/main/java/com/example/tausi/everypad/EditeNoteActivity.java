package com.example.tausi.everypad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditeNoteActivity extends AppCompatActivity {
private EditText inputNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_note);
        inputNote=findViewById(R.id.input_note);

    }
}
