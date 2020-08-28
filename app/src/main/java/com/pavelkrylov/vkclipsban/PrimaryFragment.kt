package com.pavelkrylov.vkclipsban

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.IntProperty
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.primary_fragment.*

class PrimaryFragment : Fragment(R.layout.primary_fragment) {

    private val blockEnabledLD = MutableLiveData<Boolean>()

    private val prefs = App.INSTANCE.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun getBlockEnabled() = prefs.getBoolean(BLOCK_ENABLED_KEY, true)

    private fun setBlockEnabled(enabled: Boolean) =
        prefs.edit().putBoolean(BLOCK_ENABLED_KEY, enabled).apply()

    init {
        blockEnabledLD.value = getBlockEnabled()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blockEnabledLD.observe(this, { setBlockEnabled(it) })
    }

    var colorAnimator: ObjectAnimator? = null

    @Suppress("USELESS_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blockEnabledLD.observe(viewLifecycleOwner, {
            TransitionManager.beginDelayedTransition(view as ViewGroup, AutoTransition())
            toggle.isChecked = it
            if (it) {
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
                colorAnimator?.start()
            } else {
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
                colorAnimator?.start()
            }
        })
        toggle.setOnCheckedChangeListener { _, isChecked -> blockEnabledLD.value = isChecked }
    }

    companion object {
        private const val PREFS_NAME = "settings"
        private const val BLOCK_ENABLED_KEY = "block_enabled"
    }
}