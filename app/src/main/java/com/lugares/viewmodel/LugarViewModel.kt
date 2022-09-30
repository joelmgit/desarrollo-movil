package com.lugares.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lugares.data.LugarDatabase
import com.lugares.model.Lugar
import com.lugares.repository.LugarRepository
import kotlinx.coroutines.launch

class LugarViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LugarRepository
    private val getLugares: LiveData<List<Lugar>>
    init {
        val lugarDao = LugarDatabase.getDatabase(application).lugarDao()
        repository = LugarRepository(lugarDao)
        getLugares = repository.getLugares
    }
    suspend fun addLugar(lugar: Lugar) {
        viewModelScope.launch { repository.addLugar(lugar) }
    }
    suspend fun updateLugar(lugar: Lugar) {
        viewModelScope.launch { repository.updateLugar(lugar) }
    }
    suspend fun deleteLugar(lugar: Lugar) {
        viewModelScope.launch { repository.deleteLugar(lugar) }
    }
}