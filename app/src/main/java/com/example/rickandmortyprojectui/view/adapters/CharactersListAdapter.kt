package com.example.rickandmortyprojectui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.model.Characters
import com.example.rickandmortyprojectui.model.Results
import com.squareup.picasso.Picasso

class CharactersListAdapter (private var charactersList: ArrayList<Results>) : RecyclerView.Adapter<CharactersListAdapter.ViewHolder>() {

//    lateinit var charactersList: ArrayList<Results>

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_characters_list, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = charactersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(charactersList[position].image).into(holder.avatar)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var avatar: ImageView

        init {
            avatar = view.findViewById(R.id.avatar_iv)
        }
    }

    @JvmName("setCharacters1")
    fun setCharacters(it: Characters) {
        this.charactersList = it.results
        notifyDataSetChanged()
    }
}