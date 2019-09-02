package com.github.calo001.fondo.ui.main.fragment.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.calo001.fondo.R
import com.github.calo001.fondo.network.ApiError
import kotlinx.android.synthetic.main.fragment_error.*

class ErrorFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ) : View? {
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    fun setErrorMessage(error: ApiError) {
        when(error.code) {
            401 -> {
                txtMessage.text = resources.getString(R.string.auth_error)
                imgError.setImageDrawable(context?.let {
                    ContextCompat.getDrawable(it, R.drawable.ic_lock_alert)
                })
            }
            204 -> {
                txtMessage.text = error.message
                imgError.setImageDrawable(context?.let {
                    ContextCompat.getDrawable(it, R.drawable.ic_photos)
                })
            }
            205 -> {
                txtMessage.text = error.message
                imgError.setImageDrawable(context?.let {
                    ContextCompat.getDrawable(it, R.drawable.ic_clock_alert)
                })
            }
            else -> {
                txtMessage.text = "${error.code}: ${error.message}"
                imgError.setImageDrawable(context?.let {
                    ContextCompat.getDrawable(it, R.drawable.ic_wifi_lock)
                })
            }
        }
    }

    companion object {
        const val TAG = "ErrorFragment"
        @JvmStatic
        fun newInstance() =
            ErrorFragment()
    }
}