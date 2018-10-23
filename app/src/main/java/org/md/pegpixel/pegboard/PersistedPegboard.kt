package org.md.pegpixel.pegboard

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.md.pegpixel.Peg

@Entity
data class PersistedPegboard(
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
        fun createFrom(pegboard: Pegboard): PersistedPegboard {
            return PersistedPegboard(
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
        fun createFrom(pegboard: PersistedPegboard): Pegboard {
            return Pegboard(
                name = pegboard.name,
                pegs = pegboard.pegs.map{
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