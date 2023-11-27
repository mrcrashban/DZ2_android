package com.example.dz2_min.network

import com.example.dz2_min.model.Beer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface BeerApi {
    @GET("beers")
    suspend fun getAllBeers(): List<Beer>

    companion object RetrofitBuilder {
        private const val BASE_URL = "https://api.punkapi.com/v2/"

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api: BeerApi = getRetrofit().create()
    }
}