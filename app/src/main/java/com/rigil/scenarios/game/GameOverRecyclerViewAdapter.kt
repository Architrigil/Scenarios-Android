package com.rigil.scenarios.game

//import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rigil.scenarios.R
import com.rigil.scenarios.common.ScenariosQuizDataSourceModel
import kotlinx.android.synthetic.main.row_alert_game_over_question_answers.view.*

class GameOverRecyclerViewAdapter(private val data: Array<*>, private val userAnswers: ArrayList<Pair<String?, String?>>){

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameOverRecyclerViewAdapter.ViewHolder {
//        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
//        val inflatedView = layoutInflater.inflate(R.layout.row_alert_game_over_question_answers,
//                parent, false)
//        return ViewHolder(inflatedView)
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    override fun onBindViewHolder(holder: GameOverRecyclerViewAdapter.ViewHolder, position: Int) {
//        holder.bindItems(position)
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bindItems(position: Int) {
//            data as Array<ScenariosQuizDataSourceModel.Record>
//            val dataFields = data[position].fields
//           // itemView.tv_question.text = "${position + 1}. ${dataFields.Question}"
//            itemView.iv_answer.visibility = View.VISIBLE
//           // val correctAnswer = correctAnswers[position]
//            val userAnswer = userAnswers[position]
//
////            correctAnswer.first.let {
////                itemView.tv_answer.visibility = View.VISIBLE
////                itemView.tv_answer.text = it
////            }
////            correctAnswer.let { pair ->
////                loadImageFromDb(pair.second, itemView.iv_answer, itemView.context)
////                if (pair == userAnswer) {
////                    itemView.iv_wrong_answer.visibility = View.GONE
////                    itemView.tv_wrong_answer.visibility = View.GONE
////                } else if (pair != userAnswer && (userAnswer.first != null || userAnswer.second != null)) {
////                    itemView.iv_wrong_answer.visibility = View.VISIBLE
////                    userAnswer.let { pair ->
////                        loadImageFromDb(userAnswer.second, itemView.iv_wrong_answer, itemView.context)
////                        userAnswer.first.let {
////                            itemView.tv_wrong_answer.visibility = View.VISIBLE
////                            itemView.tv_wrong_answer.text = it
////                        }
////                    }
////                } else {
////                    itemView.tv_wrong_answer.visibility = View.VISIBLE
////                    itemView.tv_wrong_answer.text = "empty"
////                }
////            }
//        }
//    }
}