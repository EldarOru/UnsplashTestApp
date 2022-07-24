package com.example.unsplashtestapp.presentation.activities

import androidx.fragment.app.Fragment

interface OnFragmentInteractionsListener {

    fun onAddBackStack(name: String, fragment: Fragment)

    fun onPopBackStack()
}