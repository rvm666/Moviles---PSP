package com.example.gestionproduccionesnavegacion.ui.listaProducciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionproduccionesnavegacion.databinding.FragmentListaTodasProduccionesBinding
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaTodasProducciones : Fragment() {
    private var _binding: FragmentListaTodasProduccionesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProduccionAdapter

    private val listaProduccionesViewModel: TodasProduccionesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaTodasProduccionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProduccionAdapter(
            actions = object : ProduccionAdapter.ProduccionesActions {
                override fun onItemClick(produccion: Produccion) {
                    infoProduccion(produccion.id)
                }
            }, onClickView = { produccion->
                // Acción al hacer clic en el elemento (si es necesario)
            })

        binding.recyclerViewProducciones.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewProducciones.adapter = adapter
        listaProduccionesViewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.producciones)
        }

        eventos()
        observacion()

    }

    private fun eventos(){
        with(binding){

            buttonAniadir.setOnClickListener {
                val action = ListaTodasProduccionesDirections.actionListaTodasProduccionesToAniadirProduccion()
                findNavController().navigate(action)
            }


        }
    }

    private fun infoProduccion(id: Int) {
        val action = ListaTodasProduccionesDirections.actionListaTodasProduccionesToInfoProduccion(id)
        findNavController().navigate(action)
    }

    private fun observacion() {
        listaProduccionesViewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.producciones)

//            state.uiEvent?.let { event ->
//                when (event) {
//                    is UiEvent.ShowSnackbar -> {
//                        val snackbar = Snackbar.make(
//                            binding.root,
//                            event.message,
//                            Snackbar.LENGTH_LONG
//                        )
//                        event.action?.let {
//                            snackbar.setAction(event.action ?: "UNDO") {
//                                // Aquí puedes manejar la acción de deshacer si lo necesitas
//                            }
//                        }
//                        snackbar.show()
//                        listaUsuariosViewModel.limpiarMensaje()
//                    }
//
//                    is UiEvent.Navigate -> TODO()
//                    else -> {}
//                }
//            }
        }
    }

}