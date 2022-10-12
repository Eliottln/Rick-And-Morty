package com.example.rickandmortyprojectui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyprojectui.model.Characters
import com.example.rickandmortyprojectui.model.MainRepository
import com.example.rickandmortyprojectui.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val character = MutableLiveData<Results>()

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

}