package org.example.moex.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.example.moex.ui.shares.SharesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, SharesFragment(), null)
                .commit()
        }
    }

}
