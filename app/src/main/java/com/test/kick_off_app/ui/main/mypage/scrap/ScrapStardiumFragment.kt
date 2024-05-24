package com.test.kick_off_app.ui.main.mypage.scrap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kick_off_app.R

class ScrapStardiumFragment : Fragment() {

    companion object {
        fun newInstance() = ScrapStardiumFragment()
    }

    private lateinit var viewModel: ScrapStardiumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scrap_stardium, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScrapStardiumViewModel::class.java)
        // TODO: Use the ViewModel
    }

}