package com.example.broadcastConsole.p

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.broadcastConsole.R

class RecyclerAdapter(Height: Int, gridLayoutManager: GridLayoutManager) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {
    val Height = Height
    var gridLayoutManager = gridLayoutManager

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerHolder {
        // Inflate some layout
        val inflated = LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_item, viewGroup, false)
        val holder = RecyclerHolder(inflated)

        // Span controlled by layout manager, height controlled by us. Using GridLayoutManager.
        // Set height as half of parent height, use span of existing layout manager
        val height = viewGroup.height / Height
        inflated.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)

        return holder
    }

    override fun onBindViewHolder(viewHolder: RecyclerHolder, i: Int) {
        // Maybe bind here
        val textView = viewHolder.textView
        textView.text = "a"
        textView.setTextColor(Color.WHITE)
        textView.setAutoSizeTextTypeUniformWithConfiguration(1, 500, 1, TypedValue.COMPLEX_UNIT_SP)
//        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
        textView.setPadding(0, 0, 0, 0)
        textView.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL)
    }

    override fun getItemCount(): Int {
        return this.Height * gridLayoutManager.spanCount // H*W
    }

    class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView
        init {
            textView = itemView.findViewById(R.id.TEXTVIEW)
        }
    }
}
