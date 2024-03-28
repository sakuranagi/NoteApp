package mbk.io.noteapp.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mbk.io.noteapp.R
import mbk.io.noteapp.databinding.NoteLayoutBinding
import mbk.io.noteapp.presentation.fragments.HomeFragmentDirections
import mbk.io.noteapp.data.model.Note

class NoteAdapter(private val onClick: (note: Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = diff.currentList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = diff.currentList[position]

        holder.binding.tvTitle.text = currentNote.title
        holder.binding.tvDesc.text = currentNote.desc
        holder.binding.tvCategory.text = currentNote.category
        if (currentNote.fav == true) {
            holder.binding.fav.visibility = View.VISIBLE
        } else if (currentNote.fav == false) {
            holder.binding.fav.visibility = View.INVISIBLE
            holder.binding.notFav.visibility = View.VISIBLE
        }

        holder.binding.notFav.setOnClickListener {
            onClick(currentNote)
        }

        holder.itemView.setOnClickListener {
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote))
        }

        holder.binding.executePendingBindings()
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
    val diff = AsyncListDiffer(this, diffCallback)

}
