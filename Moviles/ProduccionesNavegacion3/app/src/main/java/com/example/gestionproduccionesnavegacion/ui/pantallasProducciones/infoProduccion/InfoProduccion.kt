package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.infoProduccion

import com.example.gestionproduccionesnavegacion.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gestionproduccionesnavegacion.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.databinding.FragmentInfoProduccionBinding
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.domain.model.UsuarioProduccionCrossRef
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class InfoProduccion : Fragment() {
    private var _binding: FragmentInfoProduccionBinding? = null

    private val binding get() = _binding!!

    private val args: InfoProduccionArgs by navArgs()

    private val idUsuario: Int by lazy { args.idUsuario }

    private val idProduccion: Int by lazy {args.id}

    private val infoProduccionViewModel: InfoProduccionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoProduccionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookingDateEditText.setOnClickListener {
            val datePickerBuilder = MaterialDatePicker.Builder.datePicker()


            datePickerBuilder.setTitleText("Select Date")
            if (binding.bookingDateEditText.text.toString().isNotEmpty()) {
                try {
                    val dateFormat = SimpleDateFormat(Constantes.FORMATO_FECHA, Locale.getDefault())
                    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                    val parsedDate = dateFormat.parse(binding.bookingDateEditText.text.toString())
                    parsedDate?.let {
                        datePickerBuilder.setSelection(it.time)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            val datePicker = datePickerBuilder.build()
            datePicker.addOnPositiveButtonClickListener { selection ->
                val dateFormat = SimpleDateFormat(Constantes.FORMATO_FECHA, Locale.getDefault())
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                val formattedDate = dateFormat.format(Date(selection))
                binding.bookingDateEditText.setText(formattedDate)
            }
            datePicker.addOnNegativeButtonClickListener {
                binding.bookingDateEditText.clearFocus()
            }
            datePicker.addOnDismissListener {
                binding.bookingDateEditText.clearFocus()
            }
            datePicker.show(childFragmentManager, "DATE_PICKER")
        }


        configurarSelectores()
        infoProduccionViewModel.getProduccion(idProduccion)

        eventos()
        observacion()
        configurarPorUsuario()

    }




    private fun eventos() {
        with(binding){
            pelicula.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    numeroTemporadas.isEnabled = false
                }
            }

            serie.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    numeroTemporadas.isEnabled = true
                }
            }

            botonActualizar.setOnClickListener {
                if (idUsuario == -1){
                    infoProduccionViewModel.updateProduccion(crearProduccion())
                    findNavController().popBackStack()

                } else {
                    infoProduccionViewModel.updateProduccionUsuario(crearProduccion(), idUsuario)
                    findNavController().popBackStack()

                }
            }
        }
    }

    private fun configurarPorUsuario() {
        with(binding) {
            if (idUsuario != -1) {
                NombrePeli.isEnabled = false
                director.isEnabled = false
                bookingDateEditText.isEnabled = false
                opcionesGenero.isEnabled = false
                opcionesPais.isEnabled = false
                pelicula.isEnabled = false
                serie.isEnabled = false
            } else {
                vista.isEnabled = false
                valoracion.isEnabled = false
                opcionesPais.isEnabled = false
                opcionesGenero.isEnabled = false
            }
        }
    }

    private fun configurarSelectores() {
        val generos = resources.getStringArray(R.array.opcionesGenero)
        val adapterGenero = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            generos
        )
        val autoCompleteGenero = binding.opcionesGenero.editText as? AutoCompleteTextView
        autoCompleteGenero?.setAdapter(adapterGenero)


        val paises = resources.getStringArray(R.array.opcionesPais)
        val adapterPais = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            paises
        )
        val autoCompletePais = binding.opcionesPais.editText as? AutoCompleteTextView
        autoCompletePais?.setAdapter(adapterPais)
    }

    private fun observacion() {
        infoProduccionViewModel.state.observe(viewLifecycleOwner) { state ->
            binding.vista.isChecked = state.produccion.vista ?: false
            if (state.produccion.esPelicula == true) {
                binding.pelicula.isChecked = true
            } else {
                binding.serie.isChecked = true
            }
            binding.NombrePeli.editText?.setText(state.produccion.nombre)
            binding.director.editText?.setText(state.produccion.director)
            binding.bookingDateEditText.setText(
                state.produccion.fechaLanzamiento?.format(
                    DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA)
                ) ?: ""
            )
            binding.numeroTemporadas.isEnabled = state.isEnable
            binding.numeroTemporadas.editText?.setText(state.produccion.numeroSeason.toString())
            binding.opcionesGenero.editText?.setText(state.produccion.genero)
            binding.opcionesPais.editText?.setText(state.produccion.pais)
            binding.valoracion.rating = state.produccion.valoracion.toFloat()
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
                        infoProduccionViewModel.limpiarMensaje()
                    }

                    is UiEvent.Navigate -> TODO()
                }
            }
        }
    }

    private fun crearProduccion(): Produccion {
        val numeroSeasonsTexto = binding.numeroTemporadas.editText?.text.toString()
        val lanzamientoTexto = binding.bookingDateEditText.text.toString()
        val formatter = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA)

        val nuevaProduccion = Produccion(
            id = idProduccion,
            vista = binding.vista.isChecked,
            esPelicula = binding.pelicula.isChecked,
            nombre = binding.NombrePeli.editText?.text.toString(),
            director = binding.director.editText?.text.toString(),
            numeroSeason = numeroSeasonsTexto.toIntOrNull() ?: 0,
            fechaLanzamiento = LocalDate.parse(lanzamientoTexto, formatter),
            genero = binding.opcionesGenero.editText?.text.toString(),
            pais = binding.opcionesPais.editText?.text.toString()
        )
        return nuevaProduccion
    }

    private fun crearUsuarioProduccion(): UsuarioProduccionCrossRef{
        val valoracionActual = if (idUsuario != -1) {
            binding.valoracion.rating.toDouble()
        } else {
            infoProduccionViewModel.state.value?.produccion?.valoracion ?: 0.0
        }
        return UsuarioProduccionCrossRef(
            idUsuario,
            idProduccion,
            binding.vista.isChecked,
            valoracionActual
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}