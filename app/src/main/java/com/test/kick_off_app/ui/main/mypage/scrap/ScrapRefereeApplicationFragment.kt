package com.test.kick_off_app.ui.main.mypage.scrap

import android.content.Intent
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
import com.test.kick_off_app.databinding.FragmentScrapRefereeApplicationBinding
import com.test.kick_off_app.databinding.FragmentScrapStardiumBinding
import com.test.kick_off_app.functions.SharedPrefManager
import com.test.kick_off_app.ui.main.stadium.StadiumAdapter

class ScrapRefereeApplicationFragment : Fragment() {
    val manager: SharedPrefManager by lazy {
        SharedPrefManager.getInstance()
    }

    private var _binding: FragmentScrapRefereeApplicationBinding? = null
    private val binding get() = _binding!!

    // todo: 심판 추가 후 주석해제
    //private lateinit var refereeAdapter: RefereeAdapter
    private lateinit var viewModel: ScrapRefereeApplicationViewModel

    // todo: 만약에 카테고리로 필터링 된다면 prefListener 추가(구장 참고)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScrapRefereeApplicationBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ScrapRefereeApplicationViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvReferee
        // rv 구분선
        (recyclerView.layoutManager as? LinearLayoutManager)?.run {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), orientation)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }

        // todo: 심판 추가 후 주석해제
        /*// rv 어댑터
        refereeAdapter = RefereeAdapter(requireContext()) { refereeId ->
            val intent = Intent(requireContext(), RefereeActivity::class.java)
            intent.putExtra("refereeId", refereeId)
            startActivity(intent)
        }
        recyclerView.adapter = refereeAdapter

        // 즐겨찾기한 심판목록 가져오기
        viewModel.getStarredReferee(false)

        binding.swipe.setOnRefreshListener {
            viewModel.getStarredReferee(false)
            binding.swipe.isRefreshing = false
        }

        viewModel.referees.observe(viewLifecycleOwner){referees ->
            refereeAdapter.setList(referees)
            refereeAdapter.notifyDataSetChanged()
        }*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // pref 변경 리스너 해제
        //manager.unregisterPreferenceChangeListener()
        _binding = null
    }

}