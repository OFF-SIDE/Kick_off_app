package com.test.kick_off_app.ui.main.mypage.scrap

import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.R
import com.test.kick_off_app.StadiumActivity
import com.test.kick_off_app.databinding.FragmentScrapStardiumBinding
import com.test.kick_off_app.databinding.FragmentStadiumBinding
import com.test.kick_off_app.functions.SharedPrefManager
import com.test.kick_off_app.ui.main.stadium.StadiumAdapter

class ScrapStardiumFragment : Fragment() {
    val manager: SharedPrefManager by lazy {
        SharedPrefManager.getInstance()
    }


    private var _binding: FragmentScrapStardiumBinding? = null
    private val binding get() = _binding!!

    private lateinit var stadiumAdapter: StadiumAdapter
    private lateinit var viewModel: ScrapStardiumViewModel

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        when (key) {
            "current_category" -> {
                val newCategory = prefs.getString(key, null)
                // 카테고리 변경시 api 재요청
                viewModel.getStarredStadium()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScrapStardiumBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ScrapStardiumViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvStadium
        // rv 구분선
        (recyclerView.layoutManager as? LinearLayoutManager)?.run {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), orientation)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }

        // rv 어댑터
        stadiumAdapter = StadiumAdapter(requireContext()) { stadiumId ->
            val intent = Intent(requireContext(), StadiumActivity::class.java)
            intent.putExtra("stadiumId", stadiumId)
            startActivity(intent)
        }
        recyclerView.adapter = stadiumAdapter

        // 즐겨찾기한 구장 가져오기
        viewModel.getStarredStadium()

        binding.swipe.setOnRefreshListener {
            viewModel.getStarredStadium()
            binding.swipe.isRefreshing = false
        }

        viewModel.stadiums.observe(viewLifecycleOwner){stadiums ->
            stadiumAdapter.setList(stadiums)
            stadiumAdapter.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // pref 변경 리스너 해제
        manager.unregisterPreferenceChangeListener()
        _binding = null
    }

}