package com.test.kick_off_app.ui.main.stadium

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.R
import com.test.kick_off_app.StadiumActivity
import com.test.kick_off_app.data.StadiumInfo
import com.test.kick_off_app.databinding.FragmentStadiumBinding
import com.test.kick_off_app.ui.location.LocationFragment


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

        stadiumAdapter = StadiumAdapter { stadiumId ->
            val intent = Intent(requireActivity(), StadiumActivity::class.java)
            intent.putExtra("stadiumId", stadiumId)
            startActivity(intent)
        }
        recyclerView.adapter = stadiumAdapter

        stadiumAdapter.setList(tempStadium())
        stadiumAdapter.notifyDataSetChanged()

        val button = binding.locationBtn
        button.setOnClickListener {
            //showLocationFragment()
            binding.locationBtn.setOnClickListener {
                val navController = Navigation.findNavController(view)
                navController.navigate(
                    R.id.action_navigation_stadium_to_navigation_location
                )
            }
        }
    }

    fun tempStadium(): MutableList<StadiumInfo>{
        val temp =  mutableListOf<StadiumInfo>()
        temp.add(
            StadiumInfo(1,  "마포축구장", "마포구 어딘가", 1000, 2, "https://picsum.photos/200")
        )
        temp.add(
            StadiumInfo(2,  "강남축구장", "강남구 어딘가", 2000,5, "https://picsum.photos/200")
        )
        return temp
    }

    /*
    private fun selectLocationDialog() {
        val locations = arrayOf("마포구", "강남구", "종로구", /* ... */)

        val recyclerView = RecyclerView(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = LocationAdapter {

        }
        recyclerView.adapter = adapter

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("지역구 선택")
            .setView(recyclerView)
            .setPositiveButton("확인") { _, _ ->
                updateButtonText(adapter.getSelectedlocations())
            }
            .setNegativeButton("취소", null)
            .create()

        dialog.show()
    }

    private fun updateButtonText() {
        val button = binding.locationBtn
        if (selectedLocations.size > 0) {
            val buttonText = "📍 " + selectedLocations.joinToString("/")
            button.text = buttonText
        } else {
            button.text = "지역구 선택"
        }
    }

     */
    fun showLocationFragment(){
        val fragmentLocation = LocationFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragmentLocation)
            .addToBackStack(null)
            .commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}