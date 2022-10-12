package com.example.rickandmortyprojectui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val successAddComment = MutableLiveData<Boolean>()
    val successGetComments = MutableLiveData<Boolean>()
    val commentsArray = MutableLiveData<ArrayList<String>>()
    val usernamesArray = MutableLiveData<ArrayList<String>>()

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
                val list = task.value
                Log.d("TASKKK", "${list}")
//                arrayOf(list).forEach {
//                    it.last().key?.let { it1 -> usernamesArray.value?.add(it1) }
//                    commentsArray.value?.add(it.children.last().value.toString())
//                }
                Log.d("COMMENTLIST", "${usernamesArray.value}")
                successGetComments.value = true
            }
    }

    fun sendComment(comment: String, id: Int) {
        val commentsRef: DatabaseReference = database.getReference("comments")
            .child("character")
            .child("$id")
        FirebaseAuth.getInstance().currentUser?.displayName?.let { commentsRef.push().child(it).setValue(comment) };
    }

}