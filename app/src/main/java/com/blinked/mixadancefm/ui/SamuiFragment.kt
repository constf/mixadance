package com.blinked.mixadancefm.ui

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blinked.mixadancefm.R
import com.blinked.mixadancefm.databinding.FragmentSamuiBinding
import com.blinked.mixadancefm.viewmodel.RadioViewModel
import com.blinked.mixadancefm.viewmodel.SAMUI_MUSIC_URL

class SamuiFragment: Fragment() {
    private var _binding: FragmentSamuiBinding? = null
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
        _binding = FragmentSamuiBinding.inflate(inflater, container, false)
        val binding = _binding ?: return super.onCreateView(inflater, container, savedInstanceState)

        binding.idSamuiDetailsText.isSelected = true

        showActionSymbolToDo()
        viewModel.preparePlayer(SAMUI_MUSIC_URL)

        binding.idSamuiDetailsButton.setOnClickListener {
            if (viewModel.isPlayingNow()){
                viewModel.Pause()
            } else {
                viewModel.Play()
            }
            showActionSymbolToDo()
        }

        binding.idSamuiDetailsText.setOnClickListener { textView ->
            textView.isSelected = !textView.isSelected
        }

        viewModel.metaData.observe(viewLifecycleOwner) { musicData ->
            if (musicData.isNullOrEmpty()) return@observe

            binding.idSamuiDetailsText.text = musicData
        }

        return _binding?.root
    }


    private fun showActionSymbolToDo() {
        val binding = _binding ?: return
        if (viewModel.isPlayingNow()){
            binding.idSamuiDetailsButton.setImageResource(R.drawable.pause_button)
        } else {
            binding.idSamuiDetailsButton.setImageResource(R.drawable.play_button)
        }
    }
}