package org.md.pegpixel

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import android.arch.persistence.room.Room
import org.hamcrest.Matchers.`is`


@RunWith(AndroidJUnit4::class)
class PegboardDaoTest {

    private var pegboardDao: PegboardDao? = null
    private var pegpixelDatabase: PegpixelDatabase? = null

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getTargetContext()
        pegpixelDatabase = Room.inMemoryDatabaseBuilder(context, PegpixelDatabase::class.java).build()
        pegboardDao = pegpixelDatabase?.pegboardDao()
    }

    @Test
    fun savesAndLoadsPegboard() {
        val persistedPegboard = PersistedPegboard("new board")
        pegboardDao?.insert(persistedPegboard)
        val loadedPegboard = pegboardDao?.loadByName(persistedPegboard.name)

        assertThat(loadedPegboard?.name, `is`(persistedPegboard.name))
    }
}
