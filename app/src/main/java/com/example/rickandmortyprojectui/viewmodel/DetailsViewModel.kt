package com.example.rickandmortyprojectui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyprojectui.model.Comment
import com.example.rickandmortyprojectui.model.MainRepository
import com.example.rickandmortyprojectui.model.Results
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val errorMessage = MutableLiveData<String>()
    val character = MutableLiveData<Results>()
    val newComment = MutableLiveData<Comment>()
    val commentsArray = MutableLiveData<ArrayList<Comment>>()

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            val response = mainRepository.getCharacter(id)
            withContext(Dispatchers.Main) {
                try {
                    Log.d("RESPONSE", "$response")
                    character.value= response
                } catch (e: Exception) {
                    onError(e.toString())
                    Log.d("RESPONSE", "$response")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    fun getComments(id: Int) {
        database.getReference("comments")
            .child("character")
            .child("$id")
            .get()
            .addOnSuccessListener { task ->
                commentsArray.postValue(task.children.map { it.getValue(Comment::class.java) } as ArrayList<Comment>?)
            }
    }


    fun sendComment(comment: String, id: Int) {
        val commentsRef: DatabaseReference = database.getReference("comments")
            .child("character")
            .child("$id")
            .push()
        val username = FirebaseAuth.getInstance().currentUser?.displayName
        commentsRef.child("username").setValue(username)
        commentsRef.child("content").setValue(comment)

        newComment.value = Comment(username,comment)
    }

}