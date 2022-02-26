package com.example.perludilindungi.api

import com.example.perludilindungi.model.FaskesResponse
import com.example.perludilindungi.model.ProvinceCityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("get-province") // the endpoint
    suspend fun getProvince(): Response<ProvinceCityResponse>

    @GET("get-city")
    suspend fun getCity(
        @Query("start_id") provinceName: String
    ): Response<ProvinceCityResponse>

    @GET("get-faskes-vaksinasi")
    suspend fun getFaskes(
        @Query("province") province: String,
        @Query("city") city: String
    ):Response<FaskesResponse>
}