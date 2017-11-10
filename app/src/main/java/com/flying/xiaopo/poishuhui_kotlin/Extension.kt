package com.flying.xiaopo.poishuhui_kotlin

import android.content.Context
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import okhttp3.Request

/**
 * @author wupanjie
 */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun View.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.snackbar(messageRes: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, messageRes, duration).show()
}

fun Any.log(level: Level, message: String) {
    val levelInt = Level.valueOf(level.toString()).level
    when (levelInt) {
        1 -> Log.v(this.javaClass.simpleName, message)
        2 -> Log.d(this.javaClass.simpleName, message)
        3 -> Log.i(this.javaClass.simpleName, message)
        4 -> Log.w(this.javaClass.simpleName, message)
        5 -> Log.e(this.javaClass.simpleName, message)
        else -> Log.v(this.javaClass.simpleName, message)
    }
}

enum class Level(val level: Int) {
    V(1), D(2), I(3), W(4), E(5)
}

fun getHtml(url: String): String {
    val client = OkClient.instance
    val request = Request.Builder()
            .url(url)
            .build()

    val response = client.newCall(request).execute()
    return response.body()?.string() ?: ""
}

fun WebView.load(html: String) {
    this.loadDataWithBaseURL("http://ishuhui.net/", html, "text/html", "charset=utf-8", null)
}

fun ImageView.loadUrl(url: String) {
    Picasso.with(this.context)
            .load(url)
            .into(this)
}