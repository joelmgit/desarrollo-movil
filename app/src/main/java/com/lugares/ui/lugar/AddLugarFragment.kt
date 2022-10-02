package com.lugares.ui.lugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lugares.R
import com.lugares.databinding.FragmentAddLugarBinding
import com.lugares.model.Lugar
import com.lugares.viewmodel.LugarViewModel

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

        return binding.root
    }

    private fun addLugar() {
        val nombre = binding.etPlaceName.text.toString()
        val phone = binding.etPhonePlace.text.toString()
        val email = binding.etEmailPlace.text.toString()
        val web = binding.etWebPlace.text.toString()
        if(nombre.isNotEmpty()){
            val lugar = Lugar(0, nombre, email, phone, web,0.0,0.0,0.0,"", "")
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