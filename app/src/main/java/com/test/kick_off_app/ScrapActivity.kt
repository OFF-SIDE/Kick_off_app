package com.test.kick_off_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.test.kick_off_app.databinding.ActivityScrapBinding
import com.test.kick_off_app.ui.main.mypage.scrap.ScrapMatchingFragment
import com.test.kick_off_app.ui.main.mypage.scrap.ScrapRefereeApplicationFragment
import com.test.kick_off_app.ui.main.mypage.scrap.ScrapRefereeRecruitmentFragment
import com.test.kick_off_app.ui.main.mypage.scrap.ScrapStardiumFragment

class ScrapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScrapBinding
    val tabTextList = arrayListOf("구장", "심판 구인", "심판 지원", "매칭") // 메뉴에 들어갈 이름
    private val NUM_PAGES = 4 // 페이지 수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPagerScrap.adapter = ScreenSlidePagerAdapter(this)
        binding.viewPagerScrap.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tabLayoutScrap, binding.viewPagerScrap) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES // 페이지 수 리턴

        override fun createFragment(position: Int): Fragment {
            return when(position){ // 페이지 포지션에 따라 그에 맞는 프래그먼트를 보여줌
                0 -> ScrapStardiumFragment()
                1 -> ScrapRefereeRecruitmentFragment()
                2 -> ScrapRefereeApplicationFragment()
                else -> ScrapMatchingFragment()
            }
        }
    }
}