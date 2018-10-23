package org.md.pegpixel

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PegboardDao {
    @Insert
    fun insert(persistedPegboard: PersistedPegboard)

    @Query("SELECT * FROM persistedpegboard WHERE name = :name LIMIT 1")
    fun loadByName(name: String): PersistedPegboard

}
