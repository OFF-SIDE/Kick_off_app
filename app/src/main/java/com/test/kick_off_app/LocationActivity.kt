package com.test.kick_off_app

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.databinding.ActivityLocationBinding
import com.test.kick_off_app.ui.location.LocationAdapter

class LocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationBinding
    private lateinit var locationAdapter: LocationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 지역 선택창

        val recyclerView: RecyclerView = binding.rvLocation
        recyclerView.layoutManager = LinearLayoutManager(this)

        locationAdapter = LocationAdapter {  }
    }
}