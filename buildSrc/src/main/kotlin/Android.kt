import org.gradle.api.JavaVersion

object Android {
    object CompileOptions {
        val sourceCompatibility = JavaVersion.VERSION_1_8
        val targetCompatibility = JavaVersion.VERSION_1_8
    }

    object DefaultConfig {
        const val targetSdk = 33
        const val compileSdk = 33
        const val minSdk = 25
        const val versionCode = 1
        const val versionName = "1.0"
        const val applicationId = "com.example.viewer"
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    object BuildFeatures {
        const val dataBindingState = true
        const val viewBindingState = true
    }

    object KotlinOptions {
        const val jvmTarget = "1.8"
    }
}