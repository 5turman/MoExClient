package org.example.moex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
