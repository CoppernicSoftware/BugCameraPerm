package fr.coppernic.template.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.coppernic.app.askey.bug.camera.R
import fr.coppernic.template.App

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.appComponents.inject(this)
    }
}
