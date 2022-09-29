package com.example.rickandmortyprojectui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rickandmortyprojectui.R
import com.example.rickandmortyprojectui.RetrofitClient
import com.example.rickandmortyprojectui.model.Characters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitClient.getInstance().getCharacters(1).enqueue(object: Callback<Characters> {
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                val statusCode: Int = response.code();
                val character: Characters? = response.body();
                Log.d("BIDULE", "$character")
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                // Log error here since request failed
            }
        });



    }
}