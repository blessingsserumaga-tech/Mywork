# BB App Builder Demo

Native Android starter project built with Kotlin and the Android Gradle Plugin.

## Included
- BB-branded splash screen and launcher icon
- Home, Services, Notifications and Settings bottom navigation
- About page
- Persistent dark/light mode preference
- Responsive, scrollable Android layout
- No requested runtime permissions and no network access
- GitHub Actions workflow that builds and uploads a debug APK

## Build locally
Requirements: JDK 17, Android SDK Platform 35, Android Build Tools 35.x, and Gradle 8.9 or compatible with Android Gradle Plugin 8.7.3.

1. Open this folder in Android Studio, allow Gradle sync and install the requested SDK components.
2. Run `gradle :app:assembleDebug` (or use Android Studio's Build > Build APK(s)).
3. APK output: `app/build/outputs/apk/debug/app-debug.apk`.

## Build using GitHub Actions
1. Create a new GitHub repository.
2. Upload the contents of this project to the repository's `main` branch.
3. Open the repository's Actions tab and run **Build BB Android APK**, or push a commit.
4. When the workflow completes successfully, open its run and download the `BB-App-Builder-Demo-debug-apk` artifact. Extract `app-debug.apk`.

The GitHub workflow is configured but has not been executed from this environment. Therefore this source package does not claim to contain a compiled or tested APK.

## Rebrand this template
Update these values:
- App display name: `app/src/main/AndroidManifest.xml` (`android:label`)
- Package / namespace: `app/build.gradle.kts` and the Kotlin package/folder path
- Version: `versionCode` and `versionName` in `app/build.gradle.kts`
- Brand colours: `blue`, background/surface values in `MainActivity.kt`; splash/icon vector resources
- Screens and navigation: `showPage`, page functions and `buildNavigation` in `MainActivity.kt`
- App icon: `app/src/main/res/drawable/bb_icon.xml`

Keep the namespace, applicationId and Kotlin package aligned when changing the package name.
