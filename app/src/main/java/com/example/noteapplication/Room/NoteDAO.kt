/* ******************************************** *
* Data Access Object
* Part of Room DB: Entity, DAO, SQLlite(database)
* ********************************************** */

package com.example.noteapplication.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.noteapplication.Model.Note

@Dao
interface NoteDAO {

    // insert operation, needs to be individually executed: suspend (in kotlin)
    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    // to delete all notes from list
    @Query(/* value = */ "DELETE fROM `note-table`")
    suspend fun deleteAllNotes()

    @Query(/* value = */ "SELECT * FROM `note-table` ORDER BY id ASC")
    // to constantly update data in app, use Flow from kotlin coroutine
    fun getAllNotes(): kotlinx.coroutines.flow.Flow<List<Note>>
}