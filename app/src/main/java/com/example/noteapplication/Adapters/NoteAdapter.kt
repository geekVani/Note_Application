package com.example.noteapplication.Adapters

import android.content.Intent
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.R
import com.example.noteapplication.View.MainActivity
import com.example.noteapplication.View.UpdateActivity

// creating constructor of Main Activity for updating db content
class NoteAdapter(private val activity : MainActivity): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    //Note class of Model package
    var notes: List<com.example.noteapplication.Model.Note> = ArrayList()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        // define design elements
        val textViewTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val textViewDescription : TextView = itemView.findViewById(R.id.tvDescription)
        val cardview : CardView = itemView.findViewById(R.id.cardView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)

        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // transfer each column object to note holder
        // position index for each note
        var currentNote:com.example.noteapplication.Model.Note = notes[position]

        // using holder, reach to textview and cardview components
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description

        // for update operation
        holder.cardview.setOnClickListener {
            // using "activity" that represents main activity
            val intent = Intent(activity, UpdateActivity::class.java)

            // sending data of current note to new activity
            intent.putExtra("currentTitle", currentNote.title)
            intent.putExtra("currentDescription", currentNote.description)
            intent.putExtra("currentId", currentNote.id)

            // to update data in db
            // activity result launcher
            // accessing updateActivityResultLauncher directly
            activity.updateActivityResultLauncher.launch(intent)

        }
    }

    fun setNote(myNotes: List<com.example.noteapplication.Model.Note>){
        this.notes = myNotes
        notifyDataSetChanged()
    }

    // getting note position
    fun getNote(position: Int) : com.example.noteapplication.Model.Note{
        return notes[position]
    }
}