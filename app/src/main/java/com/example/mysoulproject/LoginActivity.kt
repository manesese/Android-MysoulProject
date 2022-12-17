package com.example.mysoulproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mysoulproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.apply {
            loginBtn.setOnClickListener {
                FirbaseLoginFunc()
            }
        }
    }

    //로그인 함수
    private fun FirbaseLoginFunc() {
        val email = binding.emailEt.text.toString()
        val password = binding.pwEt.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //로그인에 성공 할 경우
                    Toast.makeText(this@LoginActivity, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    //로그인에 실패 할 경우
                    Toast.makeText(this@LoginActivity, "아이디, 비밀번호를 확인해 주십시오", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

}