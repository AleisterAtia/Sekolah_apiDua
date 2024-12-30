package com.fadhiil2010.sekolah_api.Service

import com.haura.detail_produk.respon.ResponseBerita
import retrofit2.Call
import retrofit2.http.GET


interface CrudService {
    @GET("getSekolah.php")
    fun getAllSekolah(): Call<ResponseBerita>
}