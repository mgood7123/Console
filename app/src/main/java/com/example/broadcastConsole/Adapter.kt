//package com.example.broadcastConsole
//
//import android.content.Context
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.example.broadcastConsole.Adapter.ViewHolder
//
//class Adapter(list: ArrayList<String>, private val context: Context) : RecyclerView.Adapter<ViewHolder>() {
//
//    private var list = ArrayList<String>()
//
//    init {
//        this.list = list
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.content_console, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.textView.setText(list.get(position))
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textView: TextView
//
//        init {
//            textView = itemView.findViewById(R.id.textview)
//        }
//    }
//}