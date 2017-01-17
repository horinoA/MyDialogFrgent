package com.horinoa.mydialogfragment

import android.app.FragmentManager
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val okAlert:Button by lazy { findViewById(R.id.okAlertButtom) as Button }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        okAlert.setOnClickListener{
            val dialogfragment = DialogFragment()
            dialogfragment.OkAlertShow(this,R.string.end_messe_title,R.string.end_messe_messe,
                    {it ->
                        if(it == true){
                            Log.v("TAG","true")
                        }else{
                            Log.v("TAG","false")
                        }
                    }
            )
        }

    }
}

