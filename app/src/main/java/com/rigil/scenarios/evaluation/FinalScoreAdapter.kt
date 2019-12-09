package com.rigil.scenarios.evaluation

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
import com.rigil.scenarios.R
import com.rigil.scenarios.common.VideoViewFragment
import com.rigil.scenarios.utils.isItVideoFile
import com.rigil.scenarios.utils.isValidUrl
import com.rigil.scenarios.utils.loadImageFromDb
import com.rigil.scenarios.utils.loadVideoImageThumbnail
import kotlinx.android.synthetic.main.row_final_score.view.*


class FinalScoreAdapter(private var userAnswers: Array<Pair<String?, String?>?>,
                        private var correctAnswers: Array<Pair<String?, String?>>,
                        private val questions: Array<Pair<String?, String?>>) {

//    override fun getItemCount(): Int {
//        return userAnswers.size
//    }
//
//    /*init {
//        userAnswers = userAnswers.asIterable().shuffled().toTypedArray()
//        correctAnswers = correctAnswers.asIterable().shuffled().toTypedArray()
//    }*/
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalScoreAdapter.ViewHolder {
//        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.row_final_score, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: FinalScoreAdapter.ViewHolder, position: Int) {
//        holder.bindItems(position)
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bindItems(position: Int) {
//            val questionText = questions[position].first
//            itemView.tv_question_final.text = "${position + 1}. $questionText"
//            val correctAnswer = correctAnswers[position]
//            val userAnswer = userAnswers[position]
//            if (correctAnswer.second?.isValidUrl() == true) {
//                if (correctAnswer.second.isItVideoFile()) {
//                    loadVideoImageThumbnail(itemView.context, itemView.iv_correct_answer_image_final, correctAnswer.second)
//                    loadVideoImageThumbnail(itemView.context, itemView.iv_answered_image_final, userAnswer?.second)
//                    itemView.iv_correct_answer_image_final.setOnClickListener {
//                        val videoViewFragment = com.rigil.scenarios.common.VideoViewFragment()
//                        videoViewFragment.setFileUrl(correctAnswer.second)
//                        videoViewFragment.show((itemView.context as Activity).fragmentManager,
//                                VideoViewFragment::class.java.simpleName)
//                    }
//                    itemView.iv_answered_image_final.setOnClickListener {
//                        val videoViewFragment = VideoViewFragment()
//                        videoViewFragment.setFileUrl(correctAnswer.second)
//                        videoViewFragment.show((itemView.context as Activity).fragmentManager,
//                                VideoViewFragment::class.java.simpleName)
//                    }
//                } else {
//                    itemView.iv_correct_answer_image_final.visibility = View.VISIBLE
//                    itemView.tv_correct_answer_text.visibility=View.VISIBLE
//                    itemView.tv_correct_answer_text.text = correctAnswer.first
//                    itemView.iv_answered_image_final.visibility = View.VISIBLE
//                    itemView.tv_answered_text.visibility=View.VISIBLE
//                    loadImageFromDb(correctAnswer.second, itemView.iv_correct_answer_image_final, itemView.context)
//                    loadImageFromDb(userAnswer?.second, itemView.iv_answered_image_final, itemView.context)
//                    itemView.tv_answered_text.text = userAnswer?.first
//                    if(userAnswer?.second==correctAnswer.second) {
//                        itemView.tv_answered_text.setTextColor(Color.GREEN)
//                    }
//                    else{
//                        itemView.tv_answered_text.setTextColor(Color.RED)
//                    }
//                }
//            }
//            if (correctAnswer.second?.isValidUrl() != true) {
//                itemView.iv_correct_answer_image_final.visibility = View.GONE
//                itemView.iv_answered_image_final.visibility = View.VISIBLE
//                itemView.tv_answered_text.visibility = View.VISIBLE
//                itemView.tv_correct_answer_text.visibility = View.VISIBLE
//                itemView.tv_answered_text.text = userAnswer?.first
//                loadImageFromDb(userAnswer?.second, itemView.iv_answered_image_final, itemView.context)
//                itemView.tv_correct_answer_text.text = correctAnswer.first
//                if (userAnswer?.first == correctAnswer.first) {
//                    itemView.tv_answered_text.setTextColor(Color.GREEN)
//                } else {
//                    itemView.tv_answered_text.setTextColor(Color.RED)
//                }
//            }
//        }
 //   }
}