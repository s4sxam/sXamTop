plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android") // #8 DI
    // id("com.google.gms.google-services") // #50 Uncomment when google-services.json is added
}

android {
    namespace = "com.sxam.sxamtop"
    compileSdk = 35
    // ... defaultConfig remains same

    buildTypes {
        release {
            isMinifyEnabled = true // #5 Fixed
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures { compose = true; buildConfig = true } // #41 BuildConfig
    // ...
}

dependencies {
    // ... existing dependencies
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.hilt:hilt-work:1.2.0")
    ksp("androidx.hilt:hilt-compiler:1.2.0")

    // Auth Prep (#44)
    // implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    // implementation("com.google.android.gms:play-services-auth:21.2.0")
}