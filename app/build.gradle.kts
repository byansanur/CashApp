plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.byinc.cashapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.byinc.cashapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.add("cash")

    productFlavors {
        create("development") {
            dimension = "cash"
            applicationId = ConfigurationDev.applicationId
            versionCode = ConfigurationDev.versionCode
            versionName = ConfigurationDev.versionName
            resValue("string", "app_name", ConfigurationDev.appName)
        }
        create("production") {
            dimension = "cash"
            applicationId = ConfigurationProd.applicationId
            versionCode = ConfigurationProd.versionCode
            versionName = ConfigurationProd.versionName
            resValue("string", "app_name", ConfigurationProd.appName)
        }
    }

    signingConfigs {
        create("prodKey") {
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = true
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs["prodKey"]
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
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
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.13.0-alpha01")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}