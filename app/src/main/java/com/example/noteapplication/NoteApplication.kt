/* *****************************************
* Class created to generate
* instance of DAO & Repository class
* inheriting Application() class
* instead of creating constructor every time
* using lazy keyword: property delegation used to
* create instance only when application created,
* not always when it runs
* *********************************************/

package com.example.noteapplication

import android.app.Application
import com.example.noteapplication.Repository.NoteRepository
import com.example.noteapplication.Room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    //creating instance
    val database by lazy { NoteDatabase.getDatabase(/* takes context parameter */this, applicationScope) }
    val repository by lazy { NoteRepository(/* function in class */database.getNoteDAO()) }
}