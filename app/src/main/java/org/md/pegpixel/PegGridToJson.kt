package org.md.pegpixel

import com.google.gson.Gson

class PegGridToJson {
    companion object {
        fun createJsonFor(allPegs: List<PegView>): String {
            return Gson().toJson(allPegs.map { PegAsJson.fromViewObject(it)})
        }
    }
}

data class PegAsJson(
        val x: Int,
        val y: Int,
        val selected: Boolean) {
    companion object {
        fun fromViewObject(pegView: PegView): PegAsJson {
            return PegAsJson(
                x = pegView.xIndex,
                y = pegView.yIndex,
                selected = pegView.selected
            )
        }
    }
}