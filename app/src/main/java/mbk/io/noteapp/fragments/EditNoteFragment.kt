package mbk.io.noteapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import mbk.io.noteapp.MainActivity
import mbk.io.noteapp.R
import mbk.io.noteapp.databinding.FragmentEditNoteBinding
import mbk.io.noteapp.model.Note
import mbk.io.noteapp.viewmodel.NoteViewModel
import androidx.core.view.MenuProvider
import androidx.navigation.findNavController


class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editBinding: FragmentEditNoteBinding? = null
    private val binding get() = editBinding!!

    private lateinit var notesViewModel: NoteViewModel
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

        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.tvTitle.setText(currentNote.title)
        binding.tvDesc.setText(currentNote.desc)

        binding.editNoteFab.setOnClickListener {
            val title = binding.tvTitle.text.toString().trim()
            val desk = binding.tvDesc.text.toString().trim()

            if (title.isNotEmpty()){
                val note = Note(currentNote.id, title, desk)
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