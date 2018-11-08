package org.md.pegpixel.persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface PegboardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pegboardEntity: PegboardEntity)

    @Query("SELECT * FROM pegboardentity WHERE name = :name LIMIT 1")
    fun loadByName(name: String): PegboardEntity

}
