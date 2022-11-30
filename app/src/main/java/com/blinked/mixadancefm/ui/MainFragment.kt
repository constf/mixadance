package com.blinked.mixadancefm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blinked.mixadancefm.R
import com.blinked.mixadancefm.databinding.FragmentMainBinding

class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val binding = _binding ?: return super.onCreateView(inflater, container, savedInstanceState)

        binding.maxidanceActionImage.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_danceFragment)
        }

        binding.fitnessRadioImage.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_fitnessFragment)
        }

        binding.mixadanceJazzImage.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_jazzFragment)
        }

        binding.maxidanceRelaxImage.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_relaxFragment)
        }

        binding.samuiRadioImage.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_samuiFragment)
        }

        return _binding?.root
    }

}