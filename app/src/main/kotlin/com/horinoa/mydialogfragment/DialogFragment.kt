package com.horinoa.mydialogfragment

import android.app.*
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.io.Serializable

/**
 * Created by horinoA on 2017/01/13.
 */

val TITLE = "title"
val MESSE = "messe"
val SINGLEITEMS = "singleitems"
val LISTENER = "listener"


class ClickListener : Serializable {
    var okClickListner: DialogInterface.OnClickListener? = null
    var noClickListner: DialogInterface.OnClickListener? = null
    var neutralClickListner: DialogInterface.OnClickListener? = null
    var listSingleClick : DialogInterface.OnClickListener? = null
}

class DialogFragment {

    fun OkAlertShow(activity: Activity, titel: Int, messe: Int,callback:() -> Unit){
        val args = Bundle()
        args.putInt(TITLE,R.string.end_messe_title)
        args.putInt(MESSE,R.string.end_messe_messe)
        val clicklistener = ClickListener()
        clicklistener.okClickListner = DialogInterface.OnClickListener { dialog, i ->
            callback.invoke()
            dialog.dismiss()
        }
        args.putSerializable(LISTENER,clicklistener as Serializable)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.show(activity.fragmentManager,"OKALERT")
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
        mydialogfragment.show(activity.fragmentManager,"OKCANCEL")
    }

    fun OkLaterCancelAlertShow(activity:Activity,titel: Int, messe: Int, callback: (Boolean?) -> Unit) {
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
        clicklistener.neutralClickListner = DialogInterface.OnClickListener { dialog, i ->
            callback(null)
            dialog.dismiss()
        }
        args.putSerializable(LISTENER,clicklistener as Serializable)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.show(activity.fragmentManager,"OKCANCEL")
    }

    fun ListAlertShow(activity:Activity, titel: Int, list:Array<String> ,callback: (Int) -> Unit){
        val args = Bundle()
        args.putInt(TITLE,R.string.end_messe_title)
        args.putStringArray(SINGLEITEMS,list)
        val clicklistener = ClickListener()
        clicklistener.listSingleClick = DialogInterface.OnClickListener { dialog, i ->
            callback(i)
            dialog.dismiss()
        }
        args.putSerializable(LISTENER,clicklistener as Serializable)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.show(activity.fragmentManager,"LIST")
    }


}

open class MyDialogFragment : DialogFragment() {

    init { }

    companion object{
        fun newInstance(getArgs:Bundle): MyDialogFragment {
            val dialogfragment = MyDialogFragment()
            dialogfragment.arguments = getArgs
            return dialogfragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val listerner = arguments.getSerializable(LISTENER) as ClickListener

        if(arguments.containsKey(TITLE)){ builder.setTitle(getString(arguments.getInt(TITLE)))}
        if(arguments.containsKey(MESSE)){builder.setMessage(getString(arguments.getInt(MESSE)))}
        if(arguments.containsKey(SINGLEITEMS)){
            builder.setItems(arguments.getStringArray(SINGLEITEMS),listerner.listSingleClick)
        }

        val okString = listerner.neutralClickListner?.let{"Yes"} ?: "OK"
        val noString = listerner.neutralClickListner?.let{"No"} ?: "Cancel"
        listerner.okClickListner?.let {builder.setPositiveButton(okString, listerner.okClickListner ) }
        listerner.noClickListner?.let {builder.setNegativeButton(noString, listerner.noClickListner ) }
        listerner.neutralClickListner?.let {builder.setNeutralButton("Later",listerner.neutralClickListner) }


        return builder.create()
    }

}


