package com.rigil.scenarios.evaluation

import android.app.Activity
import android.graphics.Color
//import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rigil.scenarios.R
import com.rigil.scenarios.common.ScenariosQuizDataSourceModel
import com.rigil.scenarios.utils.loadImageFromDb
import kotlinx.android.synthetic.main.row_evaluation_image_trivia.view.*

class QuizEvaluationImageAdapter(private val records: Array<ScenariosQuizDataSourceModel.Record>,
                                 private val listener: QuizEvaluationImageAdapter.SelectImagePositionListener) {

    interface SelectImagePositionListener {
        fun selectPos(answer: Pair<String?, String?>?, position: Int)
    }

//    override fun getItemCount(): Int {
//        return records.size
//    }
//
//   /* init {
//        var position  = 0
//        var record = records[position]
//        var  wrongAnswer1 = record.fields.WrongAnswer1?.asIterable()?.shuffled()?.toTypedArray()
//        var  wrongAnswer2 = record.fields.WrongAnswer2?.asIterable()?.shuffled()?.toTypedArray()
//        var  wrongAnswer3 = record.fields.WrongAnswer3?.asIterable()?.shuffled()?.toTypedArray()
//        var correctAnswer = record.fields.Answer?.asIterable()?.shuffled()?.toTypedArray()
//        position++
//    }*/
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizEvaluationImageAdapter.ViewHolder {
//        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
//        val inflatedView = layoutInflater.inflate(R.layout.row_evaluation_image_trivia, parent, false)
//        return ViewHolder(inflatedView)
//    }
//
//    override fun onBindViewHolder(holder: QuizEvaluationImageAdapter.ViewHolder, position: Int) {
//        holder.bindItems(position, listener)
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bindItems(position: Int, listener: QuizEvaluationImageAdapter.SelectImagePositionListener) {
//            val record = records[position]
//          //  itemView.tv_question_evaluation.text = "${position + 1}. ${record.fields.Question}"
//          //  val attachmentUrl = record.fields.attachment
////            if (attachmentUrl != null) {
////                itemView.iv_question_evaluation.visibility = View.VISIBLE
////                val fileName = record.fields.attachment[0]?.url
////                if (fileName.isItVideoFile()) {
////                    loadVideoImageThumbnail(itemView.context, itemView.iv_question_evaluation, fileName)
////                    itemView.iv_question_evaluation.setOnClickListener {
////                        val videoViewFragment = VideoViewFragment()
////                        videoViewFragment.setFileUrl(fileName)
////                        videoViewFragment.show((itemView.context as Activity).fragmentManager,
////                                VideoViewFragment::class.java.simpleName)
////                    }
////                } else loadImageFromDb(fileName, itemView.iv_question_evaluation, itemView.context)
////            }
////            if (attachmentUrl == null) {
////                itemView.iv_question_evaluation.visibility = View.GONE
////            }
//                val clickStates = arrayOfNulls<Boolean>(records.size)
//                itemView.tv_question_evaluation.setOnClickListener {
//                    if (clickStates[position] == false) {
//                        clickStates[position] = true
//                        itemView.tv_question_evaluation.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0)
//                       itemView.ll_options.visibility = View.VISIBLE
//
//                        var arrayOfOptions = arrayOfNulls<Pair<String?, String?>>(4)
//                        val fields = record.fields
////                        arrayOfOptions[0] = Pair(fields.Answer, fields.AnswerAttachment?.get(0)?.url)
////                        arrayOfOptions[1] = Pair(fields.WrongAnswer1, fields.WrongAttachment1?.get(0)?.url)
////                        arrayOfOptions[2] = Pair(fields.WrongAnswer2, fields.WrongAttachment2?.get(0)?.url)
////                        arrayOfOptions[3] = Pair(fields.WrongAnswer3, fields.WrongAttachment3?.get(0)?.url)
//                        arrayOfOptions = arrayOfOptions.asIterable().shuffled().toTypedArray()
//
//                        val tvOption1 = itemView.tv_option1_evaluation
//                        val tvOption2 = itemView.tv_option2_evaluation
//                        val tvOption3 = itemView.tv_option3_evaluation
//                        val tvOption4 = itemView.tv_option4_evaluation
//
//                        arrayOfOptions[0]?.first.let { tvOption1.text = it }
//                        arrayOfOptions[1]?.first.let { tvOption2.text = it }
//                        arrayOfOptions[2]?.first.let { tvOption3.text = it }
//                        arrayOfOptions[3]?.first.let { tvOption4.text = it }
//
//                        loadImageFromDb(arrayOfOptions[0]?.second, itemView.iv_option1_evaluation, itemView.context)
//                        loadImageFromDb(arrayOfOptions[1]?.second, itemView.iv_option2_evaluation, itemView.context)
//                        loadImageFromDb(arrayOfOptions[2]?.second, itemView.iv_option3_evaluation, itemView.context)
//                        loadImageFromDb(arrayOfOptions[3]?.second, itemView.iv_option4_evaluation, itemView.context)
//
//                        tvOption1.setOnClickListener {
//                            makeImageViewBackgroundsTransparent()
//                            setTextViewBackgroundsTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
//                            tvOption1.setBackgroundColor(Color.GRAY)
//                            itemView.iv_option1_evaluation.setBackgroundColor(Color.GRAY)
//
//                            listener.selectPos(arrayOfOptions[0], position)
//                        }
//                        tvOption2.setOnClickListener {
//                            makeImageViewBackgroundsTransparent()
//                            setTextViewBackgroundsTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
//                            tvOption2.setBackgroundColor(Color.GRAY)
//                            itemView.iv_option2_evaluation.setBackgroundColor(Color.GRAY)
//
//                            listener.selectPos(arrayOfOptions[1], position)
//                        }
//                        tvOption3.setOnClickListener {
//                            makeImageViewBackgroundsTransparent()
//                            setTextViewBackgroundsTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
//                            tvOption3.setBackgroundColor(Color.GRAY)
//
//                            itemView.iv_option3_evaluation.setBackgroundColor(Color.GRAY)
//                            listener.selectPos(arrayOfOptions[2], position)
//                        }
//                        tvOption4.setOnClickListener {
//                            makeImageViewBackgroundsTransparent()
//                            setTextViewBackgroundsTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
//                            itemView.iv_option4_evaluation.setBackgroundColor(Color.GRAY)
//
//                            tvOption4.setBackgroundColor(Color.GRAY)
//                            listener.selectPos(arrayOfOptions[3], position)
//                        }
//
//                        itemView.iv_option1_evaluation.setOnClickListener {
//                            setTextViewBackgroundsTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
//                            makeImageViewBackgroundsTransparent()
//                            itemView.iv_option1_evaluation.setBackgroundColor(Color.GRAY)
//                            tvOption1.setBackgroundColor(Color.GRAY)
//                            listener.selectPos(arrayOfOptions[0], position)
//                        }
//                        itemView.iv_option2_evaluation.setOnClickListener {
//                            setTextViewBackgroundsTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
//                            makeImageViewBackgroundsTransparent()
//                            itemView.iv_option2_evaluation.setBackgroundColor(Color.GRAY)
//                            tvOption2.setBackgroundColor(Color.GRAY)
//                            listener.selectPos(arrayOfOptions[1], position)
//                        }
//                        itemView.iv_option3_evaluation.setOnClickListener {
//                            setTextViewBackgroundsTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
//                            makeImageViewBackgroundsTransparent()
//                            itemView.iv_option3_evaluation.setBackgroundColor(Color.GRAY)
//                            tvOption3.setBackgroundColor(Color.GRAY)
//                            listener.selectPos(arrayOfOptions[2], position)
//                        }
//                        itemView.iv_option4_evaluation.setOnClickListener {
//                            setTextViewBackgroundsTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
//                            makeImageViewBackgroundsTransparent()
//                            itemView.iv_option4_evaluation.setBackgroundColor(Color.GRAY)
//                            tvOption4.setBackgroundColor(Color.GRAY)
//                            listener.selectPos(arrayOfOptions[3], position)
//                        }
//
//                    } else {
//                        clickStates[position] = false
//                        itemView.tv_question_evaluation.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0)
//                       itemView.ll_options.visibility = View.GONE
//                    }
//
//                }
//
//        }
//
//        private fun makeImageViewBackgroundsTransparent() {
//            itemView.iv_option1_evaluation.setBackgroundColor(Color.TRANSPARENT)
//            itemView.iv_option2_evaluation.setBackgroundColor(Color.TRANSPARENT)
//            itemView.iv_option3_evaluation.setBackgroundColor(Color.TRANSPARENT)
//            itemView.iv_option4_evaluation.setBackgroundColor(Color.TRANSPARENT)
//        }
//
//        private fun setTextViewBackgroundsTransparent(tvOption1: TextView, tvOption2: TextView, tvOption3: TextView, tvOption4: TextView) {
//            tvOption1.setBackgroundColor(Color.TRANSPARENT)
//            tvOption2.setBackgroundColor(Color.TRANSPARENT)
//            tvOption3.setBackgroundColor(Color.TRANSPARENT)
//            tvOption4.setBackgroundColor(Color.TRANSPARENT)
//        }
//
//    }


}

