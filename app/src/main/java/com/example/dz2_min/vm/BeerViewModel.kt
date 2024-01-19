package com.example.dz2_min.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dz2_min.domain.PostRepositoryImpl
import com.example.dz2_min.model.Post
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BeerViewModel (private val beerRepository: PostRepositoryImpl = PostRepositoryImpl()) :
    ViewModel() {
        private val _postUiState = MutableStateFlow(emptyList<Post>())
        val beerUiState = _postUiState.asStateFlow()

        fun getAllBeers() {
            viewModelScope.launch(CoroutineExceptionHandler{ _, exception ->
                println("CoroutineExceptionHandler got $exception")}) {
                beerRepository.getAllBeers()
                    .collect { beers ->
                        _postUiState.value = beers
                    }
            }
        }
}