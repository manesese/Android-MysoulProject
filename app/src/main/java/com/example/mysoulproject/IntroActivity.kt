package com.example.mysoulproject.View.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mysoulproject.JoinActivity
import com.example.mysoulproject.LoginActivity
import com.example.mysoulproject.MainHomeActivity
import com.example.mysoulproject.databinding.ActivityIntroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class IntroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityIntroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        buttonClickFunc()
    }

    //버튼 함수
    private fun buttonClickFunc() {
        binding.apply {
            loginBtn.setOnClickListener {
                val intent = Intent(this@IntroActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            joinBtn.setOnClickListener {
                val intent = Intent(this@IntroActivity, JoinActivity::class.java)
                startActivity(intent)

            }
            noLoginBtn.setOnClickListener {
                noLoginFunc()
            }
        }
    }
    //익명 로그인 함수
    private fun noLoginFunc() {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //익명 로그인에 성공했을 경우 메인홈 으로 이동
                    Toast.makeText(this@IntroActivity, "익명 로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@IntroActivity, MainHomeActivity::class.java)
                    startActivity(intent)
                } else {
                    //익명 로그인에 실패 했을 경우
                    Toast.makeText(this@IntroActivity, "익명 로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
