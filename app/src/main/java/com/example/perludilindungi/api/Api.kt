package com.example.perludilindungi.api

import com.example.perludilindungi.models.CheckInPost
import com.example.perludilindungi.models.CheckInResponse
import com.example.perludilindungi.models.FaskesResponse
import com.example.perludilindungi.models.NewsResponse
import com.example.perludilindungi.models.ProvinceCityResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @GET("api/get-province") // the endpoint
    suspend fun getProvince(): Response<ProvinceCityResponse>

    @GET("api/get-city")
    suspend fun getCity(
        @Query("start_id") provinceName: String
    ): Response<ProvinceCityResponse>

    @GET("api/get-faskes-vaksinasi")
    suspend fun getFaskes(
        @Query("province") province: String,
        @Query("city") city: String
    ):Response<FaskesResponse>

    @GET("get-news")
    suspend fun getNews(): Response<NewsResponse>

    @POST("check-in")
    suspend fun postQr(
        @Body post: CheckInPost
    ): Response<CheckInResponse>
}