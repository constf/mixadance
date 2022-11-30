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
import com.blinked.mixadancefm.databinding.FragmentRelaxBinding
import com.blinked.mixadancefm.viewmodel.MIXADANCE_URL
import com.blinked.mixadancefm.viewmodel.RELAX_URL
import com.blinked.mixadancefm.viewmodel.RadioViewModel

class RelaxFragment: Fragment() {
    private var _binding: FragmentRelaxBinding? = null
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
        _binding = FragmentRelaxBinding.inflate(inflater, container, false)

        val binding = _binding ?: return super.onCreateView(inflater, container, savedInstanceState)

        binding.idRelaxDetailsText.isSelected = true

        showActionSymbolToDo()
        viewModel.preparePlayer(RELAX_URL)

        binding.idRelaxDetailsButton.setOnClickListener {
            if (viewModel.isPlayingNow()){
                viewModel.Pause()
            } else {
                viewModel.Play()
            }
            showActionSymbolToDo()
        }

        binding.idRelaxDetailsText.setOnClickListener { textView ->
            textView.isSelected = !textView.isSelected
        }

        viewModel.metaData.observe(viewLifecycleOwner) { musicData ->
            if (musicData.isNullOrEmpty()) return@observe

            binding.idRelaxDetailsText.text = musicData
        }

        return _binding?.root
    }

    private fun showActionSymbolToDo() {
        val binding = _binding ?: return
        if (viewModel.isPlayingNow()){
            binding.idRelaxDetailsButton.setImageResource(R.drawable.pause_button)
        } else {
            binding.idRelaxDetailsButton.setImageResource(R.drawable.play_button)
        }
    }

}