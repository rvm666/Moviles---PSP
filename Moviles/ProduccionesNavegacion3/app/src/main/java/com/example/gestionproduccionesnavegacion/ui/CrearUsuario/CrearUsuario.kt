package com.example.gestionproduccionesnavegacion.ui.CrearUsuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gestionproduccionesnavegacion.databinding.FragmentCrearUsuarioBinding
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.ui.Common.UiEvent
import com.google.android.material.snackbar.Snackbar
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CrearUsuario : Fragment() {

    private var _binding: FragmentCrearUsuarioBinding? = null
    private val binding get() = _binding!!

    private val crearUsuarioViewModel: CrearUsuarioViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrearUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventos()
        observacion()
    }


    private fun eventos(){
        binding.botonCrear.setOnClickListener{
            crearUsuarioViewModel.guardarUsuario(crearUsuario())
            findNavController().navigateUp()
        }

    }

    private fun observacion(){
        crearUsuarioViewModel.state.observe(viewLifecycleOwner){ state ->
            with(binding){
                EmailAddress.editText?.setText(state.usuario.email)
                usuario.editText?.setText(state.usuario.nombre)
                password.editText?.setText(state.usuario.contraseña)
            }
            state.uiEvent?.let{ event ->
                when (event){
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(
                            binding.root,
                            event.message,
                            Snackbar.LENGTH_LONG
                        )
                        event.action?.let {
                            snackbar.setAction(event.action ?: "UNDO"){
                                // Aquí puedes manejar la acción de deshacer si lo necesitas
                            }
                        }
                        snackbar.show()
                        crearUsuarioViewModel.limpiarMensaje()
                    }
                    is UiEvent.Navigate -> TODO()
                    else -> {}
                }
            }
        }
    }

    private fun crearUsuario(): Usuario {
        val nuevoUsuario = Usuario(
            0,
            binding.usuario.editText?.text.toString(),
            binding.password.editText?.text.toString(),
            binding.EmailAddress.editText?.text.toString(),
        )
        return nuevoUsuario
    }




}