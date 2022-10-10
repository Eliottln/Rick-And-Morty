package com.example.rickandmortyprojectui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.model.RetrofitService
import com.example.rickandmortyprojectui.model.MainRepository
import com.example.rickandmortyprojectui.view.adapters.CharactersListAdapter
import com.example.rickandmortyprojectui.viewmodel.CharactersViewModel
import com.example.rickandmortyprojectui.viewmodel.MyViewModelFactory

class CharactersListFragment : Fragment() {
    private lateinit var viewModel: CharactersViewModel
    private var adapter: CharactersListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_characters_list, container, false)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[CharactersViewModel::class.java]

        adapter = viewModel.getCharacters()?.results?.let { CharactersListAdapter(it) }

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        )[CharactersViewModel::class.java]

        viewModel.charactersList.observe(viewLifecycleOwner) {
            adapter?.setCharacters(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
        }

        val charactersRV: RecyclerView = binding.findViewById(R.id.characters_rv)
        charactersRV.adapter = adapter
//        charactersRV.layoutManager = LinearLayoutManager(activity)

        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter?.notifyDataSetChanged()

    }

    companion object {
        fun newInstance() =
            CharactersListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}