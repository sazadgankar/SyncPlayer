package com.sazadgankar.syncplayer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_controller.*


class ControllerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        val newFragment = CreateGroupFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, newFragment)
        transaction.commit()

        cancel_text_view.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
