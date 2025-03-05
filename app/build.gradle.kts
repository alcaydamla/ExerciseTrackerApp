plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.damlaalcay_hw2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.damlaalcay_hw2"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {
    // Room Bağımlılıkları
    implementation("androidx.room:room-runtime:2.6.0")
    implementation(libs.androidx.material3.android)
    kapt("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    // Diğer Bağımlılıklar
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Test Bağımlılıkları
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ( "com.squareup.picasso:picasso:2.8")

    //For Glide library to get images from server with https protocol
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")

    //STEP1: Include retrofit and converter
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Alternative converters: Moshi Converter
    //implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Alternative converters: Kotlinx Serialization Converter
    //implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    //Alternative converters: Jackson Converter
    //implementation ("com.squareup.retrofit2:converter-jackson:2.9.0")


}
