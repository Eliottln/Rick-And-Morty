package com.example.rickandmortyprojectui

import com.example.rickandmortyprojectui.model.Characters
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val  BASE_URL = "https://rickandmortyapi.com/api/"

interface CharactersInterface
{
    @GET("character")
    fun getCharacters(@Query("page") page: Int): Call<Characters>
}

//object CharactersSingleton {
//    val newsInstance:CharactersInterface
//    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        newsInstance=retrofit.create(CharactersInterface::class.java)
//    }
//}

class RetrofitClient {
    companion object {
        private var instance : CharactersInterface? = null

        @Synchronized
        fun getInstance(): CharactersInterface {
            if (instance == null)
                instance = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                    .create(CharactersInterface::class.java)
            return instance as CharactersInterface
        }
    }
}