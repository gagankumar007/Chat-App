package com.example.chatapp.Activites

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.example.chatapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val mDbRef by lazy {
        FirebaseDatabase.getInstance().getReference()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializer()
    }

    private fun initializer() {
        supportActionBar?.hide()
        binding.btnSignUp.setOnClickListener(this)
        binding.txtLogin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.txtLogin -> {
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            }

            R.id.btnSignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {

        if (TextUtils.isEmpty(binding.edtUserEmail.text.toString())) {
            binding.edtUserEmail.setError("Email is Required")
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
        mAuth.createUserWithEmailAndPassword(
            binding.edtUserEmail.text.toString(),
            binding.edtPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("DATA--->", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        " ${task.exception}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun addUserToDatabase() {

        mDbRef.child("user").child(mAuth.currentUser!!.uid).setValue(
            User(
                binding.txtUserName.text.toString(),
                binding.edtUserEmail.text.toString(),
                binding.edtPassword.text.toString(),
                mAuth.currentUser!!.uid
            )
        )

    }
}