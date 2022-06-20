package com.example.amunstore.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.amunstore.R
import com.example.amunstore.databinding.FragmentConnectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoConnectionFragment : Fragment() {


    private var _binding: FragmentConnectionBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentConnectionBinding.inflate(inflater, container, false)
        val root: View = binding.root
  //Toast.makeText(context, getString(R.string.connected), Toast.LENGTH_SHORT).show()

        binding.tryAgainAppCompactButton.setOnClickListener {
            Toast.makeText(context, getString(R.string.noConnection), Toast.LENGTH_SHORT).show()

        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}