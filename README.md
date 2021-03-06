![locsimple](https://github.com/evitwilly/locsimple/blob/master/assets/logo.png)

# locsimple

Android library that allows you to determine your location in some lines!

Benefits:
- automatic processing of permissions
- processing of enabling and disabling the transfer of location data on the device
- android 12 support
- you can customize the location service more precisely
- additional callbacks
- support for Huawei devices without Google services

My lib consists of three modules:
- location-google: location using Google services (only works on devices with google services)
- location-huawei: location using Huawei services
- location-google-huawei: uses Google services if available otherwise Huawei services

Use one of them.

## Get started

#### Define my location:

    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val locationText = findViewById<AppCompatTextView>(R.id.location_text)

            // use LocationSimpleSingle to determine the location once (in most cases)
            val simple = LocationSimpleSingle(this)
            
            // location is android.Location object
            simple.defineLocation { location ->
                locationText.text = "${location.latitude}\n${location.longitude}"
            }
        }
    }

#### Listen location changes:

    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val locationText = findViewById<AppCompatTextView>(R.id.location_text)
            
            // use LocationSimpleMultiple to listen to a new location (only foreground)
            val simple = LocationSimpleMultiple(this)
            
            // location is android.Location object
            simple.defineLocation { location ->
                locationText.text = "${location.latitude}\n${location.longitude}"
            }
        }
    }

#### Additional callbacks:

    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val locationText = findViewById<AppCompatTextView>(R.id.location_text)

            val simple = LocationSimpleSingle(this)

            simple.onPermissionDenied { 
                // permission is denied
            }

            simple.onLocationShutdown { 
                // location mode is off
            }

            simple.defineLocation { location ->
                locationText.text = "${location.latitude}\n${location.longitude}"
            }
        }
    }


#### Customizing the location service:

     class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val locationText = findViewById<AppCompatTextView>(R.id.location_text)

            // use NeededLocationPermission to indicate an exact or inaccurate location (on android 12 is ignored)
            // use LocationRequestSettings to customize the location service
            val simple = LocationSimpleMultiple(this, NeededLocationPermission.EXACTLY, LocationRequestSettings()
                .changedInterval(5000L)
                .changedFastestInterval(1000L)
                .changedPriority(LocationPriority.HIGH_ACCURACY))

            simple.defineLocation { location ->
                locationText.text = "${location.latitude}\n${location.longitude}"
            }
        }
    }


Learn more about <code>LocationRequestSettings</code> options <a href="https://developer.android.com/training/location/change-location-settings">see link</a>

## Download

I don't know how to add my lib to the maven repository yet, so you can only download the source code and follow the video instructions:

[![demo](https://img.youtube.com/vi/y5Cgzd7hink/0.jpg)](https://www.youtube.com/watch?v=y5Cgzd7hink)

