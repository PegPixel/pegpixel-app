package org.md.pegpixel

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.flask.colorpicker.ColorPickerView
import android.view.Window.FEATURE_NO_TITLE




class PickColorFragment : DialogFragment() {

    interface SelectedColorListener {
        fun handleSelectedColor(color: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val inflate = inflater.inflate(R.layout.color_picker, container, false)
        val colorPicker: ColorPickerView = inflate.findViewById(R.id.colorPicker)

        colorPicker.addOnColorSelectedListener{selectedColor ->
            Log.i("STUFF", "color selected")
            handleSelectedColor(selectedColor)
            Log.i("STUFF", "dismissing....")
            this.dismiss()
        }
        return inflate
    }

    var handleSelectedColor: (Int) -> Unit = { _ -> Unit}
}