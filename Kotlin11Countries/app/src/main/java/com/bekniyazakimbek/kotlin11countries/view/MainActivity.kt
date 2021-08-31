package com.bekniyazakimbek.kotlin11countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bekniyazakimbek.kotlin11countries.R
import com.bekniyazakimbek.kotlin11countries.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}