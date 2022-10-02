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

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        val lugarDao = LugarDatabase.getDatabase(application).lugarDao()
        repository = LugarRepository(lugarDao)
        getLugares = repository.getLugares
    }
    fun saveLugar(lugar: Lugar) {
        viewModelScope.launch { repository.saveLugar(lugar) }
    }

    fun deleteLugar(lugar: Lugar) {
        viewModelScope.launch { repository.deleteLugar(lugar) }
    }
}