package com.test.kick_off_app.ui.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.test.kick_off_app.MainActivity
import com.test.kick_off_app.R
import com.test.kick_off_app.databinding.FragmentRegisterFourthBinding
import com.test.kick_off_app.functions.showToast
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


interface OnRegisterButtonClickListener {
    fun onRegisterButtonClick()
}

class RegisterFourthFragment : Fragment() {
    private var _binding: FragmentRegisterFourthBinding? = null

    private val binding get() = _binding!!

    private lateinit var callback: OnBackPressedCallback

    private lateinit var mNextButtonClickListener: OnRegisterButtonClickListener

    private lateinit var viewModel: RegisterViewModel

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]

        // 이미지 선택 완료시
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    viewModel.updateProfileImage(uri)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterFourthBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.prevButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFourthFragment_to_registerThirdFragment)
        }

        // 다음 버튼(회원가입 완료)
        binding.nextButton.setOnClickListener {
            // 이미지 업로드
            uploadImage()
        }

        // 이미지 불러오기 버튼
        binding.buttonLoadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
        }

        viewModel.profileImage.value?.let {uri ->
            if(binding.imageProfile.visibility != View.VISIBLE) {
                binding.imageProfile.visibility = View.VISIBLE
            }
            Glide.with(this)
                .load(uri)
                .into(binding.imageProfile)
        }

        viewModel.profileImage.observe(viewLifecycleOwner){uri ->
            if(binding.imageProfile.visibility != View.VISIBLE) {
                binding.imageProfile.visibility = View.VISIBLE
            }
            Glide.with(this)
                .load(uri)
                .into(binding.imageProfile)
        }

        // 이미지 업로드 서버 응답 이벤트
        viewModel.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.peekContent()?.let { actualEvent ->
                when(actualEvent) {
                    RegisterViewModel.EVENT_IMAGE_UPLOAD_SUCCESS -> {
                        // 이미지 업로드 성공 시
                        mNextButtonClickListener.onRegisterButtonClick()
                    }
                    RegisterViewModel.EVENT_IMAGE_UPLOAD_FAIL -> {
                        // 이미지 업로드 실패 시
                        requireActivity().showToast("이미지 업로드 실패")
                    }
                }
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
        if(context is OnRegisterButtonClickListener){
            mNextButtonClickListener = context
        }
        else{
            throw RuntimeException("$context must implement OnNextButtonClickListener")
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private fun uploadImage() {
        if(viewModel.profileImage.value == null){
            requireActivity().showToast("사진을 선택하세요.")
        }
        viewModel.profileImage.value?.let { uri ->
            // 안되면 getURI 옛날 걸로
            val path = getRealPathFromURI(uri)
            if(path.isEmpty()){
                return
            }
            val file = File(path)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            viewModel.uploadImage(body)
        }
    }

    // URI에서 실제 파일 경로를 얻는 함수
    private fun getRealPathFromURI(uri: Uri): String {

        var path = ""
        val cursor = context?.contentResolver?.query(uri, null, null, null, null)
        /*
        if (cursor != null) {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            path = cursor.getString(index)
            cursor.close()
        }
         */
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                if (index != -1) {
                    path = it.getString(index)
                }
            }
        }
        return path
    }

}