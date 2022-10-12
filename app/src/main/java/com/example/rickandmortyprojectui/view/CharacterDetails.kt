package com.example.rickandmortyprojectui.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.viewmodel.CharactersViewModel
import com.example.rickandmortyprojectui.viewmodel.DetailsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CharacterDetails: AppCompatActivity() {

    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var viewModel: DetailsViewModel
    private var id: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        id = intent.getIntExtra("id", 1)

        val input: EditText = findViewById(R.id.comment_pt)
        val sendButton: Button = findViewById(R.id.send_btn)
        sendButton.setOnClickListener {
            val text: String = input.text.toString()
            sendComment(text)
            input.text.clear()
        }

        viewModel.character.observe(this) {

        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.getCharacter(id)
    }

    private fun sendComment(comment: String) {

        val commentsRef: DatabaseReference = database.getReference("comments")
            .child("character")
            .child("$id")

        FirebaseAuth.getInstance().currentUser?.displayName?.let { commentsRef.push().child(it).setValue(comment) };
    }
}