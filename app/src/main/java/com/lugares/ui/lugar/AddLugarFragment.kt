package com.lugares.ui.lugar

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import com.lugares.viewmodel.LugarViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.lugares.R
import com.lugares.databinding.FragmentAddLugarBinding
import com.lugares.model.Lugar

class AddLugarFragment : Fragment() {
    private var _binding: FragmentAddLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel = ViewModelProvider(this).get(LugarViewModel::class.java)
        _binding = FragmentAddLugarBinding.inflate(inflater, container, false)

        binding.btAddLugar.setOnClickListener({addLugar()})

        activateGPS()

        return binding.root
    }

    private fun activateGPS() {
        if(
            requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requireActivity().requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION),
                105)
        }else{
            val fusedLocationClient : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation.addOnSuccessListener {
                location : Location ->
                    binding.tvLatitud.text = "${location.latitude}"
                    binding.tvLongitud.text = "${location.longitude}"
                    binding.tvAltura.text = "${location.altitude}"
            }
        }
    }

    private fun addLugar() {
        val nombre = binding.etPlaceName.text.toString()
        val phone = binding.etPhonePlace.text.toString()
        val email = binding.etEmailPlace.text.toString()
        val web = binding.etWebPlace.text.toString()
        val longitud = binding.tvLongitud.text.toString().toDouble()
        val latitud = binding.tvLatitud.text.toString().toDouble()
        val altura = binding.tvAltura.text.toString().toDouble()

        if(nombre.isNotEmpty()){
            val lugar = Lugar(0, nombre, email, web, phone,latitud,longitud,altura,"", "")
            lugarViewModel.saveLugar(lugar);
            Toast.makeText(requireContext(),getString(R.string.msg_added_place),Toast.LENGTH_SHORT).show()
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