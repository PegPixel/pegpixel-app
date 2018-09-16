package org.md.pegpixel

import android.graphics.Color
import com.google.gson.Gson

class PegGridToJson {
    companion object {
        fun createJsonFor(pegView: PegView): String {
            val jsonString = Gson().toJson(PegAsJson.fromViewObject(pegView))
            return "$jsonString\n"
        }
    }
}

data class PegAsJson(
        val x: Int,
        val y: Int,
        val s: String,
        val r: Int?,
        val g: Int?,
        val b: Int?) {
    companion object {
        fun fromViewObject(pegView: PegView): PegAsJson {
            val color = pegView.color?.let {
                Color.valueOf(it)
            }
            return PegAsJson(
                x = pegView.xIndex,
                y = pegView.yIndex,
                s = if (pegView.selected) "t" else "f",
                r = color?.red()?.toInt(),
                g = color?.green()?.toInt(),
                b = color?.blue()?.toInt()

            )
        }
    }
}
