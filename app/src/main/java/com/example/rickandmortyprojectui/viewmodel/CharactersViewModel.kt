package com.example.rickandmortyprojectui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyprojectui.model.Characters
import com.example.rickandmortyprojectui.model.MainRepository
import kotlinx.coroutines.*

class CharactersViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val charactersList = MutableLiveData<Characters>()
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getCharacters(page: Int) {
        viewModelScope.launch {
            val response = mainRepository.getCharacters(page)
            withContext(Dispatchers.Main) {
                try {
                    Log.d("RESPONSE", "$response")
                    charactersList.value= response
                } catch (e: Exception) {
                    Log.d("RESPONSE", "$response")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
    }

}