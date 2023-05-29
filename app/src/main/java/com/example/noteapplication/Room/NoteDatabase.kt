/* ********************************
* Database Class
* Must be abstract class
* Inherited from RoomDatabase class()
* ********************************** */

package com.example.noteapplication.Room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.noteapplication.Model.Note
import com.example.noteapplication.NoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// seperate more than one table with ","
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDAO(): NoteDAO

    // create only single object of database class, always (Singleton)
    companion object{

        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getDatabase(context: NoteApplication, scope: CoroutineScope) : NoteDatabase{

            // using elvis operator (if-else statement)
            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(context.applicationContext,
                NoteDatabase::class.java, "note_database")
                    // calling inside database fn after room.db builder function
                        // specify new param (scope) in application class
                    .addCallback(NoteDatabaseCallback(scope))
                    .build()

                INSTANCE = instance

                // preventing more than one thread of DB,
                // using synchronized keyword
                instance
            }
        }
    }

    // create new inner class to assign initial values
    // in table instead of keeping it blank
    private class NoteDatabaseCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // if instance is not null, then add data
            INSTANCE?.let { database ->

                // room doesn't allow directly
                // database.getNoteDAO().insert(Note(("T", "D")))
                // hence, add coroutine scope
                scope.launch {

                    val noteDAO = database.getNoteDAO()

                    // inserting data
                    noteDAO.insert(Note("Title 1", "Description 1"))
                    noteDAO.insert(Note("Title 2", "Description 2"))
                    noteDAO.insert(Note("Title 3", "Description 3"))
                    noteDAO.insert(Note("Title 4", "Description 4"))
                }

            }
        }
    }
}