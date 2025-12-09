package com.example.gestionproduccionesnavegacion.ui.AniadirProduccion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class AniadirProduccionViewModel @Inject constructor(

) : ViewModel() {

    var state : MutableLiveData<AniadirProduccionState> = MutableLiveData(AniadirProduccionState())
        private set



}