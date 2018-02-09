package com.teamwork.sample.maicon.util.extension

import android.content.Context
import android.content.CursorLoader
import android.net.Uri
import android.os.Looper
import android.provider.MediaStore
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.widget.Toast

/**
 * Default short toast
 */
fun Context.toast(any: Any, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, any.toString(), duration).show()
}

/**
 * Default short toast
 */
fun Context.toast(@StringRes resString: Int, duration: Int = Toast.LENGTH_SHORT){
    toast(getString(resString), duration)
}

/**
 * Long duration toast
 */
fun Context.longToast(any: Any){
    toast(any.toString(), Toast.LENGTH_LONG)
}

/**
 * Long duration toast
 */
fun Context.longToast(@StringRes stringRes: Int){
    toast(getString(stringRes), Toast.LENGTH_LONG)
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT){
    context?.toast(message, duration)
}

fun Fragment.toast(@StringRes resString: Int, duration: Int = Toast.LENGTH_SHORT){
    context?.toast(getString(resString), duration)
}
fun Fragment.longToast(@StringRes stringRes: Int){
    context?.toast(getString(stringRes), Toast.LENGTH_LONG)
}


fun Context.getPath(contentUri: Uri): String {
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val result: String

    if (Looper.myLooper() == null) {
        Looper.prepare()
    }
    val cursorLoader = CursorLoader(
            this,
            contentUri, proj, null, null, null)
    val cursor = cursorLoader.loadInBackground()

    result = if (cursor != null) {
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        cursor.getString(column_index)
    } else {
        contentUri.path
    }

    return result
}

fun Context.getColorCompat(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

