package com.deveshmittal.presentation.mvvm.tools

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.databinding.BindingAdapter
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import com.deveshmittal.presentation.mvvm.tools.ANIMATION_CONST.ANIMATION_ALPHA
import com.deveshmittal.presentation.mvvm.tools.ANIMATION_CONST.ANIMATION_SLID_UP
import com.deveshmittal.presentation.mvvm.tools.ANIMATION_CONST.INTER_ACELERATION
import java.util.*


object ANIMATION_CONST {
    val ANIMATION_ALPHA = 0b000001
    val ANIMATION_SLID_UP = 0b000010
    val INTER_ACELERATION = 0b000100
    val animations = listOf(ANIMATION_ALPHA, ANIMATION_SLID_UP)
}


@BindingAdapter("cv_visible", "cv_animated", "cv_animated_mask")
fun View.changeVisible(show: Boolean, animated: Boolean, mask: Int) {
    val targetVisibility = if (show) View.VISIBLE else View.GONE
    if (visibility != targetVisibility) {
        if (!animated)
            visibility = targetVisibility
        else {
            showWithAnimation(this, show, mask)
        }
    }
}

fun showWithAnimation(view: View, show: Boolean, animatedMask: Int) {
    val animation = generateAnimtaion(view, show, animatedMask)
    if ((INTER_ACELERATION and animatedMask) > 0)
        animation.interpolator = FastOutSlowInInterpolator()
    animation.duration = 600
    animation.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
            if (show) view.visibility = View.VISIBLE
        }

        override fun onAnimationEnd(animation: Animator?) {
            if (!show) view.visibility = View.GONE
        }
    })
    animation.start()
}


fun generateAnimtaion(view: View, show: Boolean, animatedMask: Int): ObjectAnimator {
    val animatinList = LinkedList<PropertyValuesHolder>()
    ANIMATION_CONST.animations
            .filter { mask -> (mask and animatedMask) > 0; }
            .forEach { mask ->
                when (mask) {
                    ANIMATION_ALPHA -> animatinList.add(
                            if (show) PropertyValuesHolder.ofFloat(View.ALPHA, 0.3f, 1f)
                            else PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f))
                    ANIMATION_SLID_UP -> animatinList.add(
                            if (show) PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, (view.parent as View).height.toFloat(), 0f)
                            else PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f, (view.parent as View).height.toFloat()))
                }
            }
    return ObjectAnimator.ofPropertyValuesHolder(view, *animatinList.toTypedArray())
}
