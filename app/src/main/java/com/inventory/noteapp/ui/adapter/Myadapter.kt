package com.inventory.noteapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.inventory.noteapp.R
import com.inventory.noteapp.databinding.ItemViewBinding
import com.inventory.noteapp.model.Notes
import com.inventory.noteapp.ui.HomeFragmentDirections
import com.inventory.noteapp.viewModel

class Myadapter(
    val requireContext: Context,
    var allNotes: List<Notes>,
    val viewModel: viewModel,
    val navController: NavController
) :
    RecyclerView.Adapter<Myadapter.NotesviewHolder>() {

    fun updateList(noteList: List<Notes>) {

        allNotes = noteList
        notifyDataSetChanged()
    }

    class NotesviewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        var selectedNote: Notes? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesviewHolder {
        val binding: ItemViewBinding = ItemViewBinding.inflate(LayoutInflater.from(requireContext))
        return NotesviewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesviewHolder, position: Int) {
        bind(holder, position)

    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun bind(holder: NotesviewHolder, position: Int) {
        holder.binding.apply {
            val note = allNotes[position]
            textTitle.text = note.title
            textDescription.text = note.description
            textDate.text = note.date
            //setting Priority
            when (note.priority) {
                1 -> priorityColor.setBackgroundResource(R.drawable.red_dot)

                2 -> priorityColor.setBackgroundResource(R.drawable.yellow_dot)

                3 -> priorityColor.setBackgroundResource(R.drawable.green_dot)

            }

            holder.binding.root.setOnClickListener {
                selectedNote
                // viewModel.selectednote = note
                selectedNote = note
                Navigation.findNavController(it)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToEditNoteFragment())

            }
        }

    }




}

