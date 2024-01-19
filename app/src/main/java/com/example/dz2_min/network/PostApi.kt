package com.example.dz2_min.network

import com.example.dz2_min.model.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface PostApi {
    @GET("photos")
    suspend fun getAllBeers(): List<Post>

    companion object RetrofitBuilder {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api: PostApi = getRetrofit().create()
    }
}