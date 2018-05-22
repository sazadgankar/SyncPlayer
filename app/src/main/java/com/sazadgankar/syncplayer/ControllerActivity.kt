package com.sazadgankar.syncplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_controller.*
import android.util.Log


class ControllerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val newFragment = CreateGroupFragment()
        val transaction =supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment)
        transaction.commit()
    }

}
