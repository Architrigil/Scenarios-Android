package com.rigil.scenarios.game

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.rigil.scenarios.*
import com.rigil.scenarios.common.VideoViewFragment
import com.rigil.scenarios.utils.isItVideoFile
import com.rigil.scenarios.utils.loadImageFromDb
import com.rigil.scenarios.utils.loadVideoImageThumbnail
import kotlinx.android.synthetic.main.row_question_options.view.*

class QuestionOptionsAdapter(private val optionsArray: Array<Pair<String?, String?>>,
                             private val timeForQuestion: Int,
                             private val gameTimer: MutableLiveData<Int>,
                             private val listener: QuestionOptionsAdapter.OptionsInterface) :
        RecyclerView.Adapter<QuestionOptionsAdapter.ViewHolder>() {

    private var isAnswerAttempted = false
    private var isToRemoveTile = true

    interface OptionsInterface {
        fun onClickOption(position: Int)
        fun onRemovedTile(position: Int)
        fun stopTimer(isToStopTimer: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionOptionsAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val inflatedView = layoutInflater.inflate(R.layout.row_question_options, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return optionsArray.size
    }

    override fun onBindViewHolder(holder: QuestionOptionsAdapter.ViewHolder, position: Int) {
        holder.bindItems(position, listener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(position: Int, listener: QuestionOptionsAdapter.OptionsInterface) {
            if ((position == 0) && (position % 2 == 0)) {
                val anim = AnimationUtils.loadAnimation(itemView.context, R.anim.slide_in_left)
                itemView.animation = anim
            } else {
                val anim = AnimationUtils.loadAnimation(itemView.context, R.anim.slide_in_right)
                itemView.animation = anim
            }
            val ivOption = itemView.iv_option_ll
            val tvOption = itemView.tv_option_ll
            val tvSelectVideo = itemView.tv_select_video_option
            itemView.ll_option.visibility = View.VISIBLE
            ivOption.visibility = View.VISIBLE
            val optionPair = optionsArray[position]
            if (optionPair.first != null) {
                tvOption.visibility = View.VISIBLE
                tvOption.text = optionPair.first
                ivOption.visibility = View.GONE
            }
            if (optionPair.second != null && optionPair.second != "") {
                val optionMediaUrl = optionPair.second ?: ""
                if (optionMediaUrl.contains("http") && !optionPair.second.isItVideoFile()) {
                    loadImageFromDb(optionPair.second, ivOption, itemView.context)
                } else if (optionMediaUrl.contains("http") && optionMediaUrl.isItVideoFile()) {
                    tvSelectVideo.visibility = View.VISIBLE
                    loadVideoImageThumbnail(itemView.context, ivOption, optionMediaUrl)
                }
            } else {
                ivOption.visibility = View.GONE
            }
            itemView.setOnClickListener {
                if (!optionPair.second.isItVideoFile()) {
                    isAnswerAttempted = true
                    listener.onClickOption(position)
                }
            }
            ivOption.setOnClickListener {
                if (optionPair.second.isItVideoFile()) {
                    isToRemoveTile = false
                    listener.stopTimer(true)
                    val videoViewFragment = VideoViewFragment()
                    videoViewFragment.setFileUrl(optionPair.second)
                    videoViewFragment.show((itemView.context as Activity).fragmentManager, VideoViewFragment::class.simpleName)

                } else {
                    isAnswerAttempted = true
                    listener.onClickOption(position)
                }
            }

            tvSelectVideo.setOnClickListener {
                isAnswerAttempted = true
                listener.onClickOption(position)
            }
//            if (hints) {
//                val handler = Handler()
//                val runnable = object : Runnable {
//                    override fun run() {
//                        val gameTimerValue = gameTimer.value!!
//                        for (i in 0 until itemCount - 1) {
//                            if (timeForQuestion - gameTimerValue == (i + 1) * (timeForQuestion / itemCount)) {
//                                removeOption(i)
//                                break
//                            }
//                        }
//                        handler.postDelayed(this, 100L)
//                    }
//                    // removed question-options from view if conditions are satisfied
//                    private fun removeOption(index: Int) {
//                        if (!isAnswerAttempted && position == index  && isToRemoveTile) {
//                            itemView.visibility = View.GONE
//                            listener.onRemovedTile(position)
//                        }
//                    }
//                }
//                handler.postDelayed(runnable, 1000L)
//            }
        }
    }
}