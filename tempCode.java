defaultConfig {
        applicationId "com.hofmann"
        minSdkVersion 14
        targetSdkVersion 21
        versionCode 20005
        versionName "2.0.0"

        testApplicationId "com.hofmann.hofmann.test"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
        }
        productFlavors {
        pre {
        applicationIdSuffix ".pre"
        resValue "string", "app_name", "Hofmann PRE"
        buildConfigField "boolean", "K_TARGET_IS_PREPRODUCTION", "true"
        manifestPlaceholders = [
        appIcon: "@mipmap/ic_launcher_pre"
        ]
        }
        pro {
        applicationIdSuffix ".pro"
        resValue "string", "app_name", "Hofmann App"
        buildConfigField "boolean", "K_TARGET_IS_PREPRODUCTION", "false"
        manifestPlaceholders = [
        appIcon: "@mipmap/ic_launcher"
        ]
        }
        }
        if (project.hasProperty("HofmannPhone.signing")
        && new File(project.property("HofmannPhone.signing") + ".gradle").exists()) {
        apply from: project.property("HofmannPhone.signing") + ".gradle";
        }
        buildTypes {
        debug {
        applicationIdSuffix ".debug"
        }
        adhoc {
        // This copies the debuggable attribute and debug signing configurations.
        initWith debug
        applicationIdSuffix ".adhoc"
        //signingConfig android.buildTypes.debug.signingConfig
        }
        release {
        applicationIdSuffix ".hofmann"
        //FIXME: uncomment signingConfig signingConfigs.release
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
        }