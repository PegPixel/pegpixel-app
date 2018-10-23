package org.md.pegpixel

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class PersistedPegboard(
    @PrimaryKey
    val name: String
)