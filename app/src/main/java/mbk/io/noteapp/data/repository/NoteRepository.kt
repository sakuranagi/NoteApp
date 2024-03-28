package mbk.io.noteapp.data.repository

import androidx.lifecycle.LiveData
import mbk.io.noteapp.data.database.NoteDatabase
import mbk.io.noteapp.data.model.Note
import mbk.io.noteapp.domain.repositories.NoteRepositoryInt

class NoteRepository(private val db: NoteDatabase): NoteRepositoryInt {

    override suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    override suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    override suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
    override fun getAllNotes(): LiveData<List<Note>> = db.getNoteDao().getAllNotesSorted()
    override fun searchNote(query: String?): LiveData<List<Note>> = db.getNoteDao().searchNote(query)


}