package com.example.gameofpictures

import androidx.fragment.app.Fragment

interface FragmentsManipulator {
    var bestScore: Int?
    fun replaceFragment(fragment: Fragment, saveToBackStack : Boolean)
    fun saveBestScore(bestScore : Int)
}