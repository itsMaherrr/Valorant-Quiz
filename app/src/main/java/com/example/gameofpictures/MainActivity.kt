package com.example.gameofpictures

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.marginLeft
import androidx.databinding.DataBindingUtil
import com.example.gameofpictures.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bind : ActivityMainBinding
    private lateinit var toolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        toolbar = findViewById(R.id.myActionBar)
        setSupportActionBar(toolbar)
        findViewById<TextView>(R.id.toolbarTitle).also{
            it.setText(R.string.team_names)
            it.setTextColor(Color.WHITE)
            if(this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                it.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            }
        }
        bind.albumCardView.setOnClickListener {
            Intent(this, AlbumActivity::class.java).also {
                startActivity(it)
            }
        }
        bind.guessItCardView.setOnClickListener{
            Intent(this, GuessItActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}