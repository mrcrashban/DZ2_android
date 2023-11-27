package com.example.dz2_min.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dz2_min.domain.BeerRepository
import com.example.dz2_min.domain.BeerRepositoryImpl
import com.example.dz2_min.model.Beer
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BeerViewModel (private val beerRepository: BeerRepositoryImpl = BeerRepositoryImpl()) :
    ViewModel() {
        private val _beerUiState = MutableStateFlow(emptyList<Beer>())
        val beerUiState = _beerUiState.asStateFlow()

        fun getAllBeers() {
            viewModelScope.launch(CoroutineExceptionHandler{ _, exception ->
                println("CoroutineExceptionHandler got $exception")}) {
                beerRepository.getAllBeers()
                    .collect { beers ->
                        _beerUiState.value = beers
                    }
            }
        }
}