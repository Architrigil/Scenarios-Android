package com.rigil.scenarios.evaluation

import android.app.Activity
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rigil.scenarios.R
import com.rigil.scenarios.common.ScenariosQuizDataSourceModel
import com.rigil.scenarios.common.VideoViewFragment
import com.rigil.scenarios.utils.loadVideoImageThumbnail
import kotlinx.android.synthetic.main.row_evaluation_video_trivia.view.*


class QuizEvaluationVideoAdapter(private val records: Array<ScenariosQuizDataSourceModel.Record>,
                                 private val listener: QuizEvaluationVideoAdapter.SelectImagePositionListener) :
        RecyclerView.Adapter<QuizEvaluationVideoAdapter.ViewHolder>() {

    interface SelectImagePositionListener {
        fun selectPos(answer: Pair<String?, String?>?, position: Int)
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizEvaluationVideoAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val inflatedView = layoutInflater.inflate(R.layout.row_evaluation_video_trivia, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: QuizEvaluationVideoAdapter.ViewHolder, position: Int) {
        holder.bindItems(position, listener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(position: Int, listener: QuizEvaluationVideoAdapter.SelectImagePositionListener) {
            val record = records[position]
          //  itemView.tv_question_evaluation.text = "${position + 1}. ${record.fields.Question}"
          //  val attachmentUrl = record.fields.attachment
//            if (attachmentUrl != null) {
//                val fileName = record.fields.attachment[0]?.url
//                if(fileName.isItVideoFile()){
//                    loadVideoImageThumbnail(itemView.context, itemView.iv_question_evaluation, fileName)
//                    itemView.iv_question_evaluation.setOnClickListener {
//                        val videoViewFragment = VideoViewFragment()
//                        videoViewFragment.setFileUrl(fileName)
//                        videoViewFragment.show((itemView.context as Activity).fragmentManager,
//                                VideoViewFragment::class.java.simpleName)
//                    }
//                } else loadImageFromDb(fileName, itemView.iv_question_evaluation, itemView.context)
//            }
            val clickStates = arrayOfNulls<Boolean>(records.size)

            itemView.tv_question_evaluation.setOnClickListener {
                if (clickStates[position] == false) {
                    clickStates[position] = true
                    itemView.tv_question_evaluation.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0)
                    itemView.ll_options.visibility = View.VISIBLE
                    val arrayOfOptions = arrayOfNulls<Pair<String?, String?>>(4)
                    val fields = record.fields
//                    arrayOfOptions[0] = Pair(fields.Answer, fields.AnswerAttachment?.get(0)?.url)
//                    arrayOfOptions[1] = Pair(fields.WrongAnswer1, fields.WrongAttachment1?.get(0)?.url)
//                    arrayOfOptions[2] = Pair(fields.WrongAnswer2, fields.WrongAttachment2?.get(0)?.url)
//                    arrayOfOptions[3] = Pair(fields.WrongAnswer3, fields.WrongAttachment3?.get(0)?.url)

                    val tvOption1 = itemView.tv_option1_evaluation
                    val tvOption2 = itemView.tv_option2_evaluation
                    val tvOption3 = itemView.tv_option3_evaluation
                    val tvOption4 = itemView.tv_option4_evaluation

                    arrayOfOptions[0]?.first.let { tvOption1.text = it }
                    arrayOfOptions[1]?.first.let { tvOption2.text = it }
                    arrayOfOptions[2]?.first.let { tvOption3.text = it }
                    arrayOfOptions[3]?.first.let { tvOption4.text = it }

                    tvOption1.setOnClickListener {
                        makeTextViewBackgroundTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
                        tvOption1.setBackgroundColor(Color.GRAY)
                        listener.selectPos(arrayOfOptions[0], position)
                    }
                    tvOption2.setOnClickListener {
                        makeTextViewBackgroundTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
                        tvOption2.setBackgroundColor(Color.GRAY)
                        listener.selectPos(arrayOfOptions[1], position)
                    }
                    tvOption3.setOnClickListener {
                        makeTextViewBackgroundTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
                        tvOption3.setBackgroundColor(Color.GRAY)
                        listener.selectPos(arrayOfOptions[2], position)
                    }
                    tvOption4.setOnClickListener {
                        makeTextViewBackgroundTransparent(tvOption1, tvOption2, tvOption3, tvOption4)
                        tvOption4.setBackgroundColor(Color.GRAY)
                        listener.selectPos(arrayOfOptions[3], position)
                    }

                    loadVideoImageThumbnail(itemView.context, itemView.iv_option1_evaluation, arrayOfOptions[0]?.second)
                    loadVideoImageThumbnail(itemView.context, itemView.iv_option2_evaluation, arrayOfOptions[1]?.second)
                    loadVideoImageThumbnail(itemView.context, itemView.iv_option3_evaluation, arrayOfOptions[2]?.second)
                    loadVideoImageThumbnail(itemView.context, itemView.iv_option4_evaluation, arrayOfOptions[3]?.second)

                    val transparentBlackColor = Color.parseColor("#80000000")

                    itemView.tv_select_video_option_1.setOnClickListener {
                        itemView.iv_option1_evaluation.setBackgroundColor(transparentBlackColor)
                        listener.selectPos(arrayOfOptions[0], position)
                    }

                    itemView.tv_select_video_option_2.setOnClickListener {
                        itemView.iv_option2_evaluation.setBackgroundColor(transparentBlackColor)
                        listener.selectPos(arrayOfOptions[1], position)
                    }

                    itemView.tv_select_video_option_3.setOnClickListener {
                        itemView.iv_option3_evaluation.setBackgroundColor(transparentBlackColor)
                        listener.selectPos(arrayOfOptions[2], position)
                    }

                    itemView.tv_select_video_option_4.setOnClickListener {
                        itemView.iv_option4_evaluation.setBackgroundColor(transparentBlackColor)
                        listener.selectPos(arrayOfOptions[3], position)
                    }

                    itemView.iv_option1_evaluation.setOnClickListener {
                        val videoViewFragment = VideoViewFragment()
                        videoViewFragment.setFileUrl(arrayOfOptions[0]?.second)
                        videoViewFragment.show((itemView.context as Activity).fragmentManager,
                                VideoViewFragment::class.java.simpleName)
                    }

                    itemView.iv_option2_evaluation.setOnClickListener {
                        val videoViewFragment = VideoViewFragment()
                        videoViewFragment.setFileUrl(arrayOfOptions[1]?.second)
                        videoViewFragment.show((itemView.context as Activity).fragmentManager,
                                VideoViewFragment::class.java.simpleName)
                    }

                    itemView.iv_option3_evaluation.setOnClickListener {
                        val videoViewFragment = VideoViewFragment()
                        videoViewFragment.setFileUrl(arrayOfOptions[2]?.second)
                        videoViewFragment.show((itemView.context as Activity).fragmentManager,
                                VideoViewFragment::class.java.simpleName)
                    }

                    itemView.iv_option4_evaluation.setOnClickListener {
                        val videoViewFragment = VideoViewFragment()
                        videoViewFragment.setFileUrl(arrayOfOptions[3]?.second)
                        videoViewFragment.show((itemView.context as Activity).fragmentManager,
                                VideoViewFragment::class.java.simpleName)
                    }

                } else {
                    clickStates[position] = false
                    itemView.tv_question_evaluation.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0)
                    itemView.ll_options.visibility = View.GONE
                }
            }
        }

        private fun makeTextViewBackgroundTransparent(tvOption1: TextView, tvOption2: TextView, tvOption3: TextView, tvOption4: TextView) {
            tvOption1.setBackgroundColor(Color.TRANSPARENT)
            tvOption2.setBackgroundColor(Color.TRANSPARENT)
            tvOption3.setBackgroundColor(Color.TRANSPARENT)
            tvOption4.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}

