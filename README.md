# locsimple

Android library that allows you to determine your location in some lines!

#### Example:

    class MainActivity : AppCompatActivity() {
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val locationText = findViewById<TextView>(R.id.location_text)

            // Step 1. create LocationSimple object
            val simple = LocationSimple(this)

            // Step 2. call defineLocaion() method and pass a callback to it  
            simple.defineLocation { location ->
            
                // Step 3. get Location object and use it
                locationText.text = "${location.latitude}\n${location.longitude}"
                
            }
            
        }
        
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
            implementation 'com.github.KiberneticWorm:locsimple:9f6f1a4ccf'
      }

