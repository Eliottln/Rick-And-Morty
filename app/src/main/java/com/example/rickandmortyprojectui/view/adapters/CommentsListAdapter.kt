package com.example.rickandmortyprojectui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.model.Comment

class CommentsListAdapter(var commentsList: ArrayList<Comment>): RecyclerView.Adapter<CommentsListAdapter.ViewHolder>() {

//    var commentsList: ArrayList<String> = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_comment, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = commentsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var username: TextView
        var commentContent: TextView

        init {
            username = view.findViewById(R.id.username_comment_tv)
            commentContent = view.findViewById(R.id.comment_content_tv)
        }

        fun bind(content: Comment)
        {
            username.text = content.username
            commentContent.text = content.content
        }
    }

//    fun setCommmentsList()
    fun addComment(comment: Comment){
        commentsList.add(comment)
        notifyItemInserted(itemCount-1)
        notifyItemRangeChanged(itemCount-1, itemCount)
    }
}