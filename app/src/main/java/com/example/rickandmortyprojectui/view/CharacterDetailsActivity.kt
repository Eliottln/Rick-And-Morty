package com.example.rickandmortyprojectui.view


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.model.MainRepository
import com.example.rickandmortyprojectui.model.RetrofitService
import com.example.rickandmortyprojectui.view.adapters.CommentsListAdapter
import com.example.rickandmortyprojectui.viewmodel.DetailsViewModel
import com.example.rickandmortyprojectui.viewmodel.MyViewModelFactory
import com.squareup.picasso.Picasso

class CharacterDetailsActivity: AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel
    private var id: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[DetailsViewModel::class.java]

        id = intent.getIntExtra("id", 1)

        val avatar: ImageView = findViewById(R.id.avatar_details_iv)
        val name: TextView = findViewById(R.id.name_details_tv)
        val commentsRV: RecyclerView = findViewById(R.id.comments_rv)
        val layoutManager = LinearLayoutManager(this)
        val input: EditText = findViewById(R.id.comment_pt)
        val sendButton: Button = findViewById(R.id.send_btn)
        commentsRV.layoutManager = layoutManager
        val adapter = CommentsListAdapter()

        sendButton.setOnClickListener {
            val text: String = input.text.toString()
            viewModel.sendComment(text, id)
            input.text.clear()
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.character.observe(this) {
            Picasso.get().load(it.image).into(avatar)
            name.text = it.name
        }

        viewModel.newComment.observe(this) {
            adapter.addComment(it)
        }

        viewModel.commentsArray.observe(this) {
            if (commentsRV.adapter == null) {
                commentsRV.adapter = adapter
                adapter.setCommentList(it)
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.getCharacter(id)
        viewModel.getComments(id)
    }

}