package com.rigil.scenarios.evaluation

import android.os.Bundle
import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
import com.rigil.scenarios.R
import com.rigil.scenarios.common.GameScreenActivity
import com.rigil.scenarios.common.ScenariosQuizDataSourceModel
import kotlinx.android.synthetic.main.fragment_trivia_evaluation.*

class EvaluationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trivia_evaluation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        iv_home.setOnClickListener {
//            requireActivity().onBackPressed()
//        }
//        rv_evaluation.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//        val data = (requireActivity() as
//                GameScreenActivity).getAllDataRecords() as Array<ScenariosQuizDataSourceModel.Record>
//
//        val originalAnswers = arrayOfNulls<Pair<String?, String?>>(data.size)
//        val givenAnswers = arrayOfNulls<Pair<String?, String?>>(data.size)
//        val questions = arrayOfNulls<Pair<String?, String?>>(data.size)
        var score = 0
//        for (i in 0 until data.size) {
//            val dataAtIndex = data[i].fields
//            val originalAnswer = Pair(dataAtIndex.Answer, dataAtIndex.AnswerAttachment?.get(0)?.url)
//            originalAnswers[i] = originalAnswer
//            questions[i] = Pair(dataAtIndex.Question, dataAtIndex.attachment?.get(0)?.url)
//        }
     //   val optionInstance = data[0].fields.WrongAttachment1?.get(0)?.url
//        if (optionInstance?.isAVideoFile() == true) {
//            rv_evaluation.adapter = QuizEvaluationVideoAdapter(data,
//                    object : QuizEvaluationVideoAdapter.SelectImagePositionListener {
//                        override fun selectPos(answer: Pair<String?, String?>?, position: Int) {
//                            givenAnswers[position] = answer
//                            if (originalAnswers[position] == answer) score++
//                        }
//                    })
//        } else {
//            rv_evaluation.adapter = QuizEvaluationImageAdapter(data,
//                    object : QuizEvaluationImageAdapter.SelectImagePositionListener {
//                        override fun selectPos(answer: Pair<String?, String?>?, position: Int) {
//                            givenAnswers[position] = answer
//                            if (originalAnswers[position] == answer) score++
//                        }
//                    })
//        }
//        btn_game_options_submit.setOnClickListener {
//            saveAnswersAndNavigateToFinalScore(questions, originalAnswers, givenAnswers, score)
//        }
    }

    // sends attempted questions, answers, given answers and score to final score screen
    private fun saveAnswersAndNavigateToFinalScore(questions: Array<Pair<String?, String?>?>,
                                                   answers: Array<Pair<String?, String?>?>,
                                                   givenAnswers: Array<Pair<String?, String?>?>, score: Int) {
//        val finalScoreEvaluation = FinalScoreEvaluation()
//        val bundle = Bundle()
//        bundle.putSerializable("questions", questions)
//        bundle.putSerializable("answers", answers)
//        bundle.putSerializable("givenAnswers", givenAnswers)
//        bundle.putInt("score", score)
//
//        finalScoreEvaluation.arguments = bundle
//        requireActivity().supportFragmentManager
//                .beginTransaction().remove(this).commit()
//        requireActivity().supportFragmentManager
//                .beginTransaction().setCustomAnimations(com.rigil.mogcorelib.R.anim.slide_in_bottom, com.rigil.mogcorelib.R.anim.slide_out_bottom,
//                        com.rigil.mogcorelib.R.anim.slide_in_bottom, com.rigil.mogcorelib.R.anim.slide_out_bottom)
//                .add((requireActivity() as GameScreenActivity).getFragmentViewId(), finalScoreEvaluation)
//                .addToBackStack(null)
//                .commit()
    }
}