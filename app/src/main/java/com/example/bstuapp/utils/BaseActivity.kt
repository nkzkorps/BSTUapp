package com.example.bstuapp.utils

import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.bstuapp.R


open class BaseActivity : AppCompatActivity() {

    companion object {
        private const val MAX_CLICK_TIME = 1000
    }

    fun setupToolbar(
        inflatedView: View,
        title: String?,
        homeIconId: Int,
        backButtonEnabled: Boolean,
        toolbarNavigationButtonClickListener: View.OnClickListener?
    ) {
        setupToolbar(
            inflatedView,
            title,
            homeIconId.let { ContextCompat.getDrawable(inflatedView.context, it) },
            backButtonEnabled,
            toolbarNavigationButtonClickListener
        )
    }

    fun setupToolbar(
        inflatedView: View,
        title: String?,
        homeIconId: Drawable?,
        backButtonEnabled: Boolean,
        toolbarNavigationButtonClickListener: View.OnClickListener?
    ) {
        val toolbar = inflatedView.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title ?: ""
        toolbar.navigationIcon = homeIconId
        toolbar.setContentInsetsRelative(
            toolbar.contentInsetLeft,
            toolbar.contentInsetRight
        )
        val clickListener = object : View.OnClickListener {

            private var lastClickTime = 0L

            override fun onClick(v: View?) {
                if (SystemClock.elapsedRealtime() - lastClickTime < MAX_CLICK_TIME) {
                    return
                }
                lastClickTime = SystemClock.elapsedRealtime()
                toolbarNavigationButtonClickListener?.onClick(v)
            }
        }

        toolbar.setNavigationOnClickListener(clickListener)
    }
}