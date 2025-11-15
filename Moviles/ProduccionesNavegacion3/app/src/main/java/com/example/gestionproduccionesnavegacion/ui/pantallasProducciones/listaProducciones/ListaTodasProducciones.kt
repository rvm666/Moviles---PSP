package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.listaProducciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionproduccionesnavegacion.databinding.FragmentListaTodasProduccionesBinding
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaTodasProducciones : Fragment() {
    private var _binding: FragmentListaTodasProduccionesBinding? = null

    private val binding get() = _binding!!

    private val args: ListaTodasProduccionesArgs by navArgs()

    private val idUsuario: Int by lazy { args.id }
    private val vista: Boolean by lazy { args.vista }

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
                override fun onItemSwiped(produccion: Produccion) {
                    listaProduccionesViewModel.borrar(produccion.id)
                }
            }, onClickView = { produccion ->
                // Acción al hacer clic en el elemento (si es necesario)
            })

        binding.recyclerViewProducciones.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerViewProducciones.adapter = adapter
        binding.recyclerViewProducciones.setHasFixedSize(true)
        if (idUsuario == -1) {
            val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    rv: RecyclerView,
                    vh: RecyclerView.ViewHolder,
                    t: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                    val pos = vh.bindingAdapterPosition
                    if (pos == RecyclerView.NO_POSITION) return
                    adapter.getItemAt(pos)?.let { adapter.actions.onItemSwiped(it) }
                }

            })
            touchHelper.attachToRecyclerView(binding.recyclerViewProducciones)
        }


        when{
            idUsuario == -1 -> listaProduccionesViewModel.cargarProducciones()
            vista -> listaProduccionesViewModel.cargarProduccionesVistas(idUsuario)
            else -> listaProduccionesViewModel.cargarProduccionesPendientes(idUsuario)
        }



        eventos()
        observacion()
        configurarPorUsuario()

    }

    private fun eventos(){
        with(binding){
            buttonAniadir.setOnClickListener {
                val action = ListaTodasProduccionesDirections.actionListaTodasProduccionesToAniadirProduccion()
                findNavController().navigate(action)
            }

        }
    }


    private fun configurarPorUsuario() {
        with(binding) {
            if (idUsuario != -1) {
                buttonAniadir.isEnabled = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        when {
            idUsuario == -1 -> listaProduccionesViewModel.cargarProducciones()
            vista -> listaProduccionesViewModel.cargarProduccionesVistas(idUsuario)
            else -> listaProduccionesViewModel.cargarProduccionesPendientes(idUsuario)
        }
    }


    private fun infoProduccion(id: Int) {
        val action = if (idUsuario != -1){
            ListaTodasProduccionesDirections.Companion.actionListaTodasProduccionesToInfoProduccion(id, idUsuario)
        } else {
            ListaTodasProduccionesDirections.Companion.actionListaTodasProduccionesToInfoProduccion(id)
        }
        findNavController().navigate(action)
    }

    private fun observacion() {
        listaProduccionesViewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.producciones)

            state.uiEvent?.let { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(
                            binding.root,
                            event.message,
                            Snackbar.LENGTH_LONG
                        )
                        event.action?.let {
                            snackbar.setAction(event.action ?: "UNDO") {
                                // Aquí puedes manejar la acción de deshacer si lo necesitas
                            }
                        }
                        snackbar.show()
                        listaProduccionesViewModel.limpiarMensaje()
                    }

                    is UiEvent.Navigate -> TODO()
                    else -> {}
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}