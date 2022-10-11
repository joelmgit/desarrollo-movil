package com.lugares.ui.lugar

import com.lugares.viewmodel.LugarViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lugares.R
import com.lugares.databinding.FragmentUpdateLugarBinding
import com.lugares.model.Lugar

class UpdateLugarFragment : Fragment() {

    // Recuperación de argumento
    private val args by navArgs<UpdateLugarFragmentArgs>()

    private var _binding: FragmentUpdateLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel = ViewModelProvider(this).get(LugarViewModel::class.java)
        _binding = FragmentUpdateLugarBinding.inflate(inflater, container, false)

        // Se asginan los valores pasados por parametro
        binding.etPlaceName.setText(args.lugar.nombre)
        binding.etEmailPlace.setText(args.lugar.correo)
        binding.etPhonePlace.setText(args.lugar.telefono)
        binding.etWebPlace.setText(args.lugar.web)

        binding.btUpdateLugar.setOnClickListener({updateLugar()})
        return binding.root
    }

    private fun updateLugar() {
        val nombre = binding.etPlaceName.text.toString()
        val phone = binding.etPhonePlace.text.toString()
        val email = binding.etEmailPlace.text.toString()
        val web = binding.etWebPlace.text.toString()
        if(nombre.isNotEmpty()){
            val lugar = Lugar(args.lugar.id, nombre, email, web, phone, args.lugar.latitud,args.lugar.longitud,args.lugar.altura,args.lugar.rutaAudio, args.lugar.rutaimagen)
            lugarViewModel.saveLugar(lugar);
            Toast.makeText(requireContext(),getString(R.string.msg_updated_place),Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addLugarFragment_to_nav_lugar)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_missing_data),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}