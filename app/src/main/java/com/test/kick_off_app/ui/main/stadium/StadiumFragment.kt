package com.test.kick_off_app.ui.main.stadium

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test.kick_off_app.R
import com.test.kick_off_app.StadiumActivity
import com.test.kick_off_app.databinding.FragmentStadiumBinding
import com.test.kick_off_app.functions.SharedPrefManager
import com.test.kick_off_app.ui.location.LocationViewModel


class StadiumFragment : Fragment() {
    private var _binding: FragmentStadiumBinding? = null

    /*private var location: String? = null
    private var category: String? = null*/

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var stadiumAdapter: StadiumAdapter
    private lateinit var stadiumViewModel: StadiumViewModel
    private lateinit var locationViewModel: LocationViewModel

    val manager: SharedPrefManager by lazy {
        SharedPrefManager.getInstance()
    }

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        when (key) {
            "current_category" -> {
                val newCategory = prefs.getString(key, null)
                // 카테고리 변경시 api 재요청
                stadiumViewModel.getStadium(stadiumViewModel.selectedLocations.value, newCategory)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStadiumBinding.inflate(inflater, container, false)
        val root: View = binding.root

        stadiumViewModel = ViewModelProvider(this).get(StadiumViewModel::class.java)
        locationViewModel = ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)

        // 저장된 값 복원
        /*savedInstanceState?.let {
            location = it.getString("LOCATION")
            category = it.getString("CATEGORY")
        }
        if(location.isNullOrEmpty()){
            location = "마포구"
        }
        if(category.isNullOrEmpty()){
            category = "축구장"
        }*/

        // pref 변경 리스너 등록
        manager.registerPreferenceChangeListener(preferenceChangeListener)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvStadium
        recyclerView.layoutManager = LinearLayoutManager(context)

        manager.setCategory("축구장")

        // rv 구분선
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        // rv 어댑터
        stadiumAdapter = StadiumAdapter(requireContext()) { stadiumId ->
            val intent = Intent(requireContext(), StadiumActivity::class.java)
            intent.putExtra("stadiumId", stadiumId)
            startActivity(intent)
        }
        recyclerView.adapter = stadiumAdapter

        // location fragment에서 선택된 지역 받아오기
        // 만약 선택된게 없다면 null
        stadiumViewModel.setLocations(locationViewModel.getLocations())

        // 구장 목록 api 요청(지역 여러개 가능)
        stadiumViewModel.let {
            it.getStadium(it.selectedLocations.value, manager.getCategory() ?: "축구")
        }

        locationViewModel.locations.observe(viewLifecycleOwner){
            stadiumViewModel.setLocations(locationViewModel.getLocations())
        }

        // 선택된 지역 관찰해서 재요청
        stadiumViewModel.selectedLocations.observe(viewLifecycleOwner){locations ->
            stadiumViewModel.getStadium(locations, manager.getCategory())
        }

        stadiumViewModel.stadiums.observe(viewLifecycleOwner){stadiums ->
            stadiumAdapter.setList(stadiums)
            stadiumAdapter.notifyDataSetChanged()
        }

        binding.swipe.setOnRefreshListener {
            stadiumViewModel.getStadium(stadiumViewModel.selectedLocations.value, manager.getCategory())
            binding.swipe.isRefreshing = false
        }

        binding.outsideArea.setOnClickListener {
            if( binding.containerLocation.findNavController().currentDestination!!.id == R.id.locationFragment){
                binding.containerLocation.findNavController().navigate(R.id.action_locationFragment_to_locationBarFragment)
                Log.e("location", "11")
            }
            else if( binding.containerLocation.findNavController().currentDestination!!.id == R.id.locationBarFragment){
                Log.e("locationBar", "22")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putString("LOCATION", location)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // pref 변경 리스너 해제
        manager.unregisterPreferenceChangeListener()
        _binding = null
    }
}