package com.example.broadcastConsole.grid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.broadcastConsole.R
import kotlinx.android.synthetic.main.table_empty.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.table_empty)
        ConstraintOnly.addView(Terminal().termView(this))
    }
}
