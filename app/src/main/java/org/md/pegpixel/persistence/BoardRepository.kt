package org.md.pegpixel.persistence

import org.md.pegpixel.pegboard.*
import android.content.Context
import java.util.concurrent.CompletableFuture


class BoardRepository(context: Context){
    private val instance = PegpixelDatabase.getInstance(context)

    fun save(pegboard: Pegboard) {
        CompletableFuture.runAsync {
            instance.pegboardDao().insert(PersistedPegboardConverter.createFrom(pegboard))
        }
    }

    fun load(name: String): CompletableFuture<Pegboard?> {
        return CompletableFuture.supplyAsync {
            val persistedPegboard = instance.pegboardDao().loadByName(name)
            PersistedPegboardConverter.createFrom(persistedPegboard)
        }
    }
}