# TestTask Application

Цей проєкт Android створено з використанням Jetpack Compose та сучасних бібліотек для побудови інтерфейсу користувача, управління навігацією, обробки HTTP-запитів та обробки зображень.

## Зміст
1. [Параметри конфігурації](#параметри-конфігурації)
2. [Залежності](#залежності)
3. [Поради щодо усунення несправностей](#поради-щодо-усунення-несправностей)

---

## Параметри конфігурації

Проєкт налаштовано в файлі `build.gradle` наступним чином:

```groovy
android {
    namespace = "com.korniienko.testtask"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.korniienko.testtask"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
```

### Опис параметрів

- **namespace** - унікальний простір імен для пакета додатка.
- **compileSdk** - рівень SDK, який використовується для компіляції проєкту.
- **minSdk** - мінімальний рівень SDK, який підтримується проєктом.
- **targetSdk** - цільовий рівень SDK для сумісності з новими версіями Android.
- **versionCode** та **versionName** - використовуються для визначення версії додатка.

### Як налаштувати

Ви можете налаштувати ці параметри безпосередньо в `build.gradle`, якщо потрібно змінити мінімальні або цільові рівні SDK, або змінити версію додатка.

---

## Залежності

Проєкт використовує декілька бібліотек для різних функціональних можливостей:

### Основні бібліотеки

1. **AndroidX Libraries** - для основних функцій та компонентів UI.
    - `androidx.core:core-ktx` - корисні розширення для Kotlin.
    - `androidx.lifecycle:lifecycle-runtime-ktx` - для управління життєвим циклом компонентів.
    - `androidx.activity:activity-compose` - для інтеграції Jetpack Compose з Activity.
    
2. **Jetpack Compose Libraries** - для побудови UI.
    - `androidx.compose.ui` - основний UI модуль.
    - `androidx.compose.material3` - компоненти Material Design 3.
    - `androidx.navigation:navigation-compose` - для навігації між екранами.

3. **Retrofit** - для виконання HTTP-запитів.
    - `retrofit` - для створення HTTP-запитів.
    - `converter-gson` - для конвертації JSON-відповідей в об'єкти Kotlin.
    - `logging-interceptor` - для логування HTTP-запитів.

4. **Coil** - для завантаження та відображення зображень.
    - `coil-compose` - інтеграція з Jetpack Compose для відображення зображень.

### Файл `dependencies` в Gradle

```kotlin
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.coil.kt.coil.compose)
    implementation(libs.androidx.navigation.compose)
    // Тестування
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
```

---

## Поради щодо усунення несправностей і типові проблеми

### 1. Проблеми з Gradle
   - Якщо проєкт не збирається через проблеми з Gradle, спробуйте виконати **Clean Project** і **Rebuild Project** в Android Studio.
   - Переконайтеся, що всі версії залежностей сумісні між собою.

### 2. Проблеми з навігацією
   - Якщо виникають помилки під час навігації, переконайтеся, що всі шляхи маршрутів (`route`) в навігаційному графі правильно вказані і відповідають маршрутам в `NavHost`.
   - Під час переходу на інший екран використовуйте `popUpTo` для очищення стека навігації, якщо це необхідно.

### 3. Проблеми з зависанням екрана під час навантаження зображень
   - Використовуйте бібліотеку Coil для завантаження зображень асинхронно.
   - Якщо зображення не завантажується, переконайтеся, що URL-адреса правильна і зображення доступне.

### 4. Неправильне відображення компонентів Material Design
   - Переконайтеся, що всі компоненти Material Design використовують версію Material Design 3 для сумісності.
   - Перевірте, що бібліотека `androidx.compose.material3` підключена в проєкті.

### 5. Підтримка старих версій Android
   - Якщо ваш проєкт повинен підтримувати більше пристроїв, розгляньте зниження `minSdk`. Зараз він встановлений на 26, але деякі функції Jetpack Compose можуть не підтримуватись на нижчих версіях.
