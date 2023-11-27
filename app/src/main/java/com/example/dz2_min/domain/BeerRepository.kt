package com.example.dz2_min.domain

import com.example.dz2_min.model.Beer
import kotlinx.coroutines.flow.Flow
interface BeerRepository {
    fun getAllBeers(): Flow<List<Beer>>
}