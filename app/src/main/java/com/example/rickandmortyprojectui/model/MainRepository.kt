package com.example.rickandmortyprojectui.model

class MainRepository constructor(private val retrofitService: RetrofitService) {

   suspend fun getCharacters(page: Int) = retrofitService.getCharacters(page)

   suspend fun getCharacter(id: Int) = retrofitService.getCharacter(id)

}