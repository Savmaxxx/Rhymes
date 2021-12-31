package com.max.rhymes.animation

import android.animation.ObjectAnimator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import android.animation.AnimatorSet
import android.animation.ValueAnimator.INFINITE
import android.annotation.SuppressLint
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView


class MyAnimation {
    private lateinit var animatorPreloader: ObjectAnimator
    fun setRotateAnimationSheet(constraintLayout: ConstraintLayout) {
        val animatorRotateSheet =
            ObjectAnimator.ofFloat(constraintLayout, "rotation", 0.0f, 0.8f, -0.8f, 0.0f)
        animatorRotateSheet.interpolator = LinearInterpolator()
        animatorRotateSheet.duration = 400
        animatorRotateSheet.start()
    }

    @SuppressLint("Recycle")
    fun setRotateAnimation(buttonSearchRhymes: Button, imageView: ImageView) {
        imageView.visibility = VISIBLE
        val animatorClickButtonSearchRhymes = ObjectAnimator.ofFloat(
            buttonSearchRhymes,
            "rotation",
            0.0f,
            20.0f,
            -20.0f,
            15.0f,
            -15.0f,
            10.0f,
            -10.0f,
            5.0f,
            -5.0f,
            0.0f
        )
        animatorClickButtonSearchRhymes.interpolator = LinearInterpolator()
        animatorClickButtonSearchRhymes.duration = 1000
        animatorPreloader = ObjectAnimator.ofFloat(
            imageView,
            "rotation",
            0.0f, 360.0f
        )
        animatorPreloader.interpolator = LinearInterpolator()
        animatorPreloader.repeatCount = INFINITE
        animatorPreloader.duration = 1000
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animatorClickButtonSearchRhymes, animatorPreloader)
        animatorSet.start()
    }

    fun cancelAnimatorPreloader(imageView: ImageView) {
        imageView.visibility = INVISIBLE
        animatorPreloader.cancel()
    }

    fun startAnimatorPreloader(imageView: ImageView) {
        imageView.visibility = VISIBLE
        animatorPreloader.start()
    }

}