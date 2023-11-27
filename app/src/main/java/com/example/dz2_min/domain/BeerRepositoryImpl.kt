package com.example.dz2_min.domain

import com.example.dz2_min.model.Beer
import com.example.dz2_min.network.BeerApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.Flow

class BeerRepositoryImpl {
    fun getAllBeers(): kotlinx.coroutines.flow.Flow<List<Beer>> =
        callbackFlow {
            trySendBlocking(
                BeerApi.api.getAllBeers()
            )
            awaitClose()
        }
}