package com.example.noteapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.noteapplication.R
import com.example.noteapplication.R.*

class NoteAddActivity : AppCompatActivity() {

    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var buttonCancel: Button
    lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_note_add)

        // change action bar title of adding note activity
        supportActionBar?.title = "Add New Note"

        editTextTitle = findViewById(R.id.etAddTitle)
        editTextDescription = findViewById(R.id.etAddDescription)
        buttonCancel = findViewById(R.id.btnCancel)
        buttonSave = findViewById(R.id.btnSave)

        buttonCancel.setOnClickListener {

            // process cancel, close page
            Toast.makeText(applicationContext,
            "No notes added...", Toast.LENGTH_SHORT).show()
            finish()
        }

        buttonSave.setOnClickListener {

            // adding a function call
            saveNote()

        }
    }

    fun saveNote() {
        val noteTitle : String = editTextTitle.text.toString()
        val noteDescription : String = editTextDescription.text.toString()

        // sending data to main activity using intents
        // it can also be done using instance of db class
        val intent = Intent()
        intent.putExtra("title", noteTitle)
        intent.putExtra("description", noteDescription)
        setResult(RESULT_OK, intent)
        finish()
    }
}