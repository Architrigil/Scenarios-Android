package com.rigil.scenarios.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import android.os.Handler
import com.rigil.scenarios.common.ScenariosQuizDataSourceModel

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private var gameModel = GameModel()
    var totalScore : MutableLiveData<Int> = MutableLiveData()
    var scorePerQuestion: Int = 0
    var gameState: MutableLiveData<GameModel.State> = MutableLiveData()
    var questionSet: MutableLiveData<GameModel.QuestionModel> = MutableLiveData()
    private lateinit var gameData: Array<*>
    var totalQuestions: Int = 0
    var questionAnswered: MutableLiveData<Int> = MutableLiveData()
    var timer: MutableLiveData<Int> = MutableLiveData()
    var gameMode: MutableLiveData<GameModel.GameMode> = MutableLiveData()
    var currentQuestionAndImageModel: MutableLiveData<GameModel.QuestionAndImageModel> = MutableLiveData()
    var timeGivenForQuestion: MutableLiveData<Int> = MutableLiveData()
    var currentOptionsArray: MutableLiveData<Array<Pair<String?, String?>>> = MutableLiveData()
    private var handler: Handler? = Handler()
    private var runnable: Runnable? = null
    private val hasPauseReached: MutableLiveData<Boolean> = MutableLiveData()
    var totalTimeGame: Long = 0 // Total time of complete game
    val timeBlock: Long = 100 // time for repeating runnable block
    var gameEndSuccessfully = false
    var gameEndUnsuccessfully = false

    init {
        gameModel.positionQuestionSet = 0
        gameModel.score = 0
        totalScore.value = 0
        scorePerQuestion = 0
        questionAnswered.value = 0
        gameState.value = GameModel.State.NONE
        timeGivenForQuestion.value = 100
        totalTimeGame = 0
    }

    // starts preview
    fun startGame() {
        pauseTimer()
        setGameState(GameModel.State.PREVIEW)
        incrementQuestionAnsweredNumber()
        loadCurrentQuestionAndImageModel()
    }

    // starts playing mode
    fun startActualGame() {
        pauseTimer()
        setGameState(GameModel.State.RUNNING)
        loadQuestionDataSet()
    }

    fun togglePause() {
        pauseTimer()
        if (gameModel.state == GameModel.State.PAUSE) {
            setGameState(GameModel.State.PREVIEW)
            startGameCountDown()
        } else if (gameModel.state == GameModel.State.PREVIEW) {
            setGameState(GameModel.State.PAUSE)
        }
    }

    // sets data and no. of questions size
    fun setData(data: Array<*>) {
        gameData = data
        totalQuestions = gameData.size
    }

    // loads question data-set at given index
    private fun loadQuestionDataSet() {
        val questionInstance = gameData[gameModel.positionQuestionSet] as ScenariosQuizDataSourceModel.Record
        val fields = questionInstance.fields
        val time = (fields.time).toString().toIntOrNull() ?: 10
        val questionDataModel = GameModel.QuestionModel(fields.questionText, fields.answerA,
                fields.answerB, fields.answerC,
                fields.answerD, fields.resultA, fields.resultB, fields.resultC, fields.resultD, fields.time)
        setGameTimer(time * 10)
        timeGivenForQuestion.value = time * 10
        val optionsArrayList = ArrayList<Pair<String?, String?>>()
        if (questionDataModel.answerA != null) {
            optionsArrayList.add(questionDataModel.answerA to questionDataModel.resultA)
        }
        if (questionDataModel.answerB != null) {
            optionsArrayList.add(questionDataModel.answerB to questionDataModel.resultB)
        }
        if (questionDataModel.answerC != null) {
            optionsArrayList.add(questionDataModel.answerC to questionDataModel.resultC)
        }
        if (questionDataModel.answerD != null) {
            optionsArrayList.add(questionDataModel.answerD to questionDataModel.resultD)
        }

        //   while (optionsArrayList.size < 4) optionsArrayList.add(randomOption())
        val optionsArray = optionsArrayList.shuffled().toTypedArray()
        currentOptionsArray.value = optionsArray
        questionSet.value = questionDataModel
        startGameCountDown()
    }

    //loads preview of question-data set
    private fun loadCurrentQuestionAndImageModel() {
        val questionInstance = gameData[gameModel.positionQuestionSet] as ScenariosQuizDataSourceModel.Record
        val fields = questionInstance.fields
        val time = (fields.time).toString().toIntOrNull() ?: 10
        timeGivenForQuestion.value = time * 10
        setGameTimer(time * 10)
        currentQuestionAndImageModel.value = GameModel.QuestionAndImageModel(fields.questionText,
                null, time)
        scorePerQuestion = fields.points
        startGameCountDown()
    }

    fun loadNext(givenAnswer: Pair<String?, String?>) {
        pauseTimer()

        gameModel.positionQuestionSet = givenAnswer.second!!.toInt() - 1
        val questionInstance = gameData[gameModel.positionQuestionSet] as ScenariosQuizDataSourceModel.Record

        if (questionInstance.fields.endSuccessfully || questionInstance.fields.endUnsuccessfully) {
            setGameState(GameModel.State.GAME_OVER)
            setGameTimer(0)
        } else {
            incrementQuestionAnsweredNumber()
            setGameState(GameModel.State.PREVIEW)
            loadCurrentQuestionAndImageModel()
        }
    }

    fun replayGame() {
        loadCurrentQuestionAndImageModel()
        setGameState(GameModel.State.PREVIEW)
    }

    fun isGameEnded(givenAnswer: Pair<String?, String?>): Boolean {
        if (this.totalScore.value != null) {
            this.totalScore.value = this.totalScore!!.value!! + scorePerQuestion
        }

        val nextQuestionIndex = givenAnswer.second!!.toInt() - 1
        val questionInstance = gameData[nextQuestionIndex] as ScenariosQuizDataSourceModel.Record

        if (questionInstance.fields.endSuccessfully || questionInstance.fields.endUnsuccessfully) {
            gameEndSuccessfully = questionInstance.fields.endSuccessfully
            gameEndUnsuccessfully = questionInstance.fields.endUnsuccessfully
            return true
        }
        return false
    }

    private fun incrementQuestionAnsweredNumber() {
        if (questionAnswered.value != null) {
            questionAnswered.value = questionAnswered!!.value!!.plus(1)
            return
        }
        questionAnswered.value = 1
    }

    private fun pauseTimer() {
        hasPauseReached.value = true
        handler?.removeCallbacks(runnable)
    }

    fun removeTimer() {
        handler?.removeCallbacks(runnable)
    }

    // starts game countdown
    fun startGameCountDown() {
        handler?.removeCallbacks(runnable)
        runnable = null
        runnable = object : Runnable {
            override fun run() {
                if (gameState.value == GameModel.State.PREVIEW ||
                        gameState.value == GameModel.State.RUNNING) {

                    totalTimeGame += timeBlock
                    gameModel.timer--
                    setGameTimer(gameModel.timer)
                    handler?.postDelayed(this, timeBlock)
                }
            }
        }
        handler?.postDelayed(runnable, timeBlock)
    }

    //sets game state
    fun setGameState(state: GameModel.State) {
        gameModel.state = state
        gameState.value = state
    }

    //sets timer
    fun setGameTimer(timer: Int) {
        gameModel.timer = timer
        this.timer.value = timer
    }

    //sets game mode
    fun setGameMode(gameMode: GameModel.GameMode) {
        gameModel.gameMode = gameMode
        this.gameMode.value = gameMode
    }

    fun clear() {
        onCleared()
    }

    // restarts the game and reset game status
    fun restart() {
        gameModel.positionQuestionSet = 0
        gameModel.score = 0
        totalScore.value = 0
        scorePerQuestion = 0
        timeGivenForQuestion.value = 100
        gameState.value = GameModel.State.NONE
        totalTimeGame = 0
        questionAnswered.value = 0
        startGame()
    }

}