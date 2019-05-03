package com.rigil.scenarios.common

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plattysoft.leonids.ParticleSystem
import com.plattysoft.leonids.modifiers.ScaleModifier
import com.rigil.scenarios.R
import kotlinx.android.synthetic.main.fragment_question_animation.*
import java.util.*

class QuestionAnimationFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_question_animation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadQuestionMarkAnimation()
    }

    private fun loadQuestionMarkAnimation() {
        view_question_mark_animation_bg.post {
            val particleSystem: ParticleSystem = ParticleSystem(activity, 30, R.drawable.question_mark, 5000, view_question_mark_animation_bg.id).apply {
                setSpeedModuleAndAngleRange(0f, 0.03f, 0, 360)
                setInitialRotationRange(0, 360)
                addModifier(ScaleModifier(.05f, 0.11f, 0, 4000))
                emit(view_question_mark_animation_bg, 2)
            }
            val handler = Handler()
            val runnable: Runnable = object : Runnable {
                override fun run() {
                    if (view_question_mark_animation_bg != null) {
                        particleSystem.updateEmitPoint(Random().nextInt(view_question_mark_animation_bg.width), Random().nextInt(view_question_mark_animation_bg.height))
                        handler.postDelayed(this, 200)
                    } else return
                }
            }
            handler.post(runnable)
        }
    }
}