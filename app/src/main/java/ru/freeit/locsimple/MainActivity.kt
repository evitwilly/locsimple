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

        val frameLayout = FrameLayout(this)
        val locationText = AppCompatTextView(this)
        locationText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        locationText.setTextColor(Color.rgb(0, 0, 0))
        locationText.gravity = Gravity.CENTER
        locationText.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
        }
        frameLayout.addView(locationText)
        setContentView(frameLayout)

        val simple = LocationSimpleMultiple(this, NeededLocationPermission.EXACTLY, LocationRequestSettings()
            .changedPriority(LocationPriority.HIGH_ACCURACY)
            .changedInterval(5_000L)
            .changedWaitForAccurateLocation(false)
            .changedFastestInterval(1_000L))

        simple.defineLocation { location ->
            locationText.text = "${location.latitude}\n${location.longitude}"
        }
    }
}