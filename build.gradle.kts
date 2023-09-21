buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.buildGradle)
        classpath(libs.kotlinGradlePlugin)
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}