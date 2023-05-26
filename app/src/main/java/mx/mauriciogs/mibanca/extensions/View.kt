package mx.mauriciogs.mibanca.extensions

import android.view.View

fun View.hideOrShow(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}