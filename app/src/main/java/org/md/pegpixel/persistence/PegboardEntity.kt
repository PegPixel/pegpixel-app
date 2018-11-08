package org.md.pegpixel.persistence

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.md.pegpixel.pegboard.Peg
import org.md.pegpixel.pegboard.Pegboard

@Entity
data class PegboardEntity(
    @PrimaryKey
    val name: String,
    val pegs: List<PersistedPeg>
)

@Entity
data class PersistedPeg(
    @PrimaryKey
    val columnIndex: Int,
    @PrimaryKey
    val rowIndex: Int,
    val selected: Boolean,
    val color: Int
)


class PersistedPegboardConverter {
    companion object {
        fun createFrom(pegboard: Pegboard): PegboardEntity {
            return PegboardEntity(
                    name = pegboard.name,
                    pegs = pegboard.pegs.map {
                        PersistedPeg(
                                columnIndex = it.columnIndex,
                                rowIndex = it.rowIndex,
                                selected = it.selected,
                                color = it.color
                        )
                    }
            )
        }
        fun createFrom(pegboardEntity: PegboardEntity): Pegboard {
            return Pegboard(
                    name = pegboardEntity.name,
                    pegs = pegboardEntity.pegs.map {
                        Peg(
                                columnIndex = it.columnIndex,
                                rowIndex = it.rowIndex,
                                selected = it.selected,
                                color = it.color
                        )
                    }
            )
        }

    }
}