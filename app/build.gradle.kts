plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Android.DefaultConfig.compileSdk

    defaultConfig {
        applicationId = Android.DefaultConfig.applicationId
        minSdk = Android.DefaultConfig.minSdk
        targetSdk = Android.DefaultConfig.targetSdk
        versionCode = Android.DefaultConfig.versionCode
        versionName = Android.DefaultConfig.versionName

        testInstrumentationRunner = Android.DefaultConfig.testInstrumentationRunner
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }

    buildFeatures {
        dataBinding = Android.BuildFeatures.dataBindingState
        viewBinding = Android.BuildFeatures.viewBindingState
    }

    compileOptions {
        sourceCompatibility = Android.CompileOptions.sourceCompatibility
        targetCompatibility = Android.CompileOptions.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = Android.KotlinOptions.jvmTarget
    }
    namespace = "com.example.viewer"
}

dependencies {

    constraints {
        implementation(libs.kotlinStdlibJdk7)
        implementation(libs.kotlinStdlibJdk8)
    }

    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintLayout)
    implementation(libs.activityKtx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.esspressoCore)
}