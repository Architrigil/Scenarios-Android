package com.rigil.scenarios.game

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rigil.scenarios.R
import kotlinx.android.synthetic.main.fragment_game_options.*

class GameOptionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_options, container, false)
    }

    private lateinit var gameViewModel: GameViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameViewModel = ViewModelProviders.of(requireActivity()).get(GameViewModel::class.java)

        iv_home_game_option.setOnClickListener {
            gameViewModel.clear()
            requireActivity().finish()
        }
        iv_pause_play_game_option.setBackgroundResource(android.R.drawable.ic_media_pause)
        iv_pause_play_game_option.setOnClickListener {
            gameViewModel.togglePause()
        }
        gameViewModel.gameState.observe(this, Observer {
            when {
                gameViewModel.gameState.value == GameModel.State.PAUSE -> {
                    iv_pause_play_game_option.visibility = View.VISIBLE
                    iv_pause_play_game_option.setBackgroundResource(android.R.drawable.ic_media_play)
                }
                gameViewModel.gameState.value == GameModel.State.PREVIEW -> {
                    iv_pause_play_game_option.visibility = View.VISIBLE
                    iv_pause_play_game_option.setBackgroundResource(android.R.drawable.ic_media_pause)
                }
                else -> iv_pause_play_game_option.visibility = View.GONE
            }
        })
    }
}