package com.test.kick_off_app.ui.register

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.user.UserApiClient
import com.test.kick_off_app.R
import com.test.kick_off_app.databinding.FragmentRegisterFirstBinding
import com.test.kick_off_app.functions.showToast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

interface OnUserDataPassFirst {
    fun onUserDataPassFirst(bundle: Bundle)
}

class RegisterFirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentRegisterFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var id:Long? = null
    private var name:String? = ""
    private var nickname:String? = ""

    var dataPasser: OnUserDataPassFirst? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterFirstBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.nextButton.setOnClickListener {
            name = binding.textName.text.toString()
            nickname = binding.textNickName.text.toString()

            if(id != null && name != null && nickname != null){
                val curruntId = id
                val bundle = Bundle().apply {
                    putLong("id", curruntId!!)
                    putString("name", name)
                    putString("nickname", nickname)
                }

                dataPasser?.onUserDataPassFirst(bundle)

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

            }
            else if (user != null) {
                requireActivity().showToast("사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}" +
                        "\n이름: ${user.kakaoAccount?.name}")

                id = user.id
                nickname = user.kakaoAccount?.profile?.nickname
                binding.textNickName.setText(nickname)
            }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnUserDataPassFirst
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}