package com.snehadatta.signalviewer.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snehadatta.data.model.Post
import com.snehadatta.data.remote.MainRepository
import com.snehadatta.signalviewer.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UiState(
    val posts: List<Post>? = emptyList(),
    val error: String? = "",
    val isLoading: Boolean? = false
)

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts(limit: Int = 10) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            when (val result = repository.getPosts(limit)) {
                is Resource.Success -> {
                    _uiState.value = UiState(
                        posts = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _uiState.value = UiState(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = UiState(isLoading = true)
                }
            }
        }
    }

}
