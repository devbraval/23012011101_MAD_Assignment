plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.a23012011101_mad_assignment1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.a23012011101_mad_assignment1"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding  = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.annotation:annotation:1.6.0")

    // ✅ Add this for NotificationCompat
    implementation("androidx.core:core:1.10.1")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.media:media:1.6.0") // Optional (if using MediaStyle notification)
}
