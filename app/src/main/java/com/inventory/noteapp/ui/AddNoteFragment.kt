package com.inventory.noteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.inventory.noteapp.R
import com.inventory.noteapp.databinding.FragmentAddNoteBinding
import com.inventory.noteapp.model.Notes
import com.inventory.noteapp.viewModel
import com.inventory.noteapp.viewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : Fragment() {


    val viewModel: viewModel by activityViewModels {
        viewModelFactory(
            (activity?.application as
                    NotesApplication).database.dao()
        )
    }
    lateinit var binding: FragmentAddNoteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //setting Priority
        var priority: Int = 1

        binding.priorityRed.setOnClickListener {
            priority = 1
            binding.priorityRed.setImageResource(R.drawable.ic_done)
            binding.priorityYellow.setImageResource(0)
            binding.priorityGreen.setImageResource(0)
        }

        binding.priorityYellow.setOnClickListener {
            priority = 2
            binding.priorityYellow.setImageResource(R.drawable.ic_done)
            binding.priorityRed.setImageResource(0)
            binding.priorityGreen.setImageResource(0)
        }

        binding.priorityGreen.setOnClickListener {
            priority = 3
            binding.priorityGreen.setImageResource(R.drawable.ic_done)
            binding.priorityRed.setImageResource(0)
            binding.priorityYellow.setImageResource(0)
        }






        binding.buttonDone.setOnClickListener {

            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDesc.text.toString()
            val note = binding.editTextNote.text.toString()

            //Getting Date
            val c = Calendar.getInstance().time
            val dateFormater = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val date = dateFormater.format(c)

            val myNote = Notes(0, title, desc, note, priority, date)
            viewModel.viewModelScope.launch {
                viewModel.insert(myNote)
            }
            Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment())
        }
    }

}