package com.test.kick_off_app.ui.main.stadium

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test.kick_off_app.R
import com.test.kick_off_app.StadiumActivity
import com.test.kick_off_app.data.Stadium
import com.test.kick_off_app.databinding.FragmentStadiumBinding


class StadiumFragment : Fragment() {

    private var _binding: FragmentStadiumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var stadiumAdapter: StadiumAdapter
    private val selectedLocations = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStadiumBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*
        val textView: TextView = binding.textStadium
        stadiumViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
         */

        /*
        stadiumViewModel.getStadiumData()

        stadiumViewModel.result.observe(viewLifecycleOwner){notice->
            stadiumAdapter.setList(notice)
            stadiumAdapter.notifyDataSetChanged()
        }

        var swipe = v.findViewById<SwipeRefreshLayout>(com.test.bottomviewapplication.R.id.swipe)
        swipe.setOnRefreshListener {
            stadiumViewModel.getStadiumData()
            stadiumAdapter.setList(stadiumViewModel.result.value!!)
            stadiumAdapter.notifyDataSetChanged()
            swipe.isRefreshing = false
        }

         */

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stadiumViewModel =
            ViewModelProvider(this).get(StadiumViewModel::class.java)

        val recyclerView: RecyclerView = binding.rvStadium
        //view.findViewById(R.id.rv_stadium)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // rv adapter
        stadiumAdapter = StadiumAdapter { stadiumId ->
            val intent = Intent(requireActivity(), StadiumActivity::class.java)
            intent.putExtra("stadiumId", stadiumId)
            startActivity(intent)
        }
        recyclerView.adapter = stadiumAdapter

        // get response from api
        stadiumViewModel.getStadium("마포구", "축구장")

        stadiumViewModel.result.observe(viewLifecycleOwner){stadiums ->
            stadiumAdapter.setList(stadiums)
            stadiumAdapter.notifyDataSetChanged()
        }

        var swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipe.setOnRefreshListener {
            stadiumViewModel.getStadium("마포구", "축구장")
            if(stadiumViewModel.result.value==null){
                Log.e("null!", "1111")
            }else {
                stadiumAdapter.setList(stadiumViewModel.result.value!!)
                stadiumAdapter.notifyDataSetChanged()
            }
            swipe.isRefreshing = false
        }

        val locationBar = binding.constraintLocation
        locationBar.setOnClickListener {

        }
    }

    /*
    fun showLocationFragment(){
        val fragmentLocation = LocationFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragmentLocation)
            .addToBackStack(null)
            .commit()

    }
     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}