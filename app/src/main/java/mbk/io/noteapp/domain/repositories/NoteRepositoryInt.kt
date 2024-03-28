package mbk.io.noteapp.domain.repositories

import androidx.lifecycle.LiveData
import mbk.io.noteapp.data.model.Note

interface NoteRepositoryInt {

    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    fun getAllNotes(): LiveData<List<Note>>
    fun searchNote(query: String?): LiveData<List<Note>>
}