package mbk.io.noteapp.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import mbk.io.noteapp.R
import mbk.io.noteapp.data.model.Note
import mbk.io.noteapp.databinding.FragmentEditNoteBinding
import mbk.io.noteapp.presentation.MainActivity
import mbk.io.noteapp.presentation.viewmodel.NoteViewModel


@AndroidEntryPoint
class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editBinding: FragmentEditNoteBinding? = null
    private val binding get() = editBinding!!

    private val notesViewModel: NoteViewModel by viewModels()
    private lateinit var currentNote: Note

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        currentNote = args.note!!

        binding.tvTitle.setText(currentNote.title)
        binding.tvDesc.setText(currentNote.desc)

        binding.editNoteFab.setOnClickListener {
            val title = binding.tvTitle.text.toString().trim()
            val desk = binding.tvDesc.text.toString().trim()

            if (title.isNotEmpty()){
                val note = Note(currentNote.id, title, desk, binding.sCategory.selectedItem.toString())
                notesViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            }else{
                Toast.makeText(context, "Please enter the title!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Yes"){_,_->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context, "Note successfully deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("No", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu ->{
                deleteNote()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editBinding = null
    }

}