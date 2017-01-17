package com.horinoa.mydialogfragment

import android.app.*
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle

/**
 * Created by horinoA on 2017/01/13.
 */

val TITLE = "title"
val MESSE = "messe"


interface DialogFragmentImpl{
    fun OkAlertShow(titel:Int,messe:Int,callback:(Boolean)->Unit)
}

class DialogFragment {

    fun OkAlertShow(activity:Activity,titel: Int, messe: Int, callback: (Boolean) -> Unit) {
        val args = Bundle()
        args.putInt(TITLE,R.string.end_messe_title)
        args.putInt(MESSE,R.string.end_messe_messe)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.setOnOkClickListener(
                DialogInterface.OnClickListener { dialog, i ->
                    callback(true)
                    dialog.dismiss()
                })
        mydialogfragment.show(activity.fragmentManager,"")
    }

}

open class MyDialogFragment : DialogFragment() {

    var okClickListner : DialogInterface.OnClickListener? = null

    companion object{
        fun newInstance(getargs:Bundle): MyDialogFragment {
            val dialogfragment = MyDialogFragment()
            val args = Bundle()
            args.putInt(TITLE,getargs.getInt(TITLE))
            args.putInt(MESSE,getargs.getInt(MESSE))
            dialogfragment.arguments = args
            return dialogfragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle(getString(arguments.getInt(TITLE)))
                .setMessage(getString(arguments.getInt(MESSE)))
                .setPositiveButton("ok",this.okClickListner)
                .create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    fun setOnOkClickListener(listener:DialogInterface.OnClickListener) {
        this.okClickListner = listener
    }
}


