/* ****************************************************
* Repository class in architectural components
* used to seperate codes
* works as an API
* Abstract access from multiple databse access
* for rest of application
* decides best data from network & where it comes from
* bridge between resources
* ******************************************************* */

package com.example.noteapplication.Repository

import androidx.annotation.WorkerThread
import com.example.noteapplication.Model.Note
import com.example.noteapplication.Room.NoteDAO
import kotlinx.coroutines.flow.Flow

// constructor for DAO, not entire db
class NoteRepository(private val noteDAO : NoteDAO) {

    // object, flow used to know the changes
    val myAllNotes : Flow<List<Note>> = noteDAO.getAllNotes()

    // operations in single thread(WorkerThread): Optional
    @WorkerThread
    suspend fun insert(note: Note){
        noteDAO.insert(note)
    }

    @WorkerThread
    suspend fun update(note: Note){
        noteDAO.update(note)
    }

    @WorkerThread
    suspend fun delete(note: Note){
        noteDAO.delete(note)
    }
    @WorkerThread
    suspend fun deleteAllNotes(){
        noteDAO.deleteAllNotes()
    }


}