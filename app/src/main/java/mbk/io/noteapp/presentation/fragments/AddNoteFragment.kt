package mbk.io.noteapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mbk.io.noteapp.presentation.MainActivity
import mbk.io.noteapp.R
import mbk.io.noteapp.databinding.FragmentAddNoteBinding
import mbk.io.noteapp.data.model.Note
import mbk.io.noteapp.presentation.viewmodel.NoteViewModel

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var addNoteBinding: FragmentAddNoteBinding? = null
    private val binding get() = addNoteBinding!!

    private val notesViewModel: NoteViewModel by viewModels()
    private lateinit var addNoteView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        addNoteView = view
    }

    private fun saveNote(view: View) {
        val title = binding.tvTitle.text.toString().trim()
        val desc = binding.tvDesc.text.toString().trim()

            if (title.isNotEmpty())
            {
                val note = Note(0, title, desc,binding.sCategory.selectedItem.toString())
                notesViewModel.addNote(note)

                Toast.makeText(addNoteView.context, "Note saved!", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else
            {
                Toast.makeText(
                    addNoteView.context,
                    "Please enter the note title",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menu.clear()
            menuInflater.inflate(R.menu.menu_add_note, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.saveMenu -> {
                    saveNote(addNoteView)
                    true
                }

                else -> false
            }
        }

        override fun onDestroy() {
            super.onDestroy()

            addNoteBinding = null
        }
    }