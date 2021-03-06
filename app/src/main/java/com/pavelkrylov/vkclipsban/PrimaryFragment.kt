package com.pavelkrylov.vkclipsban

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.IntProperty
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.primary_fragment.*

class PrimaryFragment : Fragment(R.layout.primary_fragment) {

    private val blockEnabledLD = MutableLiveData<Boolean>()

    private var blockEnabled: Boolean by SettingsManager::blockEnabled

    init {
        blockEnabledLD.value = blockEnabled
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blockEnabledLD.observe(this, { blockEnabled = it })
    }

    var colorAnimator: ObjectAnimator? = null

    @Suppress("USELESS_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blockEnabledLD.observe(viewLifecycleOwner, { blockEnabled ->
            TransitionManager.beginDelayedTransition(view as ViewGroup, AutoTransition().apply {
                duration = SWITCH_ANIMATION_DURATION / 3L
                ordering = TransitionSet.ORDERING_SEQUENTIAL
            })
            toggle.isChecked = blockEnabled
            if (blockEnabled) {
                disabledImage.visibility = View.INVISIBLE
                enabledImage.visibility = View.VISIBLE

                stateLabel.setText(R.string.block_on)
                colorAnimator?.pause()
                colorAnimator = ObjectAnimator.ofArgb(
                    stateLabel, object : IntProperty<TextView>("textColor") {
                        override fun get(tv: TextView) = tv.currentTextColor
                        override fun setValue(tv: TextView, value: Int) = tv.setTextColor(value)
                    },
                    stateLabel.currentTextColor,
                    resources.getColor(android.R.color.holo_green_dark, null)
                )
                colorAnimator?.duration = SWITCH_ANIMATION_DURATION
                colorAnimator?.start()
            } else {
                disabledImage.visibility = View.VISIBLE
                enabledImage.visibility = View.INVISIBLE

                stateLabel.setText(R.string.block_off)
                colorAnimator?.pause()
                colorAnimator = ObjectAnimator.ofArgb(
                    stateLabel, object : IntProperty<TextView>("textColor") {
                        override fun get(tv: TextView) = tv.currentTextColor
                        override fun setValue(tv: TextView, value: Int) = tv.setTextColor(value)
                    },
                    stateLabel.currentTextColor,
                    resources.getColor(android.R.color.holo_red_dark, null)
                )
                colorAnimator?.duration = SWITCH_ANIMATION_DURATION
                colorAnimator?.start()
            }
        })
        toggle.setOnCheckedChangeListener { _, isChecked -> blockEnabledLD.value = isChecked }
    }

    companion object {
        private const val SWITCH_ANIMATION_DURATION = 250L
    }
}