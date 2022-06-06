package com.blackview.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Context.gotoAct() {
    val intent = Intent(this, T::class.java)
    if (this is Application) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Context.gotoAct(bundle: Bundle) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle)
    if (this is Application) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}