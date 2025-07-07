import java.util.Properties
import java.io.FileInputStream

val secrets = Properties()
file(rootProject.file("secrets.properties")).inputStream().use {
    secrets.load(it)
}

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "1.9.22"
}

android {
    namespace = "com.example.proiect_dsdm"
    compileSdk = 34

    buildFeatures{
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.proiect_dsdm"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "SERVER_NAME", "\"${secrets["SERVER_NAME"]}\"")
        buildConfigField("String", "DATABASE_NAME", "\"${secrets["DATABASE_NAME"]}\"")

        buildConfigField("String", "PASSWORD", "\"${secrets["PASSWORD"]}\"")
        buildConfigField("String", "USERNAME", "\"${secrets["USERNAME"]}\"")
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.core)
    implementation(libs.room.runtime)
    implementation(libs.google.services)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.recyclerview)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    annotationProcessor(libs.androidx.room.compiler)
    implementation("com.microsoft.sqlserver:mssql-jdbc:9.4.0.jre11")
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("at.favre.lib:bcrypt:0.10.2")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

}