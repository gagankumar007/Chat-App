package com.example.chatapp.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }

    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializer()
    }

    private fun initializer() {
        supportActionBar?.hide()
        binding.loginbtn.setOnClickListener(this)
        binding.txtRegister.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.txtRegister -> {
                startActivity(Intent(this, SignUpActivity::class.java))

            }

            R.id.loginbtn -> {
                login()


            }

        }
    }

    private fun login() {

        if (TextUtils.isEmpty(binding.edtUserName.text.toString())) {
            binding.edtUserName.setError("Email is Required")
            return
        }
        if (TextUtils.isEmpty(binding.edtPassword.text.toString())) {
            binding.edtPassword.setError("password is Required")
            return
        }
        if (binding.edtPassword.text.toString().length < 6) {
            binding.edtPassword.setError("password must be 6 or more Characters long")
            return
        }
        mAuth.signInWithEmailAndPassword(
            binding.edtUserName.text.toString(),
            binding.edtPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()


                } else {
                    Log.w("DATA--->", "logInUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

    }
}