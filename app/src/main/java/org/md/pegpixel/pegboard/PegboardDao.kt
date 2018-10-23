package org.md.pegpixel.pegboard

import android.arch.persistence.room.*

@Dao
interface PegboardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(persistedPegboard: PersistedPegboard)

    @Query("SELECT * FROM persistedpegboard WHERE name = :name LIMIT 1")
    fun loadByName(name: String): PersistedPegboard

}
