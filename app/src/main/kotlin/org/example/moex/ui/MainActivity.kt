package org.example.moex.ui

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import org.example.moex.R
import org.example.moex.ui.shares.SharesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.contentFrame, SharesFragment(), null)
                    .commit()
        }
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)

        if (toolbar != null) {
            val toggle = ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)

            drawerLayout.addDrawerListener(toggle)

            toggle.syncState()
        }
    }

}
