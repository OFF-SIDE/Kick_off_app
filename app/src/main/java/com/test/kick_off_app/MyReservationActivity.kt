package com.test.kick_off_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.test.kick_off_app.databinding.ActivityMyReservationBinding
import com.test.kick_off_app.ui.main.mypage.scrap.ScrapMatchingFragment
import com.test.kick_off_app.ui.main.mypage.scrap.ScrapRefereeApplicationFragment
import com.test.kick_off_app.ui.main.mypage.scrap.ScrapRefereeRecruitmentFragment
import com.test.kick_off_app.ui.main.mypage.scrap.ScrapStardiumFragment

class MyReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyReservationBinding
    val tabTextList = arrayListOf("협의 중", "예약 완료", "예약 기록") // 메뉴에 들어갈 이름
    private val NUM_PAGES = 3 // 페이지 수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPagerMyReservation.adapter = ScreenSlidePagerAdapter(this)
        binding.viewPagerMyReservation.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tabLayoutMyReservation, binding.viewPagerMyReservation) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES // 페이지 수 리턴

        override fun createFragment(position: Int): Fragment {
            return when(position){ // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
                0 -> ScrapStardiumFragment()
                1 -> ScrapRefereeRecruitmentFragment()
                else -> ScrapMatchingFragment()
            }
        }
    }
}