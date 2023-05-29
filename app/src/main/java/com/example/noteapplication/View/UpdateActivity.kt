package com.example.noteapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.noteapplication.R

class UpdateActivity : AppCompatActivity() {

    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var buttonCancel: Button
    lateinit var buttonSave: Button

    var currentId = -1 // initializing id


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        // change action bar title of adding note activity
        supportActionBar?.title = "Update Note"

        editTextTitle = findViewById(R.id.etUpdateTitle)
        editTextDescription = findViewById(R.id.etUpdateDescription)
        buttonCancel = findViewById(R.id.btnCancelUpdate)
        buttonSave = findViewById(R.id.btnSaveUpdate)

        // calling fun to update notes
        getAndSetData()

        buttonCancel.setOnClickListener {

            // process cancel, close page
            Toast.makeText(applicationContext,
                "No notes added...", Toast.LENGTH_SHORT).show()
            finish()
        }

        buttonSave.setOnClickListener {

            // adding a function call
            updateNote()

        }
    }

    fun updateNote() {
        val updatedTitle = editTextTitle.text.toString()
        val updatedDescription = editTextTitle.text.toString()
        // no need to define id as its already sent, not changed

        // sending data back to main activity
        val intent = Intent()
        intent.putExtra("updatedTitle", updatedTitle)
        intent.putExtra("updatedDescription", updatedDescription)
        // sending id, sending -1 won't let update be done by db
        if(currentId != -1){
            intent.putExtra("noteId", currentId)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    // to get data in Update Activity
    fun getAndSetData(){

        //get
        val currentTitle = intent.getStringExtra("currentTitle")
        val currentDescription = intent.getStringExtra("currentDescription")
        val currentId = intent.getIntExtra("currentId", /* problem in data */-1)

        //set
        editTextTitle.setText(currentTitle)
        editTextDescription.setText(currentDescription)

    }
}