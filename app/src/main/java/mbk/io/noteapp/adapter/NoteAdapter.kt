package mbk.io.noteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mbk.io.noteapp.databinding.NoteLayoutBinding
import mbk.io.noteapp.fragments.HomeFragmentDirections
import mbk.io.noteapp.model.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val binding: NoteLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
    val diff = AsyncListDiffer(this, diffCallback)


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

        holder.itemView.setOnClickListener{
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote))
        }

        holder.binding.executePendingBindings()
    }

}

/*class NoteViewHolder(val binding: NoteLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
           return oldItem == newItem
        }
    }
    val diff = AsyncListDiffer(this, diffCallback)


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

        holder.itemView.setOnClickListener{
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote))
        }

        holder.binding.executePendingBindings()
    }*/