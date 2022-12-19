package com.example.mysoulproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.mysoulproject.View.View.IntroActivity
import com.example.mysoulproject.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        onClickFunc()
    }

    private fun onClickFunc() {
        binding.apply {
            joinEmailEt.addTextChangedListener {
                if (isEmailFormat(joinEmailEt.text.toString())) {
                    onChangeidCheck(View.VISIBLE, "사용할수 있는 이메일 입니다.", "#648CDD")
                } else {
                    onChangeidCheck(View.VISIBLE, "이메일 형식으로 입력해주십시오", "#FF0000")
                }
            }
            joinPwEt.addTextChangedListener {
                if (isEmailFormat(joinEmailEt.text.toString()) == true) {
                    if (isPasswordFormat(joinPwEt.text.toString())) {
                        onChangepwCheck(View.VISIBLE, "사용 할 수 있는 비밀번호 입니다.", "#648CDD")
                    } else {
                        onChangepwCheck(View.VISIBLE, "최소 8자 이상, 대문자 및 소문자 및 숫자와 특수문자를 하나 이상씩 포함하여 적어주세요.", "#FF0000")
                    }
                } else {
                    onChangepwCheck(View.VISIBLE, "이메일을 먼저 입력해주세요.", "#FF0000")
                }
            }

            joinPwCheckEt.addTextChangedListener {
                if (isPasswordFormat(joinPwEt.text.toString()) && joinPwEt.text.toString() == joinPwCheckEt.text.toString()) {
                    onChangepwCheckCorrect(View.VISIBLE, "비밀번호가 일치합니다.", "#648CDD")
                } else {
                    onChangepwCheckCorrect(View.VISIBLE, "비밀번호가 일차하지 않습니다.", "#FF0000")
                }
            }

            registerBtn.setOnClickListener {
                if (isEmailFormat(joinEmailEt.text.toString()) == true && isPasswordFormat(joinPwEt.text.toString()) == true && joinPwEt.text.toString() == joinPwCheckEt.text.toString()) {
                    FirbaseLoginFuc()
                } else {
                    Toast.makeText(this@JoinActivity, "입력부분을 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.e("asd", isEmailFormat(binding.joinEmailEt.text.toString()).toString())
        Log.e("asd", isEmailFormat(binding.joinEmailEt.text.toString()).toString())


    }

    //pwcheckTv 백그라운드 설정
    private fun onChangepwCheck(pwCheckVisible: Int, pwCheckSetText: String, pwCheckSetTC: String) {
        binding.apply {
            pwCheckTv.visibility = pwCheckVisible
            pwCheckTv.setText(pwCheckSetText)
            pwCheckTv.setTextColor(Color.parseColor(pwCheckSetTC))
        }
    }
    //idCheckTv 백그라운드 설정
    private fun onChangeidCheck(idCheckVisible: Int, idCheckSetText: String, idCheckSetTC: String) {
        binding.apply {
            idCheckTv.visibility = idCheckVisible
            idCheckTv.setText(idCheckSetText)
            idCheckTv.setTextColor(Color.parseColor(idCheckSetTC))
        }
    }

    //pwCheckCorrectTv 백그라운드 설정
    private fun onChangepwCheckCorrect(pwCheckCorrectVisible: Int, pwCheckCorrectSetText: String, pwCheckCorrectSetTC: String) {
        binding.apply {
            pwCheckCorrectTv.visibility = pwCheckCorrectVisible
            pwCheckCorrectTv.setText(pwCheckCorrectSetText)
            pwCheckCorrectTv.setTextColor(Color.parseColor(pwCheckCorrectSetTC))
        }
    }

    //회원가입 시 이메일 및 패스워드 중복성 검사 및 승인 및 거절
    private fun FirbaseLoginFuc() {
        auth.createUserWithEmailAndPassword(
            binding.joinEmailEt.text.toString(),
            binding.joinPwEt.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                //회원가입에 성공 할 경우
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@JoinActivity,
                        "회원가입에 성공하였습니다. 메인화면으로 이동합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@JoinActivity, IntroActivity::class.java)
                    startActivity(intent)
                    //중복된 이메일이 있는 경우
                } else {
                    try {
                        task.getResult()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        e.message?.let { Log.e("이미 있는 아이디", it) }
                        Toast.makeText(
                            this@JoinActivity,
                            "이미 있는 중복된 이메일 입니다. 새로운 이메일을 입력해 주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    //이메일 정규식 검사
    private fun isEmailFormat(emailCheck: String): Boolean {
        return emailCheck.matches(Patterns.EMAIL_ADDRESS.toRegex())
    }

    // 정규식 : 최소 8자 이상,대문자 하나,소문자 하나, 숫자 하나, 특수문자 하나 이상
    private fun isPasswordFormat(password: String): Boolean {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}".toRegex())
    }
}
