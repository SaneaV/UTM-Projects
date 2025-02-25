buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.agp)
        classpath(libs.kotlinGradlePlugin)
    }
}

tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}