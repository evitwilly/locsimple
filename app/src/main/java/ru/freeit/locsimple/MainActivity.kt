package ru.freeit.locsimple

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.location.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locationText = findViewById<AppCompatTextView>(R.id.location_text)

        val simple = LocationSimpleMultiple(this, NeededLocationPermission.EXACTLY, LocationRequestSettings()
            .changedInterval(5000L)
            .changedFastestInterval(1000L)
            .changedPriority(LocationPriority.HIGH_ACCURACY))

        simple.defineLocation { location ->
            locationText.text = "${location.latitude}\n${location.longitude}"
        }
    }
}