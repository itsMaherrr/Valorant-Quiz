package com.example.lab3shelbyfamily

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    lateinit var cameraScreen :ImageView
    lateinit var takePictureButton: FloatingActionButton
    lateinit var resetButton: FloatingActionButton
    lateinit var snackbar:Snackbar
    val images = arrayOf(R.drawable.aboubakr,R.drawable.astra,R.drawable.cat1,R.drawable.daenerys,R.drawable.shelbyfamily,R.drawable.ghostoftsushinma,R.drawable.rmpsg,R.drawable.tasteofcherry)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar?.hide()
        snackbar = Snackbar.make(findViewById(R.id.screen),"Message",Snackbar.LENGTH_INDEFINITE)
        snackbar.view.setBackgroundColor(Color.MAGENTA)
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setAction("RETRY",View.OnClickListener { reset(resetButton) })
        cameraScreen = findViewById(R.id.screen)
        takePictureButton = findViewById(R.id.takePicBtn)
        resetButton = findViewById(R.id.resetBtn)

        resetButton.isEnabled = false
        resetButton.hide()
    }
    fun shoot(view: View){
        snackbar.show()
        cameraScreen.setImageResource(R.drawable.camera_flash)
        Timer().schedule(200){
            this@MainActivity.runOnUiThread(java.lang.Runnable {
                cameraScreen.setImageResource(images[(images.indices).random()])
            })
        }
    }
    fun reset(view: View){
        takePictureButton.isEnabled = true
        cameraScreen.animate().setDuration(200).scaleYBy(-1f)
        Timer().schedule(500){
            this@MainActivity.runOnUiThread(java.lang.Runnable {
                cameraScreen.setImageResource(R.drawable.camera)
                cameraScreen.animate().setDuration(100).scaleYBy(1f)
            })
        }
    }
}