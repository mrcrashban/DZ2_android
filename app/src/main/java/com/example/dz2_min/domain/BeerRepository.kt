package com.example.dz2_min.domain

import com.example.dz2_min.model.Beer
import java.util.concurrent.Flow

interface BeerRepository {
    fun getAllBeers(): kotlinx.coroutines.flow.Flow<List<Beer>>
}