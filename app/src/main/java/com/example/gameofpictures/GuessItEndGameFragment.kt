package com.example.gameofpictures

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.gameofpictures.databinding.FragmentGuessItEndGameBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [GuessItEndGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuessItEndGameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var score: String? = null
    private var win: Boolean? = null
    private var message: String? = null
    lateinit var bind : FragmentGuessItEndGameBinding
    lateinit var guessItActivity : FragmentsManipulator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getString(ARG_PARAM1)
            win = it.getBoolean(ARG_PARAM2)
            message = it.getString(ARG_PARAM3)
        }
        if (activity is FragmentsManipulator){
            guessItActivity = activity as FragmentsManipulator
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_guess_it_end_game, container, false)
        bind.replayButton.setOnClickListener{
            guessItActivity.replaceFragment(GuessItGameFragment(), false)
        }
        bind.homeButton.setOnClickListener{
            this.activity?.onBackPressed()
        }
        bind.resultText.text = message
        bind.scoreText.text = score
        bind.bestScoreText.text = guessItActivity.bestScore.toString()
        if (Integer.parseInt(score)>guessItActivity.bestScore!!){
            guessItActivity.saveBestScore(Integer.parseInt(score))
        }
        bind.bestScoreText.text = guessItActivity.bestScore.toString()
        val view = bind.root
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GuessItEndGameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(score: String, win : Boolean, message : String) =
            GuessItEndGameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, score)
                    putBoolean(ARG_PARAM2, win)
                    putString(ARG_PARAM3, message)
                }
            }
    }
}