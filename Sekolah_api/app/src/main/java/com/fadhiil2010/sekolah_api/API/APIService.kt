package com.fadhiil2010.sekolah_api.API

import com.fadhiil2010.sekolah_api.Model.LoginResponse
import com.fadhiil2010.sekolah_api.Model.RegisterResponse
import com.fadhiil2010.sekolah_api.Model.SekolahResponse
import com.fadhiil2010.sekolah_api.Model.TambahResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface APIService {
    @FormUrlEncoded
    @POST("api_sekolah/register.php")
    fun register(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("email") email:String

    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api_sekolah/login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @GET("api_sekolah/get_sekolah.php")
    fun getListSekolah(@Query("nama_sekolah")nama: String): Call<SekolahResponse>

    @Multipart
    @POST("api_sekolah/add_sekolah.php")
    fun addBerita(
        @Part("nama_sekolah") nama_sekolah: RequestBody,
        @Part("notlp") notlp: RequestBody,
        @Part("informasi") informasi: RequestBody,
        @Part fileGambar: MultipartBody.Part
    ): Call<TambahResponse>
}