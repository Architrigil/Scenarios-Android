package com.rigil.scenarios.review

import android.os.Bundle
import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
//import androidx.recyclerview.widget.PagerSnapHelper
import com.rigil.scenarios.R
import com.rigil.scenarios.common.GameScreenActivity
import com.rigil.scenarios.common.ScenariosQuizDataSourceModel
import kotlinx.android.synthetic.main.fragment_trivia_review.*


class ReviewFragment : Fragment() {
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_trivia_review, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        iv_review_home.setOnClickListener {
//            requireActivity().onBackPressed()
//        }
//        val llm = LinearLayoutManager(requireContext(), LinearLayout.HORIZONTAL, false)
//        rv_review.layoutManager = llm
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(rv_review)
//
//        val data = (requireActivity() as
//                GameScreenActivity).getAllDataRecords()!!
//        val flipped = arrayOfNulls<Boolean>(data.size)
//        for (i in 0 until data.size) {
//            flipped[i] = false
//        }
//        tv_slide_count.text = "1/${data.size}"
//
//        data as Array<ScenariosQuizDataSourceModel.Record>
//        rv_review.adapter = QuizReviewAdapter(flipped, data)
//
//        rv_review.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    tv_slide_count.text = "${llm.findFirstVisibleItemPosition() + 1}/${data.size}"
//                }
//            }
//        })
//
//    }


}