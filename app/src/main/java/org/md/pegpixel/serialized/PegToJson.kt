package org.md.pegpixel.serialized

import android.graphics.Color
import com.google.gson.Gson
import org.md.pegpixel.pegboard.Peg

class PegGridToJson {
    companion object {
        fun createJsonFor(peg: Peg): String {
            return Gson().toJson(PegAsJson.fromViewObject(peg))
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
            val color = peg.color.let {
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
