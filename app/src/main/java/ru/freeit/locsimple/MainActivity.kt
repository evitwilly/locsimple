package ru.freeit.locsimple

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.lib.LocationSimpleMultiple
import ru.freeit.lib.LocationSimpleSingle

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

        val simple = LocationSimpleSingle(this)

        simple.defineLocation { location ->
            locationText.text = "${location.latitude}\n${location.longitude}"
        }
    }
}