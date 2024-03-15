package com.test.kick_off_app.ui.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.test.kick_off_app.R
import com.test.kick_off_app.data.LocationEnum
import com.test.kick_off_app.data.getLocation
import com.test.kick_off_app.databinding.FragmentLocationBarBinding
import com.test.kick_off_app.functions.safeNavigate

class LocationBarFragment : Fragment() {
    private var _binding: FragmentLocationBarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationViewModel =
            ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)

        binding.locationBar.setOnClickListener {
            findNavController().navigate(R.id.action_locationBarFragment_to_locationFragment)
        }

        binding.textLocation.setText(locationViewModel.getLocationText())
        locationViewModel.locations.observe(viewLifecycleOwner){locations ->
            binding.textLocation.setText(locationViewModel.getLocationText())
        }

    }
}