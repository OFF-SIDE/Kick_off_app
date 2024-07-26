package com.test.kick_off_app.ui.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.user.UserApiClient
import com.test.kick_off_app.R
import com.test.kick_off_app.databinding.FragmentRegisterFirstBinding
import com.test.kick_off_app.functions.showToast

class RegisterFirstFragment : Fragment() {
    private var _binding: FragmentRegisterFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterFirstBinding.inflate(inflater, container, false)

        requireContext().showToast("현재 id : " + viewModel.signupInfo.value?.id
                                        +"\n현재 name : " + viewModel.signupInfo.value?.name
                                        +"\n현재 nickname : " + viewModel.signupInfo.value?.nickname
                                        +"\n현재 category : " + viewModel.signupInfo.value?.category
                                        +"\n현재 location : " + viewModel.signupInfo.value?.location)

        binding.textName.setText(viewModel.signupInfo.value?.name)
        binding.textNickName.setText(viewModel.signupInfo.value?.nickname)

        binding.nextButton.setOnClickListener {
            val name = binding.textName.text.toString()
            val nickname = binding.textNickName.text.toString()

            if(name.isNotEmpty() && nickname.isNotEmpty()){
                viewModel.updateName(name)
                viewModel.updateNickname(nickname)

                findNavController().navigate(R.id.action_registerFirstFragment_to_registerSecondFragment)
            }
            else{
                requireActivity().showToast("정보를 입력해주세요.")
            }
        }

        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                requireActivity().showToast("사용자 정보 요청 실패")

                // 액티비티 종료하기. 이후 뷰모델의 뷰이벤트로 구현
            }
            else if (user != null) {
                requireActivity().showToast("사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}" +
                        "\n이름: ${user.kakaoAccount?.name}")

                if(user.id == null){
                    // 액티비티 종료하기. 이후 뷰모델의 뷰이벤트로 구현
                }
                else{
                    viewModel.updateId(user.id!!)
                }
            }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}