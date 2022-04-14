package com.example.gameofpictures

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.media.Image
import android.media.ImageReader
import android.media.MediaDescription
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.example.gameofpictures.databinding.ActivityPictureBinding

class PictureActivity() : AppCompatActivity() {
    lateinit var binding : ActivityPictureBinding
    lateinit var image : ImageView
    lateinit var description: TextView
    lateinit var toolbar: Toolbar
    var id:Int = 0
    var currentId = id
    val agentsPictures = hashMapOf(R.id.killjoy to arrayOf(R.drawable.killjoyalbum, R.drawable.killjoy)
        , R.id.phoenix to arrayOf(R.drawable.phoenixalbum, R.drawable.phoenix)
        , R.id.raze to arrayOf(R.drawable.razealbum, R.drawable.raze)
        , R.id.sova to arrayOf(R.drawable.sovaalbum, R.drawable.sova)
        , R.id.sage to arrayOf(R.drawable.sagealbum, R.drawable.sage)
        , R.id.cypher to arrayOf(R.drawable.cypheralbum, R.drawable.cypher)
        , R.id.breach to arrayOf(R.drawable.breachalbum, R.drawable.breach)
        , R.id.viper to arrayOf(R.drawable.viperalbum, R.drawable.viper)
        , R.id.astra to arrayOf(R.drawable.astraalbum, R.drawable.astra)
        , R.id.jett to arrayOf(R.drawable.jettalbum, R.drawable.jett)
        , R.id.chamber to arrayOf(R.drawable.chamberalbum, R.drawable.chamber)
        , R.id.reyna to arrayOf(R.drawable.reynaalbum, R.drawable.reyna)
    )
    val keys = listOf(R.id.killjoy, R.id.phoenix, R.id.raze, R.id.sova, R.id.sage, R.id.cypher, R.id.breach, R.id.viper, R.id.astra, R.id.jett, R.id.chamber, R.id.reyna)

    val agentsDescription : HashMap <Int, Array<String>> = hashMapOf(R.id.killjoy to arrayOf("Killjoy", "The genius of Germany, Killjoy secures the battlefield with ease using her arsenal of inventions. If the damage from her gear doesn't stop her enemies, her robots debuff will help make short work of them.")
        , R.id.phoenix to arrayOf("Phoenix", "Hailing from the U.K., Phoenix's star power shines through in his fighting style, igniting the battlefield with flash and flare. Whether he's got backup or not, he'll rush into a fight on his own terms.")
        , R.id.raze to arrayOf("Raze", "Raze explodes out of Brazil with her big personality and big guns. With her blunt-force-trauma playstyle, she excels at flushing entrenched enemies and clearing tight spaces with a generous dose of “boom.”")
        , R.id.sova to arrayOf("Sova", "Born from the eternal winter of Russia's tundra, Sova tracks, finds, and eliminates enemies with ruthless efficiency and precision. His custom bow and incredible scouting abilities ensure that even if you run, you cannot hide.")
        , R.id.sage to arrayOf("Sage", "The stronghold of China, Sage creates safety for herself and her team wherever she goes. Able to revive fallen friends and stave off aggressive pushes, she provides a calm center to a hellish fight.")
        , R.id.cypher to arrayOf("Cypher", "The Moroccan information broker, Cypher is a one-man surveillance network who keeps tabs on the enemy’s every move. No secret is safe. No maneuver goes unseen. Cypher is always watching.")
        , R.id.breach to arrayOf("Breach", "Breach, the bionic Swede, fires powerful, targeted kinetic blasts to aggressively clear a path through enemy ground. The damage and disruption he inflicts ensures no fight is ever fair.")
        , R.id.viper to arrayOf("Viper", "The American chemist, Viper deploys an array of poisonous chemical devices to control the battlefield and cripple the enemy's vision. If the toxins don't kill her prey, her mind games surely will.")
        , R.id.astra to arrayOf("Astra", "Ghanaian Agent Astra harnesses the energies of the cosmos to reshape battlefields to her whim. With full command of her astral form and a talent for deep strategic foresight, she’s always eons ahead of her enemy’s next move.")
        , R.id.jett to arrayOf("Jett", "Representing her home country of South Korea, Jett's agile and evasive fighting style lets her take risks no one else can. She runs circles around every skirmish, cutting enemies before they even know what hit them.")
        , R.id.chamber to arrayOf("Chamber", "Well dressed and well armed, French weapons designer Chamber expels aggressors with deadly precision. He leverages his custom arsenal to hold the line and pick off enemies from afar, with a contingency built for every plan.")
        , R.id.reyna to arrayOf("Reyna", "Forged in the heart of Mexico, Reyna dominates single combat, popping off with each kill she scores. Her capability is only limited by her raw skill, making her highly dependent on performance."))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picture)
        toolbar = findViewById<Toolbar?>(R.id.myActionBar).also {
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
        var bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt("id")
            bundle = null
        }
        if(savedInstanceState != null){
            id = savedInstanceState.getInt("id")
        }
        currentId = id
        image = binding.character
        description = binding.description
        setActivity(id)
        binding.nextButton.setOnClickListener{
            currentId = keys.get((keys.indexOf(currentId)+1)%12)
            setActivity(currentId)
        }
        binding.previousButton.setOnClickListener{
            currentId = keys.get(Math.floorMod((keys.indexOf(currentId)-1), 12))
            setActivity(currentId)
        }
    }

    public fun setActivity(id : Int) {
        findViewById<TextView>(R.id.toolbarTitle).also {
            it.setText(agentsDescription.get(id)?.get(0))
            it.setTextColor(getColor(R.color.valoRed))
            val typeface = ResourcesCompat.getFont(this, R.font.valorant_font)
            it.typeface = typeface
        }
            agentsPictures.get(id)?.let {
                image.setImageResource(it[1])
            }
            description.setText(agentsDescription.get(id)?.get(1))
    }

    public override fun onSaveInstanceState(savedInstanceState:Bundle){
        savedInstanceState.putInt("id", currentId)
        super.onSaveInstanceState(savedInstanceState)
    }
}