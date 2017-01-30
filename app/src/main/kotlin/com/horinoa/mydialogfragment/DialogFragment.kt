package com.horinoa.mydialogfragment

import android.app.*
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import java.io.Serializable



/**
 * Created by horinoA on 2017/01/13.
 */

val TITLE = "title"
val MESSE = "messe"
val ITEMS = "items"
val LISTENER = "listener"
val CHOOSESINGLE = "chooseItemInt"

class ClickListener : Serializable {
    var okClickListner: DialogInterface.OnClickListener? = null
    var noClickListner: DialogInterface.OnClickListener? = null
    var neutralClickListner: DialogInterface.OnClickListener? = null
    var listClick : DialogInterface.OnClickListener? = null
    var listSinglecallback : ((Int) -> Unit)? = null
    var listMulticallback : ((Array<Boolean>) -> Unit)? = null
}

class DialogFragment {

    fun OkAlertShow(activity: Activity, title: Int, messe: Int,callback:() -> Unit){
        val args = Bundle()
        args.putInt(TITLE,title)
        args.putInt(MESSE,messe)
        val clicklistener = ClickListener()
        clicklistener.okClickListner = DialogInterface.OnClickListener { dialog, i ->
            callback.invoke()
            dialog.dismiss()
        }
        args.putSerializable(LISTENER,clicklistener as Serializable)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.show(activity.fragmentManager,"OKALERT")
    }

    fun OkCancelAlertShow(activity:Activity,title: Int, messe: Int, callback: (Boolean) -> Unit) {
        val args = Bundle()
        args.putInt(TITLE,title)
        args.putInt(MESSE,messe)
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

    fun OkLaterCancelAlertShow(activity:Activity,title: Int, messe: Int, callback: (Boolean?) -> Unit) {
        val args = Bundle()
        args.putInt(TITLE,title)
        args.putInt(MESSE,messe)
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

    fun ListAlertShow(activity:Activity, title: Int, list:Array<String> ,callback: (Int) -> Unit){
        val args = Bundle()
        args.putInt(TITLE,title)
        args.putStringArray(ITEMS,list)
        val clicklistener = ClickListener()
        clicklistener.listClick = DialogInterface.OnClickListener { dialog, i ->
            callback(i)
            dialog.dismiss()
        }
        args.putSerializable(LISTENER,clicklistener as Serializable)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.show(activity.fragmentManager,"LIST")
    }

    fun ListSingleAlertShow(activity:Activity,
                        title: Int,
                        list:Array<String> ,
                        callback: (Int) -> Unit){
        val args = Bundle()
        args.putInt(TITLE,title)
        args.putStringArray(ITEMS,list)
        val clicklistener = ClickListener()
        clicklistener.listSinglecallback = callback
        args.putSerializable(LISTENER,clicklistener as Serializable)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.show(activity.fragmentManager,"LISTSINGLE")
    }

    fun ListMultiAlertShow(activity:Activity,
                        title: Int,
                        list:Array<String> ,
                        callback: (Array<Boolean>) -> Unit){
        val args = Bundle()
        args.putInt(TITLE,title)
        args.putStringArray(ITEMS,list)
        val clicklistener = ClickListener()
        clicklistener.listMulticallback = callback
        args.putSerializable(LISTENER,clicklistener as Serializable)
        val mydialogfragment = MyDialogFragment.newInstance(args)
        mydialogfragment.show(activity.fragmentManager,"LISTMULTHI")
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

        if(arguments.containsKey(TITLE)){builder.setTitle(getString(arguments.getInt(TITLE)))}
        if(arguments.containsKey(MESSE)){builder.setMessage(getString(arguments.getInt(MESSE)))}
        if(arguments.containsKey(ITEMS)){
            builder.setItems(arguments.getStringArray(ITEMS),listerner.listClick)
        }

        val okString = listerner.neutralClickListner?.let{"Yes"} ?: "OK"
        val noString = listerner.neutralClickListner?.let{"No"} ?: "Cancel"
        listerner.okClickListner?.let {builder.setPositiveButton(okString, listerner.okClickListner ) }
        listerner.noClickListner?.let {builder.setNegativeButton(noString, listerner.noClickListner ) }
        listerner.neutralClickListner?.let{builder.setNeutralButton("Later",listerner.neutralClickListner)}

        listerner.listSinglecallback?.let{
            val callback = listerner.listSinglecallback as (Int) -> Unit
            builder.setSingleChoiceItems(arguments.getStringArray(ITEMS), 0,
                    DialogInterface.OnClickListener(){dialog,i ->
                        arguments.putInt(CHOOSESINGLE,i)
                    })
            builder.setPositiveButton(okString,DialogInterface.OnClickListener { dialog, i ->
                callback(arguments.getInt(CHOOSESINGLE))
                dialog.dismiss()
            })
            builder.setNegativeButton(noString,DialogInterface.OnClickListener { dialog, i ->
                dialog.dismiss()
            })
        }

        listerner.listMulticallback?.let{
            val callback = listerner.listMulticallback as (Array<Boolean>) -> Unit
            val checkArry = BooleanArray(arguments.getStringArray(ITEMS).size,{false})

            builder.setMultiChoiceItems(arguments.getStringArray(ITEMS),null,
                    DialogInterface.OnMultiChoiceClickListener { dialog, i, isChecked ->
                        if (isChecked)checkArry[i] = true else checkArry[i] = false
                    }
            )


        }

        return builder.create()
    }

}


