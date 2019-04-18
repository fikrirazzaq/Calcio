object ApplicationId {
    val id = "com.juvetic.mvvmarchitecture"
}

object Modules {
    // for future modules implementation (dynamic features)
}

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

object Versions {
    val gradle = "3.3.2"

    val compileSdk = 28
    val minSdk = 21
    val targetSdk = 28

    val kotlin = "1.3.30"

    val kotlinCoroutines = "1.2.0"

    val support = "1.0.0"
    val material = "1.0.0-rc01"

    val constraintLayout = "1.1.2"

    val retrofit = "2.5.0"

    val jUnit = "4.12"
    val mockWebServer = "3.14.0"
    val testRunner = "1.1.0"
    val testRules = "1.1.0"
    val espresso = "3.1.0"
}

object SupportLibraries {
    val appCompat = "androidx.appcompat:appcompat:${Versions.support}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Versions.support}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val design = "com.google.android.material:material:${Versions.material}"
}

object TestingLibraries {
    val jUnit = "junit:junit:${Versions.jUnit}"
    val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    val testRunner = "androidx.test:runner:${Versions.testRunner}"
    val testRules = "androidx.test:rules:${Versions.testRules}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitAdapterRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
}