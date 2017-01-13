package com.horinoa.mydialogfragment

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle

/**
 * Created by horinoA on 2017/01/13.
 */
open class DialogFragment : DialogFragment() {

    companion object{
        fun newInstance(title:Int){

        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}