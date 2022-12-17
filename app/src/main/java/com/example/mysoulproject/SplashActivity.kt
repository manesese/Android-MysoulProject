package com.example.mysoulproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.mysoulproject.View.View.IntroActivity
import com.example.mysoulproject.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var auth: FirebaseAuth

    // 뒤로 가기 눌렀을 때 프라그먼트 전달 인터페이스
    interface OnBackPressedListener {
        fun onBackPressed()
    }

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments
        for (fragment in fragmentList) {
            if (fragment is OnBackPressedListener) {
                (fragment as OnBackPressedListener).onBackPressed()
                return
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        alreadyLoginFunc()


    }
    // 스플래시 화면 이후 인트로로 넘어가는 함수
    private fun SplashHandlerToIntroActivity() {
        Handler().postDelayed( {
            startActivity(Intent(this, IntroActivity:: class.java))
            finish()
        }, 3000)
    }
    // 스플래시 화면 이후 메인화면으로 넘어가는 함수
    private fun SplashHandlerToMainHomeActivity() {
        Handler().postDelayed( {
            startActivity(Intent(this, MainHomeActivity:: class.java))
            finish()
        }, 3000)
    }

    private fun alreadyLoginFunc() {
        if(auth.currentUser?.uid == null) {
            Log.e("SplashActivity", "현재 로그인 되어있지 않음")
            SplashHandlerToIntroActivity()
        }else {
            Log.e("SplashActivity", "현재 로그인 되어있음")
            SplashHandlerToMainHomeActivity()
        }
    }
}