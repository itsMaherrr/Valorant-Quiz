package com.example.gameofpictures

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import com.example.gameofpictures.databinding.FragmentGuessItMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuessItMainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private lateinit var guessItActivity : FragmentsManipulator
    lateinit var bind : FragmentGuessItMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
        if (activity is FragmentsManipulator) {
            guessItActivity = activity as FragmentsManipulator
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_guess_it_main, container, false)
        val view = bind.root
        bind.playButton.setOnClickListener {
            guessItActivity.replaceFragment(GuessItGameFragment(), true)
        }
        bind.leaveButton.setOnClickListener {
            activity?.apply {
                NavUtils.getParentActivityIntent(this).also {
                    startActivity(it)
                }
            }
        }
        bind.bestScore.text = guessItActivity.bestScore.toString()
        //val animation = AnimationUtils.loadAnimation(activity, R.anim.from_right)
        //container?.animation = animation
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainScreenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int?) =
            GuessItMainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1!!)
                }
            }
    }
}