package org.md.pegpixel.ui

class PegboardView(private val allPegViews: List<PegView>) {
    fun updateColorForNonSelectedPegs(selectedColor: Int) {
        allPegViews
                .filter { !it.button.isChecked }
                .forEach { it.updatePegColor(selectedColor) }
    }

    fun findById(pegViewId: Int): PegView? {
        return allPegViews.find {
            it.button.id == pegViewId
        }
    }

}