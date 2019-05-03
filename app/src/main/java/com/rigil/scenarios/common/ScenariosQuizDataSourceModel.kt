package com.rigil.scenarios.common

import java.io.Serializable

data class ScenariosQuizDataSourceModel(val id: String, val name: String, val records: Array<Record>) : Serializable {
    data class Record(val id: String, val fields: Fields) : Serializable {
        data class Fields(val id: String, val questionText: String, val questionMedia: String, val answerA: String,
                                     val resultA: String, val answerB: String, val resultB: String, val answerC: String,
                                     val resultC: String, val answerD: String, val resultD: String,
                                     val endSuccessfully: Boolean, val endUnsuccessfully: Boolean,
                                     val points: Int  , val time: Int) : Serializable
    }
}
