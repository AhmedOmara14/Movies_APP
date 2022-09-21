package com.task.common

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object HelperClass {
    fun showToast(context: Context?, message: String?) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}