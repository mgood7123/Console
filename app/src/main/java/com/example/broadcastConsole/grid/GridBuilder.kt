package com.example.broadcastConsole.grid

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM

class GridBuilder(
    a: Context,
    c: ConstraintLayout,
    t: TableLayout? = null
) {
    val appcon = a
    val constraint = c
    val t = t
    val columnMax = 2
    var column = 1
    var line = 1
    inner class Row() {
        val column = mutableListOf<TextView>()
        val row = TableRow(appcon)
    }
    val row = mutableListOf<Row>()
    val currentRow: TableRow? = null

    fun buildTableView(): TableLayout {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
//        val layer2 = android.widget.ScrollView(appcon)
        val layer3 = android.widget.LinearLayout(appcon)
        constraint.addView(layer3, layoutParams)
//        layer2.addView(layer3, layoutParams)
        val layer4 = android.widget.TableLayout(appcon)
        layer3.addView(layer4, layoutParams)
        return layer4
    }

    fun addColumn(row: Row, ch: Char) {
        val textView = TextView(appcon)
        textView.text = ch.toString()
        textView.setTextColor(Color.WHITE)
        textView.setAutoSizeTextTypeWithDefaults(AUTO_SIZE_TEXT_TYPE_UNIFORM)
        textView.setPadding(0, 0, 0, 0)
        textView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD)
        row.column.add(textView)
        column++
    }
    fun addRow() {
        row.add(Row())
        column = 1
        line++
    }

    fun build(table: TableLayout): TableLayout {
        // 1. give the top-level TableLayout node in the XML
        // file an android:weightSum value corresponding
        // to the number of rows in the layout. If the
        // layout has 3 rows, then set android:weightSum=3.
        table.weightSum = line.toFloat()
        row.forEach {
            it.column.forEach { text ->
                // 3. set the android:layout_height and
                // android:layout_width of the items
                // inside the TableRow elements
                // ( in my case, a Button element ), to
                // "fill_parent."
                it.row.addView(text, TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT
                ).also {
                    it.weight = line.toFloat()
                })
            }
            table.addView(it.row)
        }
        return table
    }

    fun construct(text: String): TableLayout {
        addRow()
        for (i in 0..text.length - 1) {
            if (column > columnMax) addRow()
            if (text[i] == '\n') {
                addRow()
            }
            else addColumn(row[line-2], text[i])
        }
        val table = if (t != null) {
            t
        } else {
            buildTableView()
        }
        return build(table)
    }
}