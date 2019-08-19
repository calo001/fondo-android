package com.github.calo001.fondo.dialog

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.github.calo001.fondo.R

open class RoundedBottomSheetDialogFragment: BottomSheetDialogFragment() {
    override fun getTheme() : Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)
}