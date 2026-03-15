package edu.upc.xasf.xasf_lab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.upc.xasf.xasf_lab.model.Municipality
import edu.upc.xasf.xasf_lab.model.MunicipalityResponse
import edu.upc.xasf.xasf_lab.repository.MunicipalityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

sealed interface MunicipalityUiState {
    object Idle : MunicipalityUiState
    object Loading : MunicipalityUiState
    data class Success(val data: List<Municipality>) : MunicipalityUiState
    data class Error(val message: String) : MunicipalityUiState
}

class MunicipalityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MunicipalityUiState>(MunicipalityUiState.Idle)
    private val repository: MunicipalityRepository = MunicipalityRepository()
    val uiState: StateFlow<MunicipalityUiState> = _uiState
    private val json = Json { ignoreUnknownKeys = true }

    fun fetchList(urlString: String) {
        viewModelScope.launch {
            _uiState.value = MunicipalityUiState.Loading
            try {
                val result = repository.fetchMunicipalities(urlString)
                _uiState.value = MunicipalityUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = MunicipalityUiState.Error(e.localizedMessage ?: "Unknown Error")
            }
        }
    }
}