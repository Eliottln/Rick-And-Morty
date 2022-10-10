package com.example.rickandmortyprojectui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.model.Characters

class CharactersListAdapter : RecyclerView.Adapter<CharactersListAdapter.ViewHolder>() {

    lateinit var characters: Characters

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_characters_list, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = characters.results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

        }
    }

    @JvmName("setCharacters1")
    fun setCharacters(it: Characters) {
        this.characters = it
    }
}