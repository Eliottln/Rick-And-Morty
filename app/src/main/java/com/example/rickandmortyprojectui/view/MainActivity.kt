package com.example.rickandmortyprojectui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rickandmortyprojectui.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, CharactersListFragment.newInstance())
        transaction.commit()

    }
}