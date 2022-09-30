package com.example.rickandmortyprojectui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyprojectui.view.CharactersListViewHolder
import com.example.rickandmortyprojectui.R

class CharactersListAdapter(private val dataSet: Array<String>) :
    RecyclerView.Adapter<CharactersListViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CharactersListViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_characters_list, viewGroup, false)

        return CharactersListViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CharactersListViewHolder, position: Int) {

    }
}