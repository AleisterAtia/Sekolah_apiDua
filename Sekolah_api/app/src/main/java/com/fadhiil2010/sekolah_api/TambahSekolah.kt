package com.fadhiil2010.sekolah_api

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fadhiil2010.sekolah_api.API.APIClient
import com.fadhiil2010.sekolah_api.Model.TambahResponse
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TambahSekolah : AppCompatActivity() {

    private lateinit var etJudul: EditText
    private lateinit var etIsi: EditText
    private lateinit var etNotlp: EditText
    private lateinit var btnGambar: Button
    private lateinit var btnTambah: Button
    private lateinit var imgGambar: ImageView
    private var imageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_sekolah)

        btnGambar = findViewById(R.id.btnGambar)
        imgGambar = findViewById(R.id.imgGambar)
        etIsi = findViewById(R.id.etIsiSekolah)
        btnTambah = findViewById(R.id.btnTambah)
        etJudul = findViewById(R.id.etJudul)
        etNotlp = findViewById(R.id.etNotlp)

        btnGambar.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        btnTambah.setOnClickListener {
            imageFile?.let {
                    file ->
                tambahSekolah(etJudul.text.toString(), etIsi.text.toString(),etNotlp.text.toString(), file)
            }
        }
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    //menampilkan gambar
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data!!
            imageFile = File(uri.path!!)
            imgGambar.visibility = View.VISIBLE
            imgGambar.setImageURI(uri)

        }
    }


    private fun tambahSekolah(nama_sekolah: String, notlp: String, informasi: String, fileGambar: File) {
        val requestBody = fileGambar.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val partFileGambar =
            MultipartBody.Part.createFormData("fileGambar", fileGambar.name, requestBody)
        val title = nama_sekolah.toRequestBody("text/plain".toMediaTypeOrNull())
        val notlp = notlp.toRequestBody("text/plain".toMediaTypeOrNull())
        val informasi = informasi.toRequestBody("text/plain".toMediaTypeOrNull())

        APIClient.apiService.addBerita(title, notlp, informasi, partFileGambar)
            .enqueue(object : Callback<TambahResponse> {
                override fun onResponse(
                    call: Call<TambahResponse>,
                    response: Response<TambahResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.succes) {
                            startActivity(
                                Intent(
                                    this@TambahSekolah,
                                    DashboardSekolah::class.java
                                )
                            )

                        } else {
                            Toast.makeText(
                                this@TambahSekolah,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } else {
                        Toast.makeText(
                            this@TambahSekolah,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TambahResponse>, t: Throwable) {
                    Toast.makeText(
                        this@TambahSekolah,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}