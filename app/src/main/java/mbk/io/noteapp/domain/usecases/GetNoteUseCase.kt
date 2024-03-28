package mbk.io.noteapp.domain.usecases

import mbk.io.noteapp.data.model.Note
import mbk.io.noteapp.domain.repositories.NoteRepositoryInt
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(private val noteRepositoryInt: NoteRepositoryInt) {
    suspend fun insertNote(note: Note) = noteRepositoryInt.insertNote(note)
    suspend fun deleteNote(note: Note) = noteRepositoryInt.deleteNote(note)
    suspend fun updateNote(note: Note) = noteRepositoryInt.updateNote(note)
    fun getAllNotes() = noteRepositoryInt.getAllNotes()
    fun searchNote(query: String?) = noteRepositoryInt.searchNote(query)
}