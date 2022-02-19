![locsimple](https://github.com/evitwilly/locsimple/blob/master/images/logo.png)

# locsimple

Android library that allows you to determine your location in some lines!

#### Example 1. Simple using:

    class MainActivity : AppCompatActivity() {
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val locationText = findViewById<TextView>(R.id.location_text)

            // Step 1. create LocationSimpleSingle object
            val simple = LocationSimpleSingle(this)

            // Step 2. call defineLocation() method and pass a callback to it  
            simple.defineLocation { location ->
            
                // Step 3. get Location object and use it
                locationText.text = "${location.latitude}\n${location.longitude}"
                
            }
            
        }
        
    }

#### Example 2. Callbacks:

        val simple = LocationSimpleSingle(this)

        // user has rejected Location permission
        simple.onPermissionDenied {
            Log.d("TEST_", "permission is denied")
        }

        // user turned off location sharing
        simple.onLocationShutdown {
            Log.d("TEST_", "location is shutdown")
        }

        simple.defineLocation { location ->
            locationText.text = "${location.latitude}\n${location.longitude}"
        }


#### Example 3. Listen location changes

        // Step 1. create LocationSimpleMultiple object
        val simple = LocationSimpleMultiple(this)

        // Step 2. listen location changes
        simple.defineLocation { location ->
            locationText.text = "${location.latitude}\n${location.longitude}"
        }


## Download


**1.** Add it in your root build.gradle at the end of repositories:

      allprojects {
            repositories {
                  ...
                  maven { url 'https://jitpack.io' }
            }
      }

**2.** Add the dependency:

      dependencies {
            implementation 'com.github.KiberneticWorm:locsimple:25ca12fedd'
      }

