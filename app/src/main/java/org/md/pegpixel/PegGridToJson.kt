package org.md.pegpixel

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
        val s: String) {
    companion object {
        fun fromViewObject(pegView: PegView): PegAsJson {
            return PegAsJson(
                x = pegView.xIndex,
                y = pegView.yIndex,
                s = if (pegView.selected) "t" else "f"
            )
        }
    }
}
