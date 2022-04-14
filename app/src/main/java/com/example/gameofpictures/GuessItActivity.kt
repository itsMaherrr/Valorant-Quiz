package com.example.gameofpictures

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.gameofpictures.databinding.ActivityGuessItBinding
import java.util.*
import kotlin.concurrent.schedule

class GuessItActivity() : AppCompatActivity(),FragmentsManipulator {
    lateinit var bind: ActivityGuessItBinding
    val title = "Guess it!"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_guess_it)
        val toolbar = findViewById<Toolbar>(R.id.include)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        findViewById<TextView>(R.id.toolbarTitle).also {
            it.text = title
            it.typeface = ResourcesCompat.getFont(this, R.font.valorant_font)
            it.setTextColor(getColor(R.color.valoRed))
        }
        bestScore = savedInstanceState?.getInt("bestScore")
        setMainFragment()

    }

    private fun setMainFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentsContainer, GuessItMainFragment())
        fragmentTransaction.commit()
    }

    override var bestScore: Int? = null
        get() = if (field == null) 0 else field
        set(value) {field = value}

    override fun replaceFragment(fragment: Fragment, saveToBackStack : Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.from_right,R.anim.to_left, R.anim.from_left, R.anim.to_right)
        fragmentTransaction.replace(R.id.fragmentsContainer, fragment)
        if (saveToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    override fun saveBestScore(bestScore: Int) {
        this.bestScore = bestScore
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("bestScore", bestScore!!)
        super.onSaveInstanceState(outState)
    }

}
