package com.example.rickandmortyprojectui.view


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
import com.google.firebase.database.FirebaseDatabase

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

        val commentsRV: RecyclerView = findViewById(R.id.comments_rv)
        val layoutManager = LinearLayoutManager(this)
        commentsRV.layoutManager = layoutManager
        val input: EditText = findViewById(R.id.comment_pt)
        val sendButton: Button = findViewById(R.id.send_btn)

        sendButton.setOnClickListener {
            val text: String = input.text.toString()
            viewModel.sendComment(text, id)
            input.text.clear()
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.character.observe(this) {

        }

        viewModel.successGetComments.observe(this) {
            if (it) {
                val adapter = viewModel.commentsArray.value?.let {
                        it1 -> CommentsListAdapter(it1)
                }
                commentsRV.adapter = adapter
                adapter?.notifyDataSetChanged()
            }
        }

        viewModel.getCharacter(id)
        viewModel.getComments(id)
    }

}