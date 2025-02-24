package com.fadhiil2010.sekolah_api

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fadhiil2010.sekolah_api.API.APIClient
import com.fadhiil2010.sekolah_api.Model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class register : AppCompatActivity() {
    private lateinit var etUsername : EditText
    private lateinit var etPassword : EditText
    private lateinit var etEmail : EditText
    private lateinit var btnSignUp : Button
    private lateinit var tvLogin : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etEmail = findViewById(R.id.etEmail)
        btnSignUp = findViewById(R.id.btnSignUp)
        tvLogin = findViewById(R.id.tvLogin)

        btnSignUp.setOnClickListener {
            prosesRegister()
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, login::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun prosesRegister(){
        APIClient.apiService.register(
            etUsername.text.toString(),
            etPassword.text.toString(),
            etEmail.text.toString()
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful){
                    //jika berhasil menambahkan data user
                    //kondisi true succsess
                    if (response.body()!!.success){
                        //arahkan ke  loginActivity
                        startActivity(Intent(this@register, login::class.java))
                        Toast.makeText(
                            this@register,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    } //kondisi false => success

                    else {
                        Toast.makeText(
                            this@register,
                            response.body()!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
                //response berhasil
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                //response gagal
                Toast.makeText(this@register,t.message,
                    Toast.LENGTH_SHORT).show()

            }
        })
    }
}