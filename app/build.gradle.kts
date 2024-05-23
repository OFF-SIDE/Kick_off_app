

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.test.kick_off_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.test.kick_off_app"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // 당겨서 새로고침
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // GsonConverter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // okhttp
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    // glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    kapt("android.arch.lifecycle:compiler:1.1.1")
    kapt("com.github.bumptech.glide:compiler:4.16.0")
    // 카카오 로그인 모듈
    implementation("com.kakao.sdk:v2-all:2.20.1")
    // sandwich - retrofit 에러처리
    implementation("com.github.skydoves:sandwich:2.0.7")
    implementation("com.github.skydoves:sandwich-retrofit:2.0.7") // For Retrofit (Android)
    // circle image view
    implementation("de.hdodenhof:circleimageview:3.1.0")
    // viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    // retrofit 에러처리
    implementation("com.github.haroldadmin:NetworkResponseAdapter:5.0.0")
}