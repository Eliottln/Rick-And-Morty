package com.example.rickandmortyprojectui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.model.MainRepository
import com.example.rickandmortyprojectui.model.RetrofitService
import com.example.rickandmortyprojectui.view.adapters.CharactersListAdapter
import com.example.rickandmortyprojectui.viewmodel.CharactersViewModel
import com.example.rickandmortyprojectui.viewmodel.MyViewModelFactory


class CharactersListActivity : AppCompatActivity() {
    private lateinit var viewModel: CharactersViewModel
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_list)

        val charactersRV: RecyclerView = findViewById(R.id.characters_rv)

        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[CharactersViewModel::class.java]
        val adapter = CharactersListAdapter()
        val layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        )[CharactersViewModel::class.java]

        viewModel.charactersList.observe(this) {
            if (charactersRV.adapter == null) {
                charactersRV.layoutManager = layoutManager
                charactersRV.adapter = adapter
            }
            adapter.setCharacters(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        charactersRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView!!, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    viewModel.getCharacters(page++)
                }

            }
        })
        viewModel.getCharacters(page++)
    }
}