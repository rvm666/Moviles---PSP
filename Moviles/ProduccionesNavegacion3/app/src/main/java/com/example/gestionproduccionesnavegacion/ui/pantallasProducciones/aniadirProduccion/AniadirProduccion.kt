package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.aniadirProduccion


import com.example.gestionproduccionesnavegacion.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gestionproduccionesnavegacion.ui.common.Constantes
import com.example.gestionproduccionesnavegacion.databinding.FragmentAniadirProduccionBinding
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class AniadirProduccion : Fragment() {

    private var _binding: FragmentAniadirProduccionBinding? = null
    private val binding get() = _binding!!
    private val aniadirProduccionViewModel: AniadirProduccionViewModel by viewModels ()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAniadirProduccionBinding.inflate(inflater, container, false)
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


        configurarDropdowns()
        eventos()
        observacion()
        configurarPorUsuario()

    }

    private fun configurarDropdowns() {
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

    private fun configurarPorUsuario() {
        with(binding) {
            vista.isEnabled = false
            valoracion.isEnabled = false
        }
    }



    private fun eventos(){
        with(binding){
            botonGuardarProduccion.setOnClickListener {
                aniadirProduccionViewModel.guardarProduccion(crearProduccion())
                findNavController().popBackStack()
            }

            pelicula.setOnCheckedChangeListener { _, isCheked ->
                aniadirProduccionViewModel.esPelicula(isCheked)
            }
        }
    }

    private fun observacion(){
        aniadirProduccionViewModel.state.observe(viewLifecycleOwner) { state ->
            binding.vista.isEnabled = false
            binding.pelicula.isChecked == state.produccion.esPelicula
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
                        aniadirProduccionViewModel.limpiarMensaje()
                    }
                    is UiEvent.Navigate -> TODO()
                    else -> {}
                }
            }
        }
    }

    private fun crearProduccion(): Produccion {
        val numeroSeasonsTexto = binding.numeroTemporadas.editText?.text.toString()
        val lanzamientoTexto = binding.bookingDateEditText.text.toString()
        val formatter = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA)

        val nuevaProduccion = Produccion(
            id = 0,
            vista = binding.vista.isChecked,
            esPelicula = binding.serie.isChecked,
            nombre = binding.NombrePeli.editText?.text.toString(),
            director = binding.director.editText?.text.toString(),
            numeroSeason = numeroSeasonsTexto.toIntOrNull() ?: 0,
            fechaLanzamiento = LocalDate.parse(lanzamientoTexto, formatter),
            genero = binding.opcionesGenero.editText?.text.toString(),
            pais = binding.opcionesPais.editText?.text.toString(),
            valoracion = binding.valoracion.rating.toDouble()
        )
        return nuevaProduccion
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}