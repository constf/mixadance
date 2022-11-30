package com.blinked.mixadancefm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blinked.mixadancefm.R
import com.blinked.mixadancefm.databinding.FragmentJazzBinding
import com.blinked.mixadancefm.viewmodel.JAZZ_URL
import com.blinked.mixadancefm.viewmodel.MIXADANCE_URL
import com.blinked.mixadancefm.viewmodel.RadioViewModel

class JazzFragment: Fragment() {
    private var _binding: FragmentJazzBinding? = null
    private val viewModel: RadioViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callBack =requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.Stop()
            viewModel.clearMedia()
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentJazzBinding.inflate(inflater, container, false)

        val binding = _binding ?: return super.onCreateView(inflater, container, savedInstanceState)

        binding.idJazzDetailsText.isSelected = true

        showActionSymbolToDo()
        viewModel.preparePlayer(JAZZ_URL)

        binding.idJazzDetailsButton.setOnClickListener {
            if (viewModel.isPlayingNow()){
                viewModel.Pause()
            } else {
                viewModel.Play()
            }
            showActionSymbolToDo()
        }

        binding.idJazzDetailsText.setOnClickListener { textView ->
            textView.isSelected = !textView.isSelected
        }

        viewModel.metaData.observe(viewLifecycleOwner) { musicData ->
            if (musicData.isNullOrEmpty()) return@observe

            binding.idJazzDetailsText.text = musicData
        }

        return  _binding?.root
    }

    private fun showActionSymbolToDo() {
        val binding = _binding ?: return
        if (viewModel.isPlayingNow()){
            binding.idJazzDetailsButton.setImageResource(R.drawable.pause_button)
        } else {
            binding.idJazzDetailsButton.setImageResource(R.drawable.play_button)
        }
    }

}