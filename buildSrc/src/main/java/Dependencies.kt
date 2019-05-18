object ApplicationId {
    val id = "com.juvetic.calcio"
}

object Modules {
    // for future modules implementation (dynamic features)
}

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

object Versions {
    val gradle = "3.4.0"
    val kotlin = "1.3.31"

    val anko = "0.10.8"

    val compileSdk = 28
    val minSdk = 21
    val targetSdk = 28

    val support = "1.0.0"
    val material = "1.0.0-rc01"

    val constraintLayout = "1.1.3"

    val retrofit = "2.5.0"

    val jUnit = "4.12"
    val mockWebServer = "3.14.0"
    val testRunner = "1.1.0"
    val testRules = "1.1.0"
    val espresso = "3.1.0"
}

object BuildPlugins {
    val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val androidApplication = "com.android.application"
    val kotlinAndroid = "kotlin-android"
    val kotlinAndroidExtensions = "kotlin-android-extensions"
    val kotlinKapt = "kotlin-kapt"
}

object SupportLibraries {
    val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"
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
    val espressoIdling = "androidx.test.espresso:espresso-idling-resource:3.1.1"
    val mockitoCore = "org.mockito:mockito-core:2.27.0"
    val mockitoInline = "org.mockito:mockito-inline:2.27.0"
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    val ktx = "androidx.core:core-ktx:1.2.0-alpha01"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitAdapterRxJava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    val circleImageView = "de.hdodenhof:circleimageview:3.0.0"

    val stetho = "com.facebook.stetho:stetho:1.5.1"
    val stethoOkhttp = "com.facebook.stetho:stetho-okhttp3:1.5.0"

    val calligraphy = "io.github.inflationx:calligraphy3:3.1.0"
    val viewpump = "io.github.inflationx:viewpump:1.0.0"
    val leakCanary = "com.squareup.leakcanary:leakcanary-android-no-op:1.6.2"

    val glide = "com.github.bumptech.glide:glide:4.9.0"
    val glideCompiler = "com.github.bumptech.glide:compiler:4.9.0"

    val anko = "org.jetbrains.anko:anko-sdk25:${Versions.anko}"
    val ankoCommons = "org.jetbrains.anko:anko-commons:${Versions.anko}"
    val ankoAppCompat = "org.jetbrains.anko:anko-appcompat-v7:${Versions.anko}"
    val ankoConstraintLayout = "org.jetbrains.anko:anko-constraint-layout:${Versions.anko}"
    val ankoDesign = "org.jetbrains.anko:anko-design:${Versions.anko}"
    val ankoRecyclerView = "org.jetbrains.anko:anko-recyclerview-v7:${Versions.anko}"
    val ankoSqlite = "org.jetbrains.anko:anko-sqlite:${Versions.anko}"
}