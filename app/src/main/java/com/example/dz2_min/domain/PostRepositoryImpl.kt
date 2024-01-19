package com.example.dz2_min.domain

import com.example.dz2_min.model.Post
import com.example.dz2_min.network.PostApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

class PostRepositoryImpl {
    fun getAllBeers(): kotlinx.coroutines.flow.Flow<List<Post>> =
        callbackFlow {
            trySendBlocking(
                PostApi.api.getAllBeers()
            )
            awaitClose()
        }
}