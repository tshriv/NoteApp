package com.inventory.noteapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.inventory.noteapp.R
import com.inventory.noteapp.databinding.FragmentEditNoteBinding
import com.inventory.noteapp.model.Notes
import com.inventory.noteapp.ui.adapter.Myadapter
import com.inventory.noteapp.viewModel
import com.inventory.noteapp.viewModelFactory
import kotlinx.coroutines.launch

class EditNoteFragment : Fragment() {


    val viewModel: viewModel by activityViewModels {
        viewModelFactory(
            (activity?.application as NotesApplication)
                .database.dao()
        )
    }


    lateinit var binding: FragmentEditNoteBinding

    // val selectedNote = viewModel.selectednote
    val selectedNote = Myadapter.selectedNote!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentEditNoteBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextTitle.setText(selectedNote.title)
        binding.editTextDesc.setText(selectedNote.description)
        binding.editTextNote.setText(selectedNote.notes)
        when (selectedNote.priority) {
            1 -> binding.redDot.setImageResource(R.drawable.ic_done)

            2 -> binding.yellowDot.setImageResource(R.drawable.ic_done)

            3 -> binding.greenDot.setImageResource(R.drawable.ic_done)

        }

        var priority: Int = selectedNote.priority
        binding.redDot.setOnClickListener {
            priority = 1
            binding.redDot.setImageResource(R.drawable.ic_done)
            binding.yellowDot.setImageResource(0)
            binding.greenDot.setImageResource(0)
        }

        binding.yellowDot.setOnClickListener {
            priority = 2
            binding.yellowDot.setImageResource(R.drawable.ic_done)
            binding.redDot.setImageResource(0)
            binding.greenDot.setImageResource(0)
        }

        binding.greenDot.setOnClickListener {
            priority = 3
            binding.greenDot.setImageResource(R.drawable.ic_done)
            binding.redDot.setImageResource(0)
            binding.yellowDot.setImageResource(0)
        }

        binding.buttonDone.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDesc.text.toString()
            val noteText = binding.editTextNote.text.toString()
            val note = Notes(selectedNote.id, title, desc, noteText, priority, selectedNote.date)


            viewModel.viewModelScope.launch {
                viewModel.update(note)
            }
            Toast.makeText(requireContext(), "Note Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToHomeFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.delete_option_menu) {

            val builder: AlertDialog.Builder = AlertDialog.Builder(this.requireContext())

            builder.setTitle("Delete Note").setMessage("Do you want to delete this note")
            val alertDialog = builder.create()
            builder.setPositiveButton("Yes") { _, _ ->
                viewModel.viewModelScope.launch {
                    viewModel.delete(selectedNote)
                    alertDialog.dismiss()
                    Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToHomeFragment())
                }
            }
                .setNegativeButton("No") { _, _ -> alertDialog.dismiss() }


            /*   val bottomSheet = BottomSheetDialog(requireContext(),R.style.mybottom_dialog)
               bottomSheet.setContentView(R.layout.delete_dialog)
               bottomSheet.show()
               val deleteYes = bottomSheet.findViewById<TextView>(R.id.yes)
               val deleteNo = bottomSheet.findViewById<TextView>(R.id.no)

               deleteYes?.setOnClickListener {
                   viewModel.viewModelScope.launch {
                       viewModel.delete(
                           selectedNote
                       )

                       Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
                       findNavController().navigate(EditNoteFragmentDirections.actionEditNoteFragmentToHomeFragment())
                       bottomSheet.dismiss()
                   }
               }

               deleteNo?.setOnClickListener { bottomSheet.dismiss() }
   */
            //   DeleteDialogBinding.inflate(layoutInflater)

        }
        return super.onOptionsItemSelected(item)
    }
}