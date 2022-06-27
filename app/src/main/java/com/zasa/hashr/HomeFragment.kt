package com.zasa.hashr

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zasa.hashr.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val homeViewModel : HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, hashAlgorithms)
        binding.actAutoComplete.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        binding.btnGenerate.setOnClickListener {
            onGenerateClicked()
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.clearMenu){
            binding.etPlainText.text.clear()
            showSnackBar("Cleared.")
            return true
        }
        return true
    }

    private fun onGenerateClicked(){
        if (binding.etPlainText.text.isEmpty()){
            showSnackBar("Field Empty.")
        }else{
            lifecycleScope.launch {
                applyAnimations()
                navigateToSuccess(getHashData())
            }
        }
    }

    private fun getHashData(): String{
        val algorithm = binding.actAutoComplete.text.toString()
        val etPlainText = binding.etPlainText.text.toString()
        return homeViewModel.getHash(etPlainText, algorithm)
    }


    private suspend fun applyAnimations() {
        binding.btnGenerate.isClickable = false
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

    private fun navigateToSuccess(hash : String) {
        val directions = HomeFragmentDirections.actionHomeFragmentToSuccessFragment(hash)
        findNavController().navigate(directions)
    }

    private fun showSnackBar(message : String){
        val snackBar = Snackbar.make(
            binding.rootLayout,
            message,
            Snackbar.LENGTH_SHORT
        )
        snackBar.setAction("Okay"){}
        snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
        snackBar.show()
    }

    // for avoiding memory leaks set binding to null
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}