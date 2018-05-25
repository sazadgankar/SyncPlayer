package com.sazadgankar.syncplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URI


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        const val REQUEST_LOCATION_CODE = 1
        const val REQUEST_ENABLE_BT = 2
    }

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var activeGroupsArray: ArrayList<String> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var code: String
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Web View
        web_view.visibility = View.GONE
        if (!PreferenceManager.getDefaultSharedPreferences(applicationContext).contains("USER_CODE")) {
            web_view.visibility = View.VISIBLE
            main_activity_content.visibility = View.GONE
            nav_view.visibility = View.GONE

            val settings = web_view.settings
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            web_view.webViewClient = object : WebViewClient() {
                @Suppress("OverridingDeprecatedMember")
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    val uri = URI.create(url)
                    if (uri.scheme == "syncplayer") {
                        // get code
                        code = uri.query.substring(5)
                        PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().
                                putString("USER_CODE", code).apply()
                        Log.i("URL", code)
                        web_view.visibility = View.GONE
                        main_activity_content.visibility = View.VISIBLE
                        nav_view.visibility = View.VISIBLE
                    }
                    @Suppress("DEPRECATION")
                    return super.shouldOverrideUrlLoading(view, url)
                }
            }
            web_view.loadUrl("https://accounts.spotify.com/en/authorize?response_type=code&" +
                    "client_id=05dfb34804c24f8d90b96d5818ff283e&redirect_uri=SyncPlayer:%2F%2Fcallback")
        }
        //Recycler view
        viewManager = LinearLayoutManager(this)
        activeGroupsArray = arrayListOf("Asqar balaa", "kokab talaa")
        viewAdapter = GroupsRecyclerViewAdapter(activeGroupsArray)
        recyclerView = active_groups_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_CODE)
        } else {
            startDiscovery()
        }
    }

    override fun onStop() {
        super.onStop()
        bluetoothAdapter?.cancelDiscovery()

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startDiscovery()
                } else {
                    Toast.makeText(this,
                            getString(R.string.need_location_permisssion),
                            Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun startDiscovery() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this,
                    getString(R.string.need_bluetooth),
                    Toast.LENGTH_SHORT).show()
            finish()
        }
        bluetoothAdapter?.let {
            if (it.isEnabled) {
                it.startDiscovery()
            } else {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_ENABLE_BT -> if (resultCode == Activity.RESULT_OK) {
                bluetoothAdapter?.startDiscovery()
            } else {
                Toast.makeText(this,
                        getString(R.string.need_bluetooth),
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.new_group -> {
                val intent = Intent(this, ControllerActivity::class.java)
                startActivity(intent)

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
