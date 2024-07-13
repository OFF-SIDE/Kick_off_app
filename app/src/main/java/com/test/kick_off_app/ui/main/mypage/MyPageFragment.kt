package com.test.kick_off_app.ui.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.user.UserApiClient
import com.test.kick_off_app.LoginActivity
import com.test.kick_off_app.ScrapActivity
import com.test.kick_off_app.databinding.FragmentMypageBinding
import com.test.kick_off_app.functions.showToast

class MyPageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mypageViewModel =
            ViewModelProvider(this).get(MyPageViewModel::class.java)

        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*
        val textView: TextView = binding.textMypage
        mypageViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

         */
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myPageFragment =
            ViewModelProvider(this).get(MyPageViewModel::class.java)

        binding.textCategoryGuide.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.imageProfile.setOnClickListener {
            // 연결 끊기
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    requireActivity().showToast("연결 끊기 실패")
                }
                else {
                    requireActivity().showToast("연결 끊기 성공. 토큰삭제")
                }
            }
        }

        binding.constraintLayoutInfo.setOnClickListener {
            // 로그아웃
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    requireActivity().showToast("로그아웃 실패")
                }
                else {
                    requireActivity().showToast("로그아웃 성공. 토큰삭제")
                }
            }
        }


        // edit 버튼
        binding.buttonEditMypage.setOnClickListener {
            it.isSelected = !it.isSelected
            if(it.isSelected){
                binding.textCategory.visibility = View.GONE
                binding.textCategoryEdit.setText(binding.textCategory.text.toString())
                binding.textCategoryEdit.visibility = View.VISIBLE

                binding.textLocation.visibility = View.GONE
                binding.textLocationEdit.setText(binding.textLocation.text.toString())
                binding.textLocationEdit.visibility = View.VISIBLE
            }
            else{
                binding.textCategoryEdit.visibility = View.GONE
                binding.textCategory.text = binding.textCategoryEdit.text.toString()
                binding.textCategory.visibility = View.VISIBLE

                binding.textLocationEdit.visibility = View.GONE
                binding.textLocation.text = binding.textLocationEdit.text.toString()
                binding.textLocation.visibility = View.VISIBLE
            }

        }

        binding.constraintLayoutScrap.setOnClickListener {
            val intent = Intent(requireActivity(), ScrapActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}