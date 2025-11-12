package com.example.gestionproduccionesnavegacion.ui.EditarUsuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gestionproduccionesnavegacion.databinding.FragmentEditarUsuarioBinding

class EditarUsuario : Fragment() {

    private var _binding: FragmentEditarUsuarioBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            buttonProduccionesPendientes.setOnClickListener {
                val action = EditarUsuarioDirections.actionEditarUsuarioToListaTodasProducciones()
                findNavController().navigate(action)
            }

            buttonVistas.setOnClickListener {
                val action = EditarUsuarioDirections.actionEditarUsuarioToListaTodasProducciones()
                findNavController().navigate(action)
            }

        }

    }

}