package com.rigil.scenarios.game

import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rigil.scenarios.R
import kotlinx.android.synthetic.main.fragment_game_score.*

class GameScoreFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_score, container, false)
    }

    private lateinit var gameViewModel: GameViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  gameViewModel = ViewModelProviders.of(requireActivity()).get(GameViewModel::class.java)
//        gameViewModel.questionAnswered.observe(this, Observer {
//            tv_question_instance.text = "$it/${gameViewModel.totalQuestions}"
//        })
//        gameViewModel.timeGivenForQuestion.observe(this, Observer {
//            progress_bar.max = it ?: 0
//        })
//        gameViewModel.timer.observe(this, Observer {
//            tv_timer.text = (it!!/10).toString()
//            progress_bar.progress = gameViewModel.timeGivenForQuestion.value!! - it
//        })
//        gameViewModel.totalScore.observe(this, Observer {
//            tv_game_score.text = it.toString()
//        })
//        gameViewModel.opponentScore.observe(this, Observer {
//            if(gameViewModel.gameMode.value == GameModel.GameMode.SINGLE_PLAYER){
//                tv_opponent_game_score.visibility = View.GONE
//                tv_opponent_score_label.visibility = View.GONE
//            } else {
//                tv_opponent_score_label.visibility =View.VISIBLE
//                tv_opponent_game_score.visibility = View.VISIBLE
//                tv_opponent_game_score.text = it.toString()
//            }
//        })
    }
}