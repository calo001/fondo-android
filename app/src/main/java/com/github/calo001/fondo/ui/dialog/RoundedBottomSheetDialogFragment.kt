package com.github.calo001.fondo.ui.dialog

import android.app.Dialog
import android.os.Bundle
import com.github.calo001.fondo.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class RoundedBottomSheetDialogFragment: BottomSheetDialogFragment() {
    override fun getTheme() : Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)
}