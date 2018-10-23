package org.md.pegpixel

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import android.arch.persistence.room.Room
import android.graphics.Color
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.md.pegpixel.pegboard.Peg
import org.md.pegpixel.pegboard.Pegboard
import org.md.pegpixel.persistence.PegboardDao
import org.md.pegpixel.persistence.PegpixelDatabase
import org.md.pegpixel.persistence.PersistedPegboardConverter


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
        val peg = Peg(1, 2, false, Color.RED)
        val pegs = arrayListOf(peg)
        val persistedPegboard = PersistedPegboardConverter.createFrom(Pegboard("new board", pegs))
        pegboardDao?.insert(persistedPegboard)
        val loadedPegboard = pegboardDao?.loadByName(persistedPegboard.name)
        val loadedPeg = loadedPegboard?.pegs?.first()
        assertThat(loadedPegboard?.name, `is`(persistedPegboard.name))
        assertThat(loadedPeg?.columnIndex, `is`(peg.columnIndex))
        assertThat(loadedPeg?.rowIndex, `is`(peg.rowIndex))
        assertThat(loadedPeg?.selected, `is`(peg.selected))
        assertThat(loadedPeg?.color, `is`(peg.color))
    }

    @After
    fun closeDb() {
        pegpixelDatabase?.close()
    }
}
