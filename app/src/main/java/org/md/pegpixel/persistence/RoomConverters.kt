package org.md.pegpixel.persistence

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class RoomConverters{
    @TypeConverter
    fun fromString(pegsAsString: String): List<PersistedPeg> {
        val listType = object : TypeToken<List<PersistedPeg>>() {}.type
        return Gson().fromJson(pegsAsString, listType)
    }
    @TypeConverter
    fun fromList(pegs: List<PersistedPeg>): String {
        val gson = Gson()
        return gson.toJson(pegs)
    }
}