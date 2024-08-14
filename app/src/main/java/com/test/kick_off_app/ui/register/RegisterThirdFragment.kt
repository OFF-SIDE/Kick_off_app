package com.test.kick_off_app.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.MainActivity
import com.test.kick_off_app.R
import com.test.kick_off_app.RegisterActivity
import com.test.kick_off_app.databinding.FragmentRegisterThirdBinding
import com.test.kick_off_app.ui.category.CategoryAdapter

class RegisterThirdFragment : Fragment() {
    private var _binding: FragmentRegisterThirdBinding? = null

    private val binding get() = _binding!!

    private lateinit var callback: OnBackPressedCallback

    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterThirdBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.prevButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerThirdFragment_to_registerSecondFragment)
        }

        binding.nextButton.setOnClickListener {
            viewModel.signupInfo.value?.category?.let{
                if(it.isNotEmpty()) {
                    findNavController().navigate(R.id.action_registerThirdFragment_to_registerFourthFragment)
                }
            }
        }

        binding.buttonReset.setOnClickListener {
            viewModel.resetCategory()
        }

        // grid rv
        categoryAdapter = CategoryAdapter {position ->
            viewModel.updateCategory(position)
            Log.d("category position", position.toString())
        }
        val recyclerView: RecyclerView = binding.rvCategory
        recyclerView.adapter = categoryAdapter

        viewModel.categories.value?.let{
            categoryAdapter.setLocations(it)
        }
        viewModel.categories.observe(viewLifecycleOwner){category->
            categoryAdapter.setLocations(category)
            categoryAdapter.notifyDataSetChanged()
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
                findNavController().navigate(R.id.action_registerThirdFragment_to_registerSecondFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}