package com.rigil.scenarios.evaluation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rigil.scenarios.R
import kotlinx.android.synthetic.main.fragment_final_score.*

class FinalScoreEvaluation : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_final_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_home_final_score.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val questions = arguments?.getSerializable("questions") as Array<Pair<String?, String?>>
        val answers = arguments?.getSerializable("answers") as Array<Pair<String?, String?>>
        val givenAnswers = arguments?.getSerializable("givenAnswers") as Array<Pair<String?, String?>?>
        val score = arguments?.getInt("score")

        tv_score_final_evaluation.text = "Score: $score/${answers?.size}"
        rv_final_score.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rv_final_score.adapter = FinalScoreAdapter(givenAnswers, answers, questions)
    }
}