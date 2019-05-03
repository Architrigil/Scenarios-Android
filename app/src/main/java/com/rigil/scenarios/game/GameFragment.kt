package com.rigil.scenarios.game

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigil.scenarios.R
import com.rigil.scenarios.common.VideoViewFragment
import com.rigil.scenarios.nearby.NearbyConnectionManager
import com.rigil.scenarios.utils.isItVideoFile
import com.rigil.scenarios.utils.loadImageFromDb
import com.rigil.scenarios.utils.loadVideoImageThumbnail
import kotlinx.android.synthetic.main.alert_after_game_complete.view.*
import kotlinx.android.synthetic.main.alert_play_next.view.*
import kotlinx.android.synthetic.main.alert_question_image.view.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlin.collections.ArrayList


class GameFragment : Fragment(), IOnFocusListenable {
    private lateinit var gameViewModel: GameViewModel
    private var pointsEarned = 0
    private var pointsLost = 0
    private var wrongAnswered = 0
    private var correctAnswered = 0
    private lateinit var nearbyConnectionManager: NearbyConnectionManager
    private var endpointId: String = ""
    // private var correctAnswers = ArrayList<Pair<String?, String?>>()
    private var userAnswers = ArrayList<Pair<String?, String?>>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    private fun setUpGameDataObserver(data: Array<*>) {

        if (gameViewModel.gameState.value == GameModel.State.NONE) {
            gameViewModel.setData(data)
            gameViewModel.startGame()
        }
        gameViewModel.questionSet.observe(this, Observer { questionModel ->

            questionModel?.let {
                tv_question_text.text = it.question
                val optionsArray = gameViewModel.currentOptionsArray.value!!
                rv_question_data.layoutManager = GridLayoutManager(requireContext(), 2)
                val animation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_top_to_down_anim)
                rv_question_data.layoutAnimation = animation
                rv_question_data.adapter = questionOptionsAdapterInstance(optionsArray, it)
                rv_question_data?.adapter?.notifyDataSetChanged()
                rv_question_data.scheduleLayoutAnimation()
            }
        })

        gameViewModel.gameState.observe(this, Observer {
            when {
                gameViewModel.gameState.value == GameModel.State.GAME_OVER -> {

                    if (gameViewModel.gameEndSuccessfully) {
                        showAlertToCompletingGame("You have successfully solved the problem. You earned ${gameViewModel.totalScore.value} points.", "Congratulations", true)
                    }

                    if (gameViewModel.gameEndUnsuccessfully) {
                        showAlertToCompletingGame("You gave up on question. Do you want to replay or end the game?", "Game Ended", false)
                    }
                }
                gameViewModel.gameState.value == GameModel.State.PREVIEW -> {
                    rv_question_data.visibility = View.GONE
                    iv_start_button_game.visibility = View.VISIBLE
                    val topToBottom = AnimationUtils.loadLayoutAnimation(requireContext(),
                            R.anim.layout_top_to_down_anim)
                    cv_question.layoutAnimation = topToBottom
                }
                gameViewModel.gameState.value == GameModel.State.RUNNING -> {
                    rv_question_data.visibility = View.VISIBLE
                    iv_start_button_game.visibility = View.GONE
                    val bottomToTop = AnimationUtils.loadLayoutAnimation(requireContext(),
                            R.anim.layout_bottom_to_top)
                    cv_question.layoutAnimation = bottomToTop
                }
            }
        })

        gameViewModel.timer.observe(this, Observer {
            if (gameViewModel.gameState.value == GameModel.State.PREVIEW && it!! < 1) {
                gameViewModel.startActualGame()
            } else if (gameViewModel.gameState.value == GameModel.State.RUNNING && it!! < 1) {
                userAnswers.add(null to null)
                gameViewModel.setGameState(GameModel.State.PAUSE)
                gameViewModel.totalScore.value = gameViewModel.totalScore.value ?: 0 + 0
                showAlertToCompletingGame("Timer is up.", "Game Ended", true)
            }
        })

        gameViewModel.currentQuestionAndImageModel.observe(this, Observer {
            tv_question_text.text = it?.question
            val questionAttachmentUrl = it?.attachment
            if (questionAttachmentUrl != null) {
                if (!questionAttachmentUrl.isItVideoFile()) {
                    loadImageFromDb(questionAttachmentUrl, iv_question_image, requireContext())
                    iv_question_image.setOnClickListener {
                        questionImageAlert(questionAttachmentUrl)
                    }
                } else {
                    loadVideoImageThumbnail(requireContext(), iv_question_image, questionAttachmentUrl)
                    iv_question_image.setOnClickListener {
                        gameViewModel.removeTimer()
                        val videoViewFragment = VideoViewFragment()
                        videoViewFragment.setFileUrlWithListener(questionAttachmentUrl,
                                object : VideoViewFragment.VideoPlaybackListener {
                                    override fun onDestroyVideoView() {
                                        gameViewModel.startGameCountDown()
                                    }
                                })
                        videoViewFragment.show(activity?.fragmentManager, VideoViewFragment::class.java.simpleName)
                    }
                }
            } else {
                iv_question_image.visibility = View.INVISIBLE
            }
        })
    }

    // returns question options, set-data instance
    private fun questionOptionsAdapterInstance(optionsArray: Array<Pair<String?, String?>>, it: GameModel.QuestionModel):
            QuestionOptionsAdapter {

        return QuestionOptionsAdapter(optionsArray,
                gameViewModel.timer.value ?: 100, gameViewModel.timer,
                object : QuestionOptionsAdapter.OptionsInterface {
                    override fun onClickOption(position: Int) {
                        userAnswers.add(optionsArray[position])

                        if (gameViewModel.isGameEnded(optionsArray[position])) {
                            gameViewModel.setGameState(GameModel.State.GAME_OVER)
                        } else {
                            showAlertToLoadNext(optionsArray[position], GameFragment.Companion.CORRECT, gameViewModel.scorePerQuestion)
                        }
                        gameViewModel.setGameState(GameModel.State.PAUSE)
                    }

                    override fun onRemovedTile(position: Int) {
                    }

                    override fun stopTimer(isToStopTimer: Boolean) {
                        if (isToStopTimer) {
                            gameViewModel.removeTimer()
                        }
                    }
                })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel = ViewModelProviders.of(requireActivity()).get(GameViewModel::class.java)

        gameViewModel.setGameMode(GameModel.GameMode.SINGLE_PLAYER)
        val data = requireActivity().intent.getSerializableExtra("data") as Array<*>
        setUpGameDataObserver(data)

        iv_start_button_game.setOnClickListener {
            gameViewModel.startActualGame()
        }
        if (savedInstanceState == null) {
            rv_question_data.visibility = View.GONE
        }
    }

    // shows alert to Replay Game or Finish Game
    private fun showAlertToCompletingGame(message: String, title: String, endGame: Boolean) {
        if (isVisible) {
            val adb = AlertDialog.Builder(requireContext())
            val view = LayoutInflater.from(context).inflate(R.layout.alert_play_next, null)
            adb.setView(view)
            val adbCreate = adb.create()
            val correctOrIncorrect = view.tv_correct_incorrect
            val scored = view.tv_score
            correctOrIncorrect.text = title
            scored.text = message

            if (endGame) {
                view.btn_end_game_alert.text = resources.getString(R.string.ok)
                view.btn_replay_alert.visibility = View.GONE
            }

            view.btn_replay_alert.setOnClickListener {
                adbCreate.dismiss()
                gameViewModel.replayGame()
            }

            view.btn_end_game_alert.setOnClickListener {
                requireActivity().onBackPressed()
            }

            adbCreate.setCancelable(false)
            adbCreate.show()
        }
    }

    // shows alert to load next question-set
    private fun showAlertToLoadNext(answer: Pair<String?, String?>, answerStatus: String, score: Int) {
        if (isVisible) {
            val adb = AlertDialog.Builder(requireContext())
            val view = LayoutInflater.from(context).inflate(R.layout.alert_play_next, null)
            adb.setView(view)
            val adbCreate = adb.create()
            val correctOrIncorrect = view.tv_correct_incorrect
            val scored = view.tv_score
            view.btn_replay_alert.visibility = View.GONE
            view.btn_end_game_alert.text = resources.getString(R.string.next)
            correctOrIncorrect.text = answerStatus
            scored.text = score.toString()
            view.btn_end_game_alert.setOnClickListener {
                adbCreate.dismiss()
                gameViewModel.loadNext(answer)
            }
            adbCreate.setCancelable(false)
            adbCreate.show()
        }
    }

    // opens the image in alert dialog
    private fun questionImageAlert(imageUrl: String) {
        val adb = AlertDialog.Builder(requireContext(), R.style.AlertWithBlackTextAndTransparentBackground)
        val view = LayoutInflater.from(context).inflate(R.layout.alert_question_image, null)
        adb.setView(view)
        val adbCreate = adb.create()
        val scored = view.question_image
        loadImageFromDb(imageUrl, scored, requireContext())
        val doneButton = view.btn_done
        doneButton.setOnClickListener {
            adbCreate.dismiss()
        }
        adbCreate.show()
    }

//    private fun saveDataInLocalDb(leaderboardId: String, recordId: String, Name: String, Score: Int, Duration: Int, GameType: String) {
//        val gameDaoRef = GameDao_Impl(GameDatabase.getInstance(this.activity!!.application))
//        Observable.fromCallable {
//            val recordData = LeaderboardRecords(leaderboardId, recordId, LeaderboardRecords.Fields(leaderboardId,
//                    Name,
//                    Duration, Score, GameType))
//            gameDaoRef.insertLeaderboardRecords(recordData)
//        }.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                }, {
//                    print(it.localizedMessage)
//                })
//    }


    // show alert after the game gets over
    private fun showAlertGameOver(data: Array<*>) {
        if (isVisible) {
            val adb = AlertDialog.Builder(requireContext(), R.style.AlertWithBlackTextAndTransparentBackground).create()
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.alert_after_game_complete, null)
            adb.setView(view)
            val userScore = gameViewModel.totalScore.value
            view.tv_score_game_over_alert.text = "Your score: $userScore"
            view.iv_home_game_alert_game_over.setOnClickListener {
                adb.dismiss()
                gameViewModel.clear()
                requireActivity().finish()
            }
            view.iv_pause_alert_game_over.setOnClickListener {
                correctAnswered = 0
                wrongAnswered = 0
                pointsEarned = 0
                pointsLost = 0
                gameViewModel.restart()
                adb.dismiss()
            }
            view.tv_correct.text = "Correct: $correctAnswered"
            view.tv_points_earned.text = "Points Earned: $pointsEarned"
            view.tv_points_lost.text = "Points Lost: $pointsLost"
            view.tv_wrong.text = "Wrong: $wrongAnswered"
            val gameOverRecyclerViewAdapter = GameOverRecyclerViewAdapter(data, userAnswers)
            view.rv_game_question_options.layoutManager = LinearLayoutManager(requireContext())
            view.rv_game_question_options.adapter = gameOverRecyclerViewAdapter
            //  correctAnswers = ArrayList()
            userAnswers = ArrayList()

            view.rg_segmented.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId == R.id.rb_details) {
                    view.ll_points_details.visibility = View.VISIBLE
                    view.rv_game_question_options.visibility = View.GONE

                } else if (checkedId == R.id.rb_answer) {
                    view.ll_points_details.visibility = View.GONE
                    view.rv_game_question_options.visibility = View.VISIBLE
                }
            }
            adb.setCancelable(false)
            adb.show()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showLogDebug(message: String) {
        Log.d(GameFragment.Companion.TAG, message)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {

        if (hasFocus)
            hideSystemUI()

    }

    private fun hideSystemUI() {
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    companion object {
        const val CORRECT = "Correct"
        //        const val WRONG = "Wrong"
//        const val NOT_ATTEMPTED = "Not Attempted"
        const val TAG = "GameFragment"
    }
}