plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.mobile.laboratory"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mobile.laboratory"
        minSdk = 34
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // Standard dependencies for Android
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // RecyclerView dependency
    implementation(libs.recyclerview)

    // Picasso for image loading
    implementation(libs.picasso)

    // Lombok for annotations (if you are using it)
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.jsoup)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}