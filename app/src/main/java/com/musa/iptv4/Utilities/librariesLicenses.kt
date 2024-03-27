package com.musa.iptv4.Utilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.musa.iptv4.R

class librariesLicenses : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libraries_licenses)
        val license :WebView = findViewById(R.id.license_web)
        license.loadUrl("https://forotan1.github.io/")

    }
}