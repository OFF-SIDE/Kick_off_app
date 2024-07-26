package com.test.kick_off_app.ui.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.test.kick_off_app.R
import com.test.kick_off_app.databinding.FragmentRegisterFirstBinding
import com.test.kick_off_app.databinding.FragmentRegisterSecondBinding


class RegisterSecondFragment : Fragment() {
    private var _binding: FragmentRegisterSecondBinding? = null

    private val binding get() = _binding!!

    private lateinit var callback: OnBackPressedCallback

    private var location:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            //location = binding.textLocation.text.toString()

            if(location != null){
                val bundle = Bundle().apply{
                    putString("location", location)
                }


                findNavController().navigate(R.id.action_registerSecondFragment_to_registerThirdFragment)
            }

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