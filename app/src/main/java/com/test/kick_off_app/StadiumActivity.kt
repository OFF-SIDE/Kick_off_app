package com.test.kick_off_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.test.kick_off_app.databinding.ActivityStadiumBinding
import com.test.kick_off_app.functions.showToast
import com.test.kick_off_app.network.GlideApp
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

        val viewModel = ViewModelProvider(this).get(StadiumDetailViewModel::class.java)

        val recyclerView = binding.rvStadiumInfo
        recyclerView.layoutManager = LinearLayoutManager(this)

        if(currentStadiumId == -1){
            // 잘못된 stadiumId
            Log.e("stadiumId", "invalid")
            finish()
        }

        // rv adapter
        stadiumInfoAdapter = StadiumInfoAdapter { link ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
        recyclerView.adapter = stadiumInfoAdapter

        recyclerView.stopNestedScroll()

        // get response from api
        viewModel.getStadiumDetail(currentStadiumId)

        viewModel.result.observe(this){stadiumDetail ->
            binding.textStadium.setText(stadiumDetail.stadium.name)
            binding.textAddress.setText(stadiumDetail.stadium.location)
            binding.textPhone.setText(stadiumDetail.stadium.contactPhone)
            binding.textPrice.setText(stadiumDetail.stadium.price)
            binding.textComment.setText(stadiumDetail.stadium.comment)

            GlideApp.with(binding.imageStadium)
                .load(stadiumDetail.stadium.image)
                .error(R.drawable.baseline_error_24)
                .into(binding.imageStadium)

            stadiumInfoAdapter.setList(stadiumDetail.stadiumInfoList)
            stadiumInfoAdapter.notifyDataSetChanged()
        }

        binding.buttonScrap.setOnClickListener {
            // 즐겨찾기 버튼
        }

        viewModel.viewEvent.observe(this) {event ->
            event?.peekContent()?.let { actualEvent ->
                when(actualEvent) {
                    StadiumDetailViewModel.EVENT_STAR_STADIUM -> {
                        showToast("즐겨찾기로 등록하였습니다.")
                    }
                    StadiumDetailViewModel.EVENT_UNSTAR_STADIUM -> {
                        showToast("즐겨찾기 해제하였습니다.")
                    }
                }
            }
        }

        binding.buttonScrapOn.setOnClickListener {
            viewModel.result.value?.stadium?.id?.run{
                viewModel.starStadium(this)
            }
        }

        binding.buttonScrapOff.setOnClickListener {
            viewModel.result.value?.stadium?.id?.run{
                viewModel.unStarStadium(this)
            }
        }
    }
}