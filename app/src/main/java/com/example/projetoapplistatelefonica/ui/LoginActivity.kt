package com.example.projetoapplistatelefonica.ui

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projetoapplistatelefonica.R
import com.example.projetoapplistatelefonica.database.DBHelper
import com.example.projetoapplistatelefonica.databinding.ActivityLoginBinding
import com.example.projetoapplistatelefonica.model.ContactModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        sharedPreferences = application.getSharedPreferences("login", MODE_PRIVATE)
        val username = sharedPreferences.getString("username","")
        if (username != null) {
            if (username.isNotEmpty()){
                startActivity(Intent(this,MainActivity::class.java))
            }
        }

        binding.buttonLogin.setOnClickListener(){
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val logged = binding.checkboxLoged.isChecked

            if (username.isNotEmpty() && password.isNotEmpty()){
                if (db.login(username,password)){
                    if (logged){
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("username",username)
                        editor.apply()
                    }
                    startActivity(Intent(this,MainActivity::class.java))

                }else{
                    Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
                    binding.editUsername.setText("")
                    binding.editPassword.setText("")
                }
            }else{
                Toast.makeText(this, getString(R.string.please_insert_all_required_fields), Toast.LENGTH_SHORT).show()
            }

        }

        binding.textSignup.setOnClickListener(){
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }
}