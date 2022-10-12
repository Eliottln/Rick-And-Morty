package com.example.rickandmortyprojectui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.model.Characters
import com.example.rickandmortyprojectui.model.Results
import com.squareup.picasso.Picasso

class CharactersListAdapter (val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<CharactersListAdapter.ViewHolder>() {

    var charactersList: ArrayList<Results> = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_character, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = charactersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(charactersList[position], itemClickListener)
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

        fun bind(results: Results, clickListener: OnItemClickListener)
        {
            Picasso.get().load(results.image).into(avatar)
            name.text = results.name
            status.text = results.status.plus(" - ").plus(results.species)
            location.text = results.location?.name
            origin.text = results.origin?.name

            itemView.setOnClickListener {
                clickListener.onItemClicked(results)
            }
        }
    }

    fun setCharacters(it: Characters) {
        this.charactersList.addAll(it.results)
        notifyDataSetChanged()
    }
}

interface OnItemClickListener {
    fun onItemClicked(results: Results)
}