package com.pavelkrylov.vkclipsban

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(R.layout.main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyAccessibilityService.getStateLD().observe(this, {
            when (it!!) {
                MyAccessibilityService.STATE.IN_ACTIVE -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, AccessibilityEnableFragment())
                        .commit()
                }
                MyAccessibilityService.STATE.ACTIVE -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PrimaryFragment())
                        .commit()
                }
            }
        })
    }
}