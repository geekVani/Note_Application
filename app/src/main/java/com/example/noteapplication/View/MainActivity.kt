package com.example.noteapplication.View

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.Adapters.NoteAdapter
import com.example.noteapplication.Model.Note
import com.example.noteapplication.NoteApplication
import com.example.noteapplication.R
import com.example.noteapplication.ViewModel.NoteViewModel
import com.example.noteapplication.ViewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel : NoteViewModel
    lateinit var addActivityResultLauncher : ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(this)
        recyclerView.adapter = noteAdapter

        // register activity for result
        registerActivityResultLauncher()

        // new view model object
        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        noteViewModel.myAllNotes.observe(this, Observer { notes ->

            // update UI
            noteAdapter.setNote(notes)
        })

        // to perform delete operation on slide action
        // cannot create instance of abstract class,
        // hence writing object of unanimous class
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0
        , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            // method for dragging & dropping in android
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // position of note
                // viewHolder.adapterPosition
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
                Toast.makeText(applicationContext,
                "Note is deleted successfully", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    // mandatory function: register activity for result
    fun registerActivityResultLauncher(){

        // for adding new note
        addActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        , ActivityResultCallback { resultAddNote ->
                val resultCode = resultAddNote.resultCode
                val data = resultAddNote.data

                if (resultCode == RESULT_OK && data != null){
                    // import data into string containers
                    val noteTitle : String = data.getStringExtra("title").toString()
                    val noteDescription : String = data.getStringExtra("description").toString()

                    val note = Note(noteTitle, noteDescription)

                    // adding new note to list
                    noteViewModel.insert(note)
                }
            }
        )
        // for updating new note
        updateActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        , ActivityResultCallback { resultUpdateNote ->
                val resultCode = resultUpdateNote.resultCode
                val data = resultUpdateNote.data

                if (resultCode == RESULT_OK && data != null){

                    val updatedTitle : String = data.getStringExtra("updatedTitle").toString()
                    val updatedDescription : String = data.getStringExtra("updatedDescription").toString()
                    val noteId = data.getIntExtra("noteId", -1)

                    val newNote = Note(updatedTitle, updatedDescription)
                    newNote.id = noteId

                    noteViewModel.update(newNote)
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_menu, menu)
        return true
    }

    private fun showDialogMessage() {
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Notes ?")
            .setMessage("If click Yes, all notes will be deleted. \nYou can swipe Left or Right to delete specific note.")
            .setIcon(R.drawable.alert_icon)
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                noteViewModel.deleteAllNotes()
            })
        dialogMessage.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_add_note -> {
                val intent = Intent(this, NoteAddActivity::class.java)
//                startActivity(intent)
                addActivityResultLauncher.launch(intent)
            }
            // fn to show dialog message on deleting all notes
            R.id.item_delete_note -> showDialogMessage()
/*
                Toast.makeText(applicationContext, "Delete icon was clicked", Toast.LENGTH_LONG)
                .show()
*/
        }
        return true
    }
}