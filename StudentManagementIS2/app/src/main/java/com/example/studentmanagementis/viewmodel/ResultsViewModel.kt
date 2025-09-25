// viewmodel/ResultsViewModel.kt
package com.example.studentmanagementis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentmanagementis.network.ApiService
import com.example.studentmanagementis.network.Result
import kotlinx.coroutines.launch

class ResultsViewModel(private val apiService: ApiService) : ViewModel() {
    private val _results = MutableLiveData<List<Result>>()
    val results: LiveData<List<Result>> = _results

    fun fetchResults() {
        viewModelScope.launch {
            try {
                val resultList = apiService.getResults()
                _results.postValue(resultList)
            } catch (e: Exception) {
                // Mock data for testing (as per previous implementation)
                _results.postValue(listOf(
                    Result(1, "Mathematics", 85, "A"),
                    Result(1, "Science", 78, "B"),
                    Result(1, "English", 92, "A"),
                    Result(1, "History", 65, "C"),
                    Result(1, "Geography", 88, "A")
                ))
            }
        }
    }
}