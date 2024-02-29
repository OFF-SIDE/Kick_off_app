package com.test.kick_off_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.kick_off_app.databinding.ActivityStadiumBinding


class StadiumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStadiumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStadiumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textStadium.setText("StadiumActivity")
    }
}