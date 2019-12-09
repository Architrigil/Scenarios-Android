package com.rigil.scenarios.review

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
//import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.rigil.scenarios.*
import com.rigil.scenarios.common.ScenariosQuizDataSourceModel
import kotlinx.android.synthetic.main.row_trivia_item.view.*


class QuizReviewAdapter(private val flipped: Array<Boolean?>,
                        private val records: Array<ScenariosQuizDataSourceModel.Record>) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizReviewAdapter.ViewHolder {
//        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
//        val inflatedView = layoutInflater.inflate(R.layout.row_trivia_item, parent, false)
//        return ViewHolder(inflatedView)
//    }
//
//    override fun getItemCount(): Int {
//        return records.size
//    }
//
//    override fun onBindViewHolder(holder: QuizReviewAdapter.ViewHolder, position: Int) {
//        holder.bindItems(position)
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bindItems(position: Int) {
//            val mediaOptionAnswer = itemView.iv_answer_image
//            val record = records[position]
//            val attachmentView = itemView.iv_attachment_image
//           // val attachmentUrl = record.fields.attachment?.get(0)?.url
////            if (attachmentUrl != null) {
////                attachmentView.visibility = View.VISIBLE
////                if (attachmentUrl.isItVideoFile()) {
////                    loadVideoImageThumbnail(itemView.context, attachmentView, attachmentUrl)
////                    itemView.iv_attachment_image.setOnClickListener {
////                        val videoViewFragment = VideoViewFragment()
////                        videoViewFragment.setFileUrl(attachmentUrl)
////                        videoViewFragment.show((itemView.context as Activity).fragmentManager,
////                                VideoViewFragment::class.java.simpleName)
////                    }
////                } else {
////                    loadImageFromDb(attachmentUrl, attachmentView, itemView.context)
////                    itemView.iv_attachment_image.setOnClickListener(null)
////                }
////            }
////            if (attachmentUrl == null) {
////                attachmentView.visibility = View.GONE
////            }
//            itemView.iv_flip_question_answer.visibility = View.VISIBLE
//            itemView.iv_flip_question_answer.setBackgroundResource(R.drawable.drawable_flip_for_answer)
//           // itemView.tv_question.text = "${position + 1}. ${record.fields.Question}"
//            itemView.iv_flip_question_answer.setOnClickListener {
//
//                val decelerateInterpolator = ObjectAnimator.ofFloat(itemView, "scaleX", 1f, 0f)
//                val accelerateDecelerateInterpolator = ObjectAnimator.ofFloat(itemView, "scaleX", 0f, 1f)
//                decelerateInterpolator.interpolator = DecelerateInterpolator()
//                accelerateDecelerateInterpolator.interpolator = AccelerateDecelerateInterpolator()
//                decelerateInterpolator.addListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator?) {
//                        super.onAnimationEnd(animation)
//
//                        if (flipped[position] == true) {
//                            itemView.iv_flip_question_answer.visibility = View.VISIBLE
//                            itemView.iv_flip_question_answer.setBackgroundResource(R.drawable.drawable_flip_for_answer)
//                            flipped[position] = false
//                            itemView.tv_question.visibility = View.VISIBLE
//                            mediaOptionAnswer.visibility = View.GONE
////                            if (attachmentUrl != null) {
////                                attachmentView.visibility = View.VISIBLE
////                            }
////
////                            if (attachmentUrl == null) {
////                                attachmentView.visibility = View.GONE
////                            }
////                            itemView.tv_question.text = "${position + 1}. ${record.fields.Question}"
//                        } else {
//                            itemView.iv_flip_question_answer.visibility = View.VISIBLE
//                            itemView.iv_flip_question_answer.setBackgroundResource(R.drawable.drawable_flip_for_answer)
//                            attachmentView.visibility = View.GONE
//                            mediaOptionAnswer.visibility = View.VISIBLE
//                            flipped[position] = true
//                            itemView.tv_question.visibility = View.GONE
//                           // val fileNameUrl = record.fields.AnswerAttachment?.get(0)?.url
////                            if (fileNameUrl.isItVideoFile()) {
////                                loadVideoImageThumbnail(itemView.context, mediaOptionAnswer, fileNameUrl)
////                                mediaOptionAnswer.setOnClickListener {
////                                    val videoViewFragment = VideoViewFragment()
////                                    videoViewFragment.setFileUrl(fileNameUrl)
////                                    videoViewFragment.show((itemView.context as Activity).fragmentManager,
////                                            VideoViewFragment::class.java.simpleName)
////                                }
////                            } else {
////                                loadImageFromDb(fileNameUrl, mediaOptionAnswer, itemView.context)
////                            }
////                            record.fields.Answer.let {
////                                itemView.tv_question.visibility = View.VISIBLE
////                                itemView.tv_question.text = it
////                            }
//                        }
//
//                        accelerateDecelerateInterpolator.start()
//                    }
//                })
//                decelerateInterpolator.start()
//            }
//        }
//    }
}