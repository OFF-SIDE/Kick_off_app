package com.test.kick_off_app.ui.main.stadium

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kick_off_app.R
import com.test.kick_off_app.databinding.FragmentStadiumBinding
import com.test.kick_off_app.databinding.FragmentStadiumDetailBinding

class StadiumDetailFragment : Fragment() {
    private var _binding: FragmentStadiumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StadiumDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stadium_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StadiumDetailViewModel::class.java)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}