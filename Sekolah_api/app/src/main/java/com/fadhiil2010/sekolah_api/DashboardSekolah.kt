package com.fadhiil2010.sekolah_api

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fadhiil2010.sekolah_api.API.APIClient
import com.fadhiil2010.sekolah_api.Adapter.SekolahAdapter
import com.fadhiil2010.sekolah_api.Model.SekolahResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardSekolah : AppCompatActivity() {

    private lateinit var svJudul: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var rvBerita: RecyclerView
    private lateinit var floatBtnTambah: FloatingActionButton
    private lateinit var sekolahAdapter: SekolahAdapter
    private lateinit var imgNotFound: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard_sekolah)

        svJudul = findViewById(R.id.svJudul)
        progressBar = findViewById(R.id.progressBar)
        rvBerita = findViewById(R.id.rvBerita)
        floatBtnTambah = findViewById(R.id.floatBtnTambah)
        imgNotFound = findViewById(R.id.imgNotFound)

        // Inisialisasi Adapter
        sekolahAdapter = SekolahAdapter(arrayListOf())
        rvBerita.adapter = sekolahAdapter
        rvBerita.layoutManager = LinearLayoutManager(this)

        // Panggil method getBerita
        getSekolah("")

        floatBtnTambah.setOnClickListener{
            startActivity(Intent(this@DashboardSekolah,TambahSekolah::class.java))
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun getSekolah(nama: String) {
        progressBar.visibility = View.VISIBLE

        APIClient.apiService.getListSekolah(nama).enqueue(object : Callback<SekolahResponse> {
            override fun onResponse(
                call: Call<SekolahResponse>,
                response: Response<SekolahResponse>
            ) {
                if (response.isSuccessful) {
                    val beritaResponse = response.body()
                    if (beritaResponse != null && beritaResponse.succes) {
                        // Jika data ditemukan
                        sekolahAdapter.setData(beritaResponse.data)
                        imgNotFound.visibility = View.GONE
                    } else {
                        // Jika data tidak ditemukan
                        sekolahAdapter.setData(emptyList())
                        imgNotFound.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(
                        this@DashboardSekolah,
                        "Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<SekolahResponse>, t: Throwable) {
                Toast.makeText(
                    this@DashboardSekolah,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
                progressBar.visibility = View.GONE
            }
        })
    }

}