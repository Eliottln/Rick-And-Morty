package com.example.rickandmortyprojectui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortyprojectui.model.Characters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel: ViewModel() {

    private var charactersListLiveData= MutableLiveData<ArrayList<Characters>>()
    fun getCustomersRepository(): LiveData<ArrayList<Characters>>? {
        return charactersListLiveData
    }
    init {
        charactersListLiveData= Repository.getCharacters()!!
    }

}

class Repository {

    companion object {
        fun getCharacters(): MutableLiveData<ArrayList<Characters>>? {

            val charactersListLiveData: MutableLiveData<ArrayList<Characters>> = MutableLiveData<ArrayList<Characters>>()

            CoroutineScope(Dispatchers.Default).launch {

                launch(Dispatchers.IO) {
                    val apiInterface = ApiInterfaceBuilder.getApiInterface()
                    val response = apiInterface?.getCustomerList("")
                    withContext(Dispatchers.Default)
                    {
                        response?.let {
                            if (response.isSuccessful()) {
                                charactersListLiveData.postValue(response.body()!!.data)
                            }

                        }
                    }
                }

            }
            return charactersListLiveData
        }

    }
}