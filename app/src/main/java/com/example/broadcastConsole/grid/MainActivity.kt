package com.example.broadcastConsole.grid

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TextView
import com.example.broadcastConsole.R
import kotlinx.android.synthetic.main.table.*
import kotlinx.android.synthetic.main.table.view.*
import com.esotericsoftware.tablelayout.*
fun LOG(message: String) = Log.w("LOG", message)

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table)

        // Keep your code clean by creating widgets separate from layout.

//        val textView = TextView(applicationContext)
//        textView.text = "a".toString()
//        textView.setTextColor(Color.WHITE)
//        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
//        textView.setPadding(0, 0, 0, 0)
//        textView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD)
//        val table = Table() // does not work
//        table.add(textView)              // Row 0, column 0.
//        table.add(textView).width(100f)    // Row 0, column 1.
//        table.row()                       // Move to next row.
//        table.add(textView)           // Row 1, column 0.
//        table.add(textView).width(100f) // Row 1, column 1.
//        Constraint.addView(table)

//        Constraint.post {
//            LOG("actual height of Constraint: ${Constraint.height}")
//            GridBuilder(applicationContext, Constraint, TableLayout).construct("prep")
//        }
    }
}
