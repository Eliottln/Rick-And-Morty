package com.example.rickandmortyprojectui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.model.Characters
import com.example.rickandmortyprojectui.model.Results
import com.squareup.picasso.Picasso

class CharactersListAdapter : RecyclerView.Adapter<CharactersListAdapter.ViewHolder>() {

    var charactersList: ArrayList<Results> = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_characters_list, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = charactersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(charactersList[position].image).into(holder.avatar)
        holder.name.text = charactersList[position].name
        holder.status.text = charactersList[position].status.plus(" - ").plus(charactersList[position].species)
        holder.location.text = charactersList[position].location?.name
        holder.origin.text = charactersList[position].origin?.name
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var avatar: ImageView
        var name: TextView
        var status: TextView
        var location: TextView
        var origin: TextView

        init {
            avatar = view.findViewById(R.id.avatar_iv)
            name = view.findViewById(R.id.name_tv)
            status = view.findViewById(R.id.status_tv)
            location = view.findViewById(R.id.location_tv)
            origin = view.findViewById(R.id.origin_tv)
        }
    }

    fun setCharacters(it: Characters) {
        this.charactersList.addAll(it.results)
        notifyDataSetChanged()
    }
}