package org.md.pegpixel

import com.google.gson.Gson

class PegGridToJson {
    companion object {
        fun createJsonFor(allPegs: List<PegView>): String {
            return Gson().toJson(allPegs)
        }
    }
}