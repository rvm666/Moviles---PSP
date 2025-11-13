package com.example.gestionproduccionesnavegacion.ui.PantallaMainEstadisticas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gestionproduccionesnavegacion.databinding.FragmentPantallaMainEstadisticasBinding
import com.example.gestionproduccionesnavegacion.domain.useCase.Producciones.GetNumTotalProducciones
import com.example.gestionproduccionesnavegacion.domain.useCase.Usuarios.GetNumTotalUsuarios
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PantallaMainEstadisticas : Fragment() {

    private var _binding: FragmentPantallaMainEstadisticasBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainEstadisticasViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPantallaMainEstadisticasBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventos()
        observacion()
    }


    private fun eventos(){
        mainViewModel.getTotalUsuarios()
        mainViewModel.getTotalProducciones()

    }

    private fun observacion(){
        mainViewModel.state.observe(viewLifecycleOwner){ state ->
            binding.numeroUsuarios.text = state.usuarios.toString()
            binding.numeroProducciones.text = state.producciones.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}