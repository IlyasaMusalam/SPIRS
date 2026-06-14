plugins {
    alias(libs.plugins.android.application)
}

android {

    namespace = "com.example.spirs"

    compileSdk = 36

    defaultConfig {

        applicationId = "com.example.spirs"

        minSdk = 24
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {

            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // AndroidX
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // RecyclerView
    implementation(
        "androidx.recyclerview:recyclerview:1.3.2"
    )

    // CardView
    implementation(
        "androidx.cardview:cardview:1.0.0"
    )

    // GPS Location
    implementation(
        "com.google.android.gms:play-services-location:21.3.0"
    )

    // Gson
    implementation(
        "com.google.code.gson:gson:2.10.1"
    )

    // Lifecycle
    implementation(
        "androidx.lifecycle:lifecycle-runtime-ktx:2.8.7"
    )

    // Unit Test
    testImplementation(libs.junit)

    // Android Test
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}