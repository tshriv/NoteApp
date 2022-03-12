package com.inventory.noteapp.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.inventory.noteapp.R
import com.inventory.noteapp.databinding.FragmentHomeBinding
import com.inventory.noteapp.model.Notes
import com.inventory.noteapp.ui.adapter.Myadapter
import com.inventory.noteapp.viewModel
import com.inventory.noteapp.viewModelFactory


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: Myadapter
    lateinit var mainList: List<Notes>

    val viewModel :viewModel by viewModels {viewModelFactory((activity?.application as NotesApplication).database.dao())  }
    //val viewModel: viewModel by activityViewModels { viewModelFactory((activity?.application as NotesApplication).database.dao()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        viewModel.getallNotes().observe(viewLifecycleOwner, { allnotes ->
            mainList = allnotes
            adapter = Myadapter(requireContext(), allnotes, viewModel, findNavController())
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("currentDestination",findNavController().currentDestination.toString())
        binding.addnoteButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAddNoteFragment()
            )
        }

        binding.filterHigh.setOnClickListener {
            getFilteredNotes(1)
        }
        binding.filterMedium.setOnClickListener {
            getFilteredNotes(2)
        }
        binding.filterLow.setOnClickListener {
            getFilteredNotes(3)
        }
        binding.filterAll.setOnClickListener {
            getAllNotes()
        }

    }

    fun getFilteredNotes(priority: Int) {
        viewModel.getFilteredNotes(priority).observe(viewLifecycleOwner, { noteslist ->
            mainList = noteslist
            adapter.updateList(noteslist)
        })
    }

    fun getAllNotes() {
        viewModel.getallNotes().observe(viewLifecycleOwner, { allnotes ->
            mainList = allnotes
            adapter.updateList(allnotes)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.app_bar_search)
        val mySearchView = item.actionView as SearchView

        mySearchView.queryHint = "Search Notes Here"
        mySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchedList: MutableList<Notes> = mutableListOf<Notes>()
                for (note in mainList) {
                    if (note.title.contains(newText.toString())) {
                        searchedList.add(note)
                    }
                }
                adapter.updateList(searchedList)
                return true
            }
        })
    }
}