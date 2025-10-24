import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("androidx.room")
}
ksp {
    arg("option_name", "option_value")
    // other options...
}
android {
    namespace = "com.example.athousandcourses"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.athousandcourses"
        minSdk = 28
        targetSdk = 36
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

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    //room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
//делегаты
    implementation (libs.adapterdelegates4)
    implementation (libs.adapterdelegates4.kotlin.dsl)
        //ретрофит и json
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

//навигация
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

        //даггер
    implementation(libs.dagger)
    ksp (libs.dagger.compiler)
    ksp (libs.dagger.android.processor)
    implementation(libs.javax.inject)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(project(":domain"))
    implementation(project(":data"))
}