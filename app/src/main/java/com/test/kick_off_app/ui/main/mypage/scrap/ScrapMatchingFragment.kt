package com.test.kick_off_app.ui.main.mypage.scrap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kick_off_app.R

class ScrapMatchingFragment : Fragment() {

    companion object {
        fun newInstance() = ScrapMatchingFragment()
    }

    private lateinit var viewModel: ScrapMatchingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scrap_matching, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScrapMatchingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}