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
            return PegAsJson(
                    x = peg.columnIndex,
                    y = peg.rowIndex,
                    s = if (peg.selected) "t" else "f",
                    r = Color.red(peg.color),
                    g = Color.green(peg.color),
                    b = Color.blue(peg.color)
            )
        }

        private fun as255Int(floatColor: Float?): Int?{
            return floatColor?.times(255)?.toInt()
        }
    }
}
