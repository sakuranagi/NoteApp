package mbk.io.noteapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mbk.io.noteapp.data.model.Note
import mbk.io.noteapp.domain.usecases.GetNoteUseCase
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteUseCase: GetNoteUseCase) : ViewModel() {

    fun addNote(note: Note) =
        viewModelScope.launch {
            noteUseCase.insertNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteUseCase.deleteNote(note)
        }

    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteUseCase.updateNote(note)
        }

    fun getAllNotes(): LiveData<List<Note>> = noteUseCase.getAllNotes()

    fun searchNote(query: String?): LiveData<List<Note>> = noteUseCase.searchNote(query)
}