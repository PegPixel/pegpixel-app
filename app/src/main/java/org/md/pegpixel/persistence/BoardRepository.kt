package org.md.pegpixel.persistence

import android.content.Context
import kotlinx.coroutines.*
import org.md.pegpixel.pegboard.Pegboard

class BoardRepository(context: Context){
    private val instance = PegpixelDatabase.getInstance(context)

    fun save(pegboard: Pegboard) {
        GlobalScope.launch(Dispatchers.IO) {
                instance.pegboardDao().insert(PersistedPegboardConverter.createFrom(pegboard))
            }
        }

    fun load(name: String): Deferred<Pegboard?> {
        return GlobalScope.async(Dispatchers.IO) {
            val persistedPegboard = instance.pegboardDao().loadByName(name)
            PersistedPegboardConverter.createFrom(persistedPegboard)
        }
    }
}