package com.horinoa.mydialogfragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val okAlertText: TextView by lazy{ findViewById(R.id.okAlertTextView) as TextView }
    val okAlert: Button by lazy { findViewById(R.id.okAlertButtom) as Button }
    val okCancelText: TextView by lazy{ findViewById(R.id.okNoAlertTextView) as TextView}
    val okCancelAlert:Button by lazy {findViewById(R.id.okNoAlertButtom) as Button}
    val okLaterNoText: TextView by lazy{ findViewById(R.id.okLaterNoAlertTextView) as TextView}
    val okLaterNoAlert:Button by lazy {findViewById(R.id.okLaterNoAlertButtom) as Button}
    val listText: TextView by lazy{ findViewById(R.id.listAlertTextView) as TextView}
    val listAlert:Button by lazy {findViewById(R.id.listAlertButtom) as Button}
    val listSingleText: TextView by lazy{ findViewById(R.id.listSingleAlertTextView) as TextView}
    val listSingleAlert:Button by lazy {findViewById(R.id.listSingleAlertButtom) as Button}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main)
            val dialogfragment  = DialogFragment()
            okAlert.setOnClickListener {
                dialogfragment.OkAlertShow(
                        this,
                        R.string.end_messe_title,
                        R.string.end_messe_messe,
                        {okAlertText.setText("OK")}
                )
            }

            okCancelAlert.setOnClickListener {
                dialogfragment.OkCancelAlertShow(
                        this, R.string.end_messe_title,
                        R.string.end_messe_messe,
                        { it ->
                            if (it == true) {
                                okCancelText.setText("OK")
                            } else {
                                okCancelText.setText("NO")
                            }
                        }
                )
            }

            okLaterNoAlert.setOnClickListener {
                dialogfragment.OkLaterCancelAlertShow(
                        this,
                        R.string.end_messe_title,
                        R.string.end_messe_messe,
                        {it ->
                            when (it){
                                true ->{
                                    //okの場合
                                    okLaterNoText.setText("OK")
                                }
                                false -> {
                                    //Noの場合
                                    okLaterNoText.setText("NO")
                                }
                                else -> {
                                    //Laterの場合
                                    okLaterNoText.setText("Later")
                                }
                            }
                        }
                )
            }

            listAlert.setOnClickListener {
                val strlist = arrayOf("hogehohe","fugafuga","pakupaku")
                dialogfragment.ListAlertShow(this,
                        R.string.end_messe_title,
                        strlist,
                        {it ->
                            listText.setText(strlist[it])
                        }
                )
            }

            listSingleAlert.setOnClickListener {
                val strlist = arrayOf("teketeke","akuaku","hoihoi")
                dialogfragment.ListSingleAlertShow(this,
                        R.string.end_messe_title,
                        strlist,
                        {it ->
                            listSingleText.setText(strlist[it])
                        })
            }


        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }
}


