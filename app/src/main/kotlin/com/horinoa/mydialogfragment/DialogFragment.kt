package com.horinoa.mydialogfragment

import android.app.*
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import java.io.Serializable

/**
 * Created by horinoA on 2017/01/13.
 */

val TITLE = "title"
val MESSE = "messe"
val ITEMS = "items"
val LISTENER = "listener"


class ClickListener : Serializable {
    var okClickListner: DialogInterface.OnClickListener? = null
    var noClickListner: DialogInterface.OnClickListener? = null
    var neutralClickListner: DialogInterface.OnClickListener? = null
}

class DialogFragment {

    fun OkAlertShow(activity:Activity,titel: Int, messe:Int){
        val args = Bundle()
        args.putInt(TITLE,R.string.end_messe_title)
        args.putInt(MESSE,R.string.end_messe_messe)
        val clicklistener = ClickListener()
        clicklistener.okClickListner = DialogInterface.OnClickListener { dialog, i ->
            dialog.dismiss()
        }
        args.putSerializable(LISTENER,clicklistener as Serializable)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.show(activity.fragmentManager,"")
    }


    fun OkCancelAlertShow(activity:Activity,titel: Int, messe: Int, callback: (Boolean) -> Unit) {
        val args = Bundle()
        args.putInt(TITLE,R.string.end_messe_title)
        args.putInt(MESSE,R.string.end_messe_messe)
        val clicklistener = ClickListener()
        clicklistener.okClickListner = DialogInterface.OnClickListener { dialog, i ->
            callback(true)
            dialog.dismiss()
        }
        clicklistener.noClickListner =  DialogInterface.OnClickListener { dialog, i ->
            callback(false)
            dialog.dismiss()
        }
        args.putSerializable(LISTENER,clicklistener as Serializable)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.show(activity.fragmentManager,"")
    }


}

open class MyDialogFragment : DialogFragment() {

    companion object{
        fun newInstance(getargs:Bundle): MyDialogFragment {
            val dialogfragment = MyDialogFragment()
            dialogfragment.arguments = getargs
            return dialogfragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val listerner = arguments.getSerializable(LISTENER) as ClickListener
        listerner.okClickListner?.let {builder.setPositiveButton("OK", listerner.okClickListner ) }
        listerner.noClickListner?.let {builder.setNegativeButton("Cancel", listerner.noClickListner ) }

        arguments.getInt(TITLE)?.let{builder.setTitle(getString(arguments.getInt(TITLE)))}
        arguments.getInt(MESSE)?.let{builder.setMessage(getString(arguments.getInt(MESSE)))}
        arguments.getInt(ITEMS)?.let{arguments.getStringArray(ITEMS)}
        return builder.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }


    override fun onDestroyView() {
        if (getDialog() != null && getRetainInstance()){
            getDialog().setDismissMessage(null)}
        super.onDestroyView()
    }

}


