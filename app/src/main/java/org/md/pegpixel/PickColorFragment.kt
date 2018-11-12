package org.md.pegpixel

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.flask.colorpicker.ColorPickerView



class PickColorFragment : DialogFragment() {

    interface SelectedColorListener {
        fun handleSelectedColor(pegViewId: Int, selectedColor: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val inflate = inflater.inflate(R.layout.color_picker, container, false)
        val colorPicker: ColorPickerView = inflate.findViewById(R.id.colorPicker) as ColorPickerView

        colorPicker.addOnColorSelectedListener{selectedColor ->
            val pegViewId = arguments.getInt("pegViewId")
            val listener  =  activity as SelectedColorListener
            listener.handleSelectedColor(pegViewId, selectedColor)
            handleSelectedColor(selectedColor)
            this.dismiss()
        }
        return inflate
    }

    var handleSelectedColor: (Int) -> Unit = { _ -> Unit}
}