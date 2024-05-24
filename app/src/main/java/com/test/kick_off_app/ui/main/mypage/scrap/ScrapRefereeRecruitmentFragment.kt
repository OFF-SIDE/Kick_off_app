package com.test.kick_off_app.ui.main.mypage.scrap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.kick_off_app.R

class ScrapRefereeRecruitmentFragment : Fragment() {

    companion object {
        fun newInstance() = ScrapRefereeRecruitmentFragment()
    }

    private lateinit var viewModel: ScrapRefereeRecruitmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scrap_referee_recruitment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScrapRefereeRecruitmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}