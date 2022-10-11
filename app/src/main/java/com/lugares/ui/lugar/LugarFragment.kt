package com.lugares.ui.lugar

import com.lugares.viewmodel.LugarViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lugares.R
import com.lugares.adapter.LugarAdapter
import com.lugares.databinding.FragmentLugarBinding

class LugarFragment : Fragment() {

    private var _binding: FragmentLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel = ViewModelProvider(this).get(LugarViewModel::class.java)
        _binding = FragmentLugarBinding.inflate(inflater, container, false)

        binding.addLugarButton.setOnClickListener{
            findNavController().navigate(R.id.action_nav_lugar_to_addLugarFragment)
        }

        // Activar recycler view mediante adapter
        val lugarAdapter = LugarAdapter()
        val recycler = binding.recycler
        recycler.adapter = lugarAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        lugarViewModel.getLugares.observe(viewLifecycleOwner){
            lugares -> lugarAdapter.setLugares(lugares)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}