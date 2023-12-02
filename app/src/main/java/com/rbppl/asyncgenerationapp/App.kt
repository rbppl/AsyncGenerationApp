package com.rbppl.asyncgenerationapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rbppl.asyncgenerationapp.ui.fragment.ItemFragment
class App : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ItemFragment())
                .commit()
        }
    }
}