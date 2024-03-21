package com.test.kick_off_app.ui.main.stadium

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test.kick_off_app.R
import com.test.kick_off_app.StadiumActivity
import com.test.kick_off_app.databinding.FragmentStadiumBinding
import com.test.kick_off_app.ui.location.LocationViewModel


class StadiumFragment : Fragment() {
    private var _binding: FragmentStadiumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var stadiumAdapter: StadiumAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStadiumBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stadiumViewModel =
            ViewModelProvider(this).get(StadiumViewModel::class.java)

        val locationViewModel =
            ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)

        val recyclerView: RecyclerView = binding.rvStadium
        recyclerView.layoutManager = LinearLayoutManager(context)

        // divider
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        // rv adapter
        stadiumAdapter = StadiumAdapter { stadiumId ->
            val intent = Intent(requireContext(), StadiumActivity::class.java)
            intent.putExtra("stadiumId", stadiumId)
            startActivity(intent)
        }
        recyclerView.adapter = stadiumAdapter

        // get response from api
        stadiumViewModel.getStadium("마포구", "축구장")

        locationViewModel.locations.observe(viewLifecycleOwner){locations ->
            if(locationViewModel.selectCount.value == 1){
                stadiumViewModel.getStadium(locationViewModel.firstLocation(), "축구장")
            }
            else if(locationViewModel.selectCount.value == 2){
                stadiumViewModel.getStadium2(locationViewModel.firstLocation(), locationViewModel.secondLocation(), "축구장")
            }
        }

        stadiumViewModel.result.observe(viewLifecycleOwner){stadiums ->
            stadiumAdapter.setList(stadiums)
            stadiumAdapter.notifyDataSetChanged()
        }

        var swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipe.setOnRefreshListener {
            if(locationViewModel.selectCount.value == 1){
                stadiumViewModel.getStadium(locationViewModel.firstLocation(), "축구장")
            }
            else if(locationViewModel.selectCount.value == 2){
                stadiumViewModel.getStadium2(locationViewModel.firstLocation(), locationViewModel.secondLocation(), "축구장")
            }
            if(stadiumViewModel.result.value==null){
                Log.e("e: null", "stadium = null")
            }else {
                stadiumAdapter.setList(stadiumViewModel.result.value!!)
                stadiumAdapter.notifyDataSetChanged()
            }
            swipe.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}