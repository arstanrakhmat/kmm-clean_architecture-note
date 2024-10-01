plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            dependencies {
                implementation(libs.sqldelightRuntime)
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.sqldelightDriverAndroid)
        }

        iosMain.dependencies {
            implementation(libs.sqldelightDriverIos)
        }
    }
}

sqldelight {
    database("NoteDatabase") {
        packageName = "com.example.noteappkmm.database"
        sourceFolders = listOf("sqldelight")
    }
}


android {
    namespace = "com.example.noteappkmm"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
