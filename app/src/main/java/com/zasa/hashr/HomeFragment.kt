package com.zasa.hashr

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.zasa.hashr.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, hashAlgorithms)
        binding.actAutoComplete.setAdapter(arrayAdapter)

        binding.btnGenerate.setOnClickListener {
            lifecycleScope.launch{
                applyAnimations()
                navigateToSuccess()
            }
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    private suspend fun applyAnimations(){
        binding.tvTitle.animate().alpha(0f).duration = 400L
        binding.btnGenerate.animate().alpha(0f).duration = 400L
        binding.tilHash.animate()
            .alpha(0f)
            .translationXBy(1200f)
            .duration = 400L
        binding.etPlainText.animate()
            .alpha(0f)
            .translationXBy(-1200f)
            .duration = 400L

        delay(300)
        binding.vSuccess.animate().alpha(1f).duration = 600L
        binding.vSuccess.animate().rotationBy(720f).duration = 600L
        binding.vSuccess.animate().scaleXBy(900f).duration = 800L
        binding.vSuccess.animate().scaleYBy(900f).duration = 800L

        delay(500L)
        binding.ivSuccess.animate().alpha(1f).duration = 1000L

        delay(1500L)
    }

    private fun navigateToSuccess(){
        findNavController().navigate(R.id.action_homeFragment_to_successFragment)
    }

    // for avoiding memory leaks set binding to null
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}