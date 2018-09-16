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
                r = as255Int(color?.red()),
                g = as255Int(color?.green()),
                b = as255Int(color?.blue())
            )
        }

        private fun as255Int(floatColor: Float?): Int?{
            return floatColor?.times(255)?.toInt()
        }
    }
}
