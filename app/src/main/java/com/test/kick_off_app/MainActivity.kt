package com.test.kick_off_app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.kick_off_app.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mDetector: GestureDetectorCompat

    private var prevFocus: View? = null

    var permission_list = arrayOf<String>(
        Manifest.permission.INTERNET,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)

        // chat test
        binding.chatButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_stadium, R.id.navigation_referee, R.id.navigation_matching, R.id.navigation_community, R.id.navigation_mypage
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
         */
        navView.setupWithNavController(navController)


        checkPermission()


        val pref = getSharedPreferences("isFirst", MODE_PRIVATE)
        val first = pref.getBoolean("isFirst", false)
        if (first == false) {
            //Log.d("Is first Time?", "first")
            val editor = pref.edit()
            editor.putBoolean("isFirst", true)
            editor.commit()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            //Log.d("Is first Time?", "not first")
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Toast.makeText(this, "로그인 기록 없음", Toast.LENGTH_SHORT).show()
                } else if (tokenInfo != null) {
                    Toast.makeText(this, "자동 로그인" +
                            "\n회원번호: ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초", Toast.LENGTH_LONG).show()
                }
            }
        }

        mDetector = GestureDetectorCompat(this, SingleTapListener())
    }
    fun checkPermission() {
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        //안드로이드6.0 (마시멜로) 이후 버전부터 유저 권한설정 필요
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        for (permission in permission_list) {
            //권한 허용 여부를 확인한다.
            val chk = checkCallingOrSelfPermission(permission!!)
            if (chk == PackageManager.PERMISSION_DENIED) {
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list, 0)
            }
        }
    }

    // 터치 영역에 따라 키보드를 숨기기 위해 구현
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        // Activity에서 터치 이벤트가 발생할 때 현재 포커스를 가진 뷰를 저장
        if (ev.action == MotionEvent.ACTION_UP)
            prevFocus = currentFocus
        val result = super.dispatchTouchEvent(ev)
        // dispatchTouchEvent 호출 후 singleTapUp 제스처 탐지
        mDetector.onTouchEvent(ev)
        return result
    }

    private inner class SingleTapListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            // ACTION_UP 이벤트에서 포커스를 가진 뷰가 EditText일 때 터치 영역을 확인하여 키보드를 토글
            if (e.action == MotionEvent.ACTION_UP && prevFocus is EditText) {
                val prevFocus = prevFocus ?: return false
                // 포커를 가진 EditText의 터치 영역 계산
                val hitRect = Rect()
                prevFocus.getGlobalVisibleRect(hitRect)

                // 터치 이벤트가 EditText의 터치 영역에 속하지 않을 때 키보드를 숨길지 결정
                if (!hitRect.contains(e.x.toInt(), e.y.toInt())) {
                    if (currentFocus is EditText && currentFocus != prevFocus) {
                        // 터치한 영역의 뷰가 다른 EditText일 때는 키보드를 가리지 않는다.
                        return false
                    } else {
                        // 터치한 영역이 EditText의 터치 영역 밖이면서 다른 EditText가 아닐 때 키보드 hide
                        getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(prevFocus.windowToken, 0)
                        prevFocus.clearFocus()
                    }
                }
            }
            return super.onSingleTapUp(e)
        }
    }
    /*
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val currentFragment = binding.container.findNavController().currentDestination

        if(currentFragment!!.id == R.id.navigation_stadium && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE)){
            Log.d("fragment", "stadium")
            Toast.makeText(this, "fragment stadium", Toast.LENGTH_SHORT).show()

        }
        /*
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken, 0
            )
        }
         */
        return super.dispatchTouchEvent(ev)
    }
    */
}