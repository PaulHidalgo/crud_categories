plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.services)
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.phidalgo.crudcategories"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.phidalgo.crudcategories"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.androidx.activity)

    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("io.insert-koin:koin-core:3.2.0")
    implementation("com.airbnb.android:lottie:5.2.0")
        // Retrofit for networking
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

        // Converter for JSON (if you're using Gson for parsing JSON)
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

        // Optional: OkHttp (a powerful HTTP client that Retrofit uses by default)
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")

        // Optional: Logging interceptor for OkHttp (for logging network requests)
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    // Glide for image loading
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    // Optional: Glide compiler for annotation processing (required if using GlideApp)
    kapt ("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}