package com.test.kick_off_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.test.kick_off_app.databinding.ActivityStadiumBinding
import com.test.kick_off_app.ui.main.stadium.StadiumDetailViewModel
import com.test.kick_off_app.ui.main.stadium.StadiumInfoAdapter

class StadiumActivity : AppCompatActivity() {
    lateinit var binding: ActivityStadiumBinding
    private lateinit var stadiumInfoAdapter: StadiumInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStadiumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentStadiumId = intent.getIntExtra("stadiumId", -1)

        val stadiumDetailViewModel = ViewModelProvider(this).get(StadiumDetailViewModel::class.java)

        val recyclerView = binding.rvStadiumInfo
        recyclerView.layoutManager = LinearLayoutManager(this)

        // rv adapter
        stadiumInfoAdapter = StadiumInfoAdapter { link ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
        recyclerView.adapter = stadiumInfoAdapter

        recyclerView.stopNestedScroll()

        // get response from api
        stadiumDetailViewModel.getStadiumDetail(currentStadiumId, 1)

        stadiumDetailViewModel.result.observe(this){stadiumDetail ->
            binding.textStadium.setText(stadiumDetail.stadium.name)
            binding.textAddress.setText(stadiumDetail.stadium.location)
            binding.textPhone.setText(stadiumDetail.stadium.contactPhone)
            binding.textPrice.setText(stadiumDetail.stadium.price)
            binding.textComment.setText(stadiumDetail.stadium.comment)

            Glide.with(binding.imageStadium)
                .load(stadiumDetail.stadium.image)
                .error(R.drawable.baseline_error_24)
                .into(binding.imageStadium)

            stadiumInfoAdapter.setList(stadiumDetail.stadiumInfoList)
            stadiumInfoAdapter.notifyDataSetChanged()
        }
    }
}