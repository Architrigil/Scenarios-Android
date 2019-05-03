package com.rigil.scenarios.game

class GameModel {

    data class QuestionModel(val question: String?, val answerA: String?,
                             val answerB: String?, val answerC: String?,
                             val answerD: String?, val resultA: String?,
                             val resultB: String?, val resultC: String?,
                             val resultD: String?,var time: Int? = 10)

    data class QuestionAndImageModel(val question: String?, val attachment: String?, var time: Int? = 10)

    enum class State {
        NONE, PAUSE, RUNNING, ANIMATING, PREVIEW, GAME_OVER
    }

    enum class GameMode {
        SINGLE_PLAYER
    }

    var score: Int = 0
    var timer: Int = 0
    var state: GameModel.State = GameModel.State.NONE
    var gameMode: GameModel.GameMode = GameModel.GameMode.SINGLE_PLAYER
    var positionQuestionSet: Int = 0

}