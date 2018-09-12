package org.md.pegpixel

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class PegGridToJson {
    companion object {
        fun createJsonFor(allPegs: List<PegView>): String {
            return Gson().toJson(allPegs)

        }
    }
}