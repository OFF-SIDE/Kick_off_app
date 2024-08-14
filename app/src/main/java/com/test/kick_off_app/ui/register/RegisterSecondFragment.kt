package com.test.kick_off_app.ui.register

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.R
import com.test.kick_off_app.databinding.FragmentRegisterFirstBinding
import com.test.kick_off_app.databinding.FragmentRegisterSecondBinding
import com.test.kick_off_app.functions.showToast
import com.test.kick_off_app.ui.location.LocationAdapter


class RegisterSecondFragment : Fragment() {
    private var _binding: FragmentRegisterSecondBinding? = null

    private val binding get() = _binding!!

    private lateinit var callback: OnBackPressedCallback

    private lateinit var locationAdapter: LocationAdapter

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterSecondBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.prevButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerSecondFragment_to_registerFirstFragment)
        }

        binding.nextButton.setOnClickListener {
            viewModel.signupInfo.value?.location?.let {
                if(it.isNotEmpty()){
                    findNavController().navigate(R.id.action_registerSecondFragment_to_registerThirdFragment)
                }
            }
        }

        binding.buttonReset.setOnClickListener {
            viewModel.resetLocation()
        }

        // grid rv
        locationAdapter = LocationAdapter { position ->
            //TODO : onClick
            // 뷰모델 함수 : 클릭 시 뷰모델 내의 라이브 데이터인 locationList<boolean> 변경
            viewModel.updateLocation(position)
            Log.d("location positon", position.toString())
        }
        val recyclerView: RecyclerView = binding.rvLocation
        recyclerView.adapter = locationAdapter

        viewModel.locations.value?.let {
            locationAdapter.setLocations(it)
        }
        viewModel.locations.observe(viewLifecycleOwner){locations->
            locationAdapter.setLocations(locations)
            locationAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_registerSecondFragment_to_registerFirstFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}