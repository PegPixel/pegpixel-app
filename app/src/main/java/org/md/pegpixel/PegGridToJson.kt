package org.md.pegpixel

import android.graphics.Color
import com.google.gson.Gson

class PegGridToJson {
    companion object {
        fun createJsonFor(peg: Peg): String {
            val jsonString = Gson().toJson(PegAsJson.fromViewObject(peg))
            return jsonString
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
        fun fromViewObject(peg: Peg): PegAsJson {
            val color = peg.color?.let {
                Color.valueOf(it)
            }
            return PegAsJson(
                x = peg.columnIndex,
                y = peg.rowIndex,
                s = if (peg.selected) "t" else "f",
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
