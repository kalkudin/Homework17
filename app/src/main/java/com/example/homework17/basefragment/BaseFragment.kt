package com.example.homework17.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

typealias inflater<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

abstract class BaseFragment<VB : ViewBinding>(private val inflate: inflater<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        setUpListeners()
        setUpViewActionListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setUp()

    abstract fun setUpListeners()

    abstract fun setUpViewActionListeners()

}