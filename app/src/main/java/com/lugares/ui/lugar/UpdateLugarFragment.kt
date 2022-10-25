package com.lugares.ui.lugar

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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

        binding.tvLatitud.text=args.lugar.latitud.toString()
        binding.tvLongitud.text=args.lugar.longitud.toString()
        binding.tvAltura.text=args.lugar.altura.toString()

        binding.btUpdateLugar.setOnClickListener { updateLugar() }
        binding.btDeleteLugar.setOnClickListener { deleteLugar() }
        binding.btEmail.setOnClickListener { sendEmail() }
        binding.btPhone.setOnClickListener { call() }
        binding.btWhatsapp.setOnClickListener { sendWhatsapp() }
        binding.btWeb.setOnClickListener { viewWeb() }
        binding.btLocation.setOnClickListener { viewInMap() }

        return binding.root
    }

    private fun call() {
        val phone = binding.etPhonePlace.text.toString()
        if(phone.isNotEmpty()){
            val intent = Intent(Intent.ACTION_CALL)
            val uri = "tel:$phone"
            intent.data = Uri.parse(uri)
            if(requireActivity().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                requireActivity().requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 105)
            }else{
                requireActivity().startActivity(intent)
            }
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_missing_data),Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewInMap() {
        val latitud = binding.tvLatitud.text.toString().toDouble()
        val longitud = binding.etWebPlace.text.toString().toDouble()

        if(latitud.isFinite() && longitud.isFinite()){
            val uri = "geo:$latitud,$longitud?z18"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_missing_data),Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewWeb() {
        val web = binding.etWebPlace.text.toString()
        if(web.isNotEmpty()){
            val uri = "http://$web"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_missing_data),Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendWhatsapp() {
        val phone = binding.etPhonePlace.text.toString()
        if(phone.isNotEmpty()){
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = "whatsapp://send?phone=506$phone&text=" + getString(R.string.msg_saludos)
            intent.setPackage("com.whatsapp")
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_missing_data),Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmail() {
        val email = binding.etEmailPlace.text.toString()
        if(email.isNotEmpty()){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type="message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.msg_saludos) + " " + binding.etPlaceName.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_mensaje_correo))
            startActivity(intent)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_missing_data),Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteLugar() {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle(R.string.msg_delete_place)
        alert.setMessage(getString(R.string.msg_ask_to_delete) + "${args.lugar.nombre}?")
        alert.setPositiveButton(getString(R.string.msg_si)){_,_ ->
            lugarViewModel.deleteLugar(args.lugar)
            Toast.makeText(requireContext(),getString(R.string.msg_deleted_place),Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_lugar)
        }
        alert.setNegativeButton(getString(R.string.msg_no)){_,_ ->}
        alert.create().show()
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
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_lugar)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_missing_data),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}