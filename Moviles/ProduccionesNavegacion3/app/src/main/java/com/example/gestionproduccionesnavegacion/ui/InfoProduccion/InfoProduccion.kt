package com.example.gestionproduccionesnavegacion.ui.InfoProduccion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.anadirusuarios1.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.databinding.FragmentInfoProduccionBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoProduccion : Fragment() {
    private var _binding: FragmentInfoProduccionBinding? = null

    private val binding get() = _binding!!

    private val args: InfoProduccionArgs by navArgs()

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




        infoProduccionViewModel.getProduccion(args.id)
        eventos()
        observacion()

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
        }
    }

    private fun observacion() {
        infoProduccionViewModel.state.observe(viewLifecycleOwner) { state ->
            binding.vista.isChecked = state.produccion.vista ?: false
            binding.pelicula.isChecked = state.produccion.esPelicula == true
            binding.serie.isChecked = state.produccion.esPelicula == false
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
        }
    }

}