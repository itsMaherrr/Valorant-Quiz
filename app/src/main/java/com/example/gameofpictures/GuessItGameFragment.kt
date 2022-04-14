package com.example.gameofpictures

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.gameofpictures.databinding.FragmentGuessItGameBinding
import kotlinx.coroutines.awaitAll
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val PIC_ID = "agentPicId"

/**
 * A simple [Fragment] subclass.
 * Use the [GuessItGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuessItGameFragment : Fragment() {
    private val agentNameById = hashMapOf(
        R.drawable.killjoy to "killjoy",
        R.drawable.phoenix to "Phoenix",
        R.drawable.raze to "Raze",
        R.drawable.sova to "Sova",
        R.drawable.sage to "Sage",
        R.drawable.cypher to "Cypher",
        R.drawable.breach to "Breach",
        R.drawable.viper to "Viper",
        R.drawable.astra to "Astra",
        R.drawable.jett to "Jett",
        R.drawable.chamber to "Chamber",
        R.drawable.reyna to "Reyna"
    )
    lateinit var buttons: Array<Button>
    private var agentsIds = agentNameById.keys.shuffled().toList()
    private var agentPicID: Int? = R.drawable.phoenix
    private var id: Int? = null
    private var currentAgentId = 0
    private var correctChoice = ""
    private var roundNumber = 1
    private var endGame: Boolean = false
    private var difficulty = Difficulty.EASY
    lateinit var timer: TimerTask
    lateinit var guessItActivity: FragmentsManipulator
    private lateinit var bind: FragmentGuessItGameBinding
    lateinit var timeBar: ProgressBar
    lateinit var agentPic: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            agentPicID = it.getInt(PIC_ID) // made it in order to try to change the current fragment with another fragment with viper agent (in case of correct choice)
        }   // see the commented line in button click listener
        if (activity is FragmentsManipulator) {
            guessItActivity = activity as FragmentsManipulator
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_guess_it_game, container, false)
        val view: View = bind.root
        timeBar = bind.timeBar
        agentPic = bind.agentPic
        buttons = arrayOf(bind.choice1, bind.choice2, bind.choice3, bind.choice4)
        for (button in buttons) {
            button.setOnClickListener {
                when (button.text) {
                    correctChoice -> {
                        if (timeBar.progress != 100) {
                            timer.cancel()
                            bind.scoreView.apply {
                                this.setText((Integer.parseInt(this.text.toString()) + (100 - (timeBar.progress) / 5)*difficulty.bonus).toString())
                            }
                            button.background = resources.getDrawable(R.drawable.correct_answer_button)
                            Timer().schedule(20) {
                                activity?.runOnUiThread {
                                    button.background = resources.getDrawable(R.drawable.button_shape)
                                    updateRound()
                                }
                            }
                        }
                    }
                    else -> {
                        if (timeBar.progress != 100) {
                            timer.cancel()
                            disableButtons()
                            button.background = resources.getDrawable(R.drawable.wrong_answer_button)
                            Timer().schedule(100){
                                endGame("You Lost !!")
                            }
                        }
                    }
                }
            }
        }
        updateRound()
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(agentPicId: Int) =
            GuessItGameFragment().apply {
                arguments = Bundle().apply {
                    putInt(PIC_ID, agentPicId)
                }
            }
    }

    private fun setTimeCounter(difficulty: Difficulty?) {
        timeBar.setProgress(0)
        Thread {
            timer = Timer().schedule(140, (difficulty!!.time / 500).toLong()) {
                timeBar.incrementProgressBy(1)
                if (timeBar.progress == 500) {
                    timer.cancel()
                    disableButtons()
                    endGame("Time Out !!")
                }
            }
            /*Thread.sleep(100)
            while (timeBar.progress < 100){
                timeBar.incrementProgressBy(1)
                Thread.sleep((difficulty!!.time/100).toLong())
            }
            activity?.findViewById<Button>(R.id.choice2)?.setText("Game Over")*/

        }.start()

    }

    private fun updateRound() {
        if ((roundNumber % 4 == 1) && (roundNumber != 1)) {
            difficulty = updatedifficulty(difficulty)
        }
        selectAgent()
        if (!endGame) {
            setTimeCounter(difficulty)
            setAgent(id!!)
            setChoices(id!!)
            setCorrectChoice(id!!)
            roundNumber += 1
        }
        else {
            endGame("You Won !!")
        }
    }

    private fun updatedifficulty(difficulty: Difficulty): Difficulty {
        when (difficulty) {
            Difficulty.EASY -> return Difficulty.MEDIUM
            Difficulty.MEDIUM -> return Difficulty.DIFFICULT
            else -> return Difficulty.DIFFICULT
        }
    }

    private fun setAgent(id: Int) {
        bind.agentPic.setImageResource(id)
    }

    private fun setChoices(id: Int) {
        var choices = arrayOf(agentNameById.get(id))
        while (choices.size < 4) {
            var choice = agentNameById.values.random()
            if (!choices.contains(choice)) {
                choices = choices.plusElement(choice)
            }
        }
        choices.shuffle()
        for (i in 0..3) {
            buttons[i].setText(choices[i])
        }
    }

    private fun setCorrectChoice(id: Int) {
        correctChoice = agentNameById.get(id).toString()

    }

    private fun selectAgent() {
        if (currentAgentId == agentNameById.size){
            endGame = true
        }
        else {
            id = agentsIds[currentAgentId]
            currentAgentId += 1
        }
    }

    private fun endGame(message : String) {
        val score = bind.scoreView.text.toString()
        guessItActivity.replaceFragment(GuessItEndGameFragment.newInstance(score, endGame, message), false)
    }

    private fun disableButtons(){
        for (button in buttons){
            button.isClickable = false
        }
    }

}