package com.test.kick_off_app.ui.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.R
import com.test.kick_off_app.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {
    private var _binding: FragmentLocationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonClose.setOnClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_locationBarFragment)

        }

        val locationViewModel =
            ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)

        val recyclerView: RecyclerView = binding.rvLocation
        //recyclerView.layoutManager = GridLayoutManager(context, 7)

        // grid rv
        //var listManager = GridLayoutManager(this, 3)
        locationAdapter = LocationAdapter {position ->
            locationViewModel.toggleLocation(requireContext(), position)
        }
        recyclerView.adapter = locationAdapter

        locationAdapter.setLocations(locationViewModel.locations.value!!)
        locationViewModel.locations.observe(viewLifecycleOwner){locations ->
            locationAdapter.setLocations(locations)
            locationAdapter.notifyDataSetChanged()
            binding.textLocation.setText(locationViewModel.getLocationText())
        }
    }
}