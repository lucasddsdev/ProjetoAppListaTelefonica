package com.example.projetoapplistatelefonica.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projetoapplistatelefonica.R
import com.example.projetoapplistatelefonica.database.DBHelper
import com.example.projetoapplistatelefonica.databinding.ActivityLoginBinding
import com.example.projetoapplistatelefonica.databinding.ActivitySignupBinding
import com.example.projetoapplistatelefonica.model.UserModel

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        binding.buttonSignup.setOnClickListener() {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val passwordC = binding.editConfirmPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && passwordC.isNotEmpty()) {
                if (password == passwordC) {
                    val res = db.insertUser(username, password)
                    if(res > 0){
                        Toast.makeText(this, getString(R.string.signup_ok), Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, getString(R.string.signup_error), Toast.LENGTH_SHORT).show()
                        binding.editUsername.setText("")
                        binding.editPassword.setText("")
                        binding.editConfirmPassword.setText("")
                    }
                } else {
                    Toast.makeText(this, getString(R.string.passawords_dont_t_match), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.please_insert_all_required_fields), Toast.LENGTH_SHORT).show()
            }


        }

        binding.buttonBackToLogin.setOnClickListener(){
            finish()
        }
    }
}