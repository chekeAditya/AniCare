# AniCare

![anicare](https://user-images.githubusercontent.com/81345503/143842933-ff711494-34d8-4e47-880b-639f4cebc9b6.png)

# 🔗Open-Source Library

* [MVVM-Architecture](https://developer.android.com/jetpack/guide)
* [Viewmodel-LiveData](https://developer.android.com/codelabs/basic-android-kotlin-training-livedata#0)
* [Room Database](https://developer.android.com/reference/android/arch/persistence/room/RoomDatabase)
* [Dependency Injection-Hilt](https://developer.android.com/training/dependency-injection)
* [Coroutines](https://developer.android.com/kotlin/coroutines)
* [Lottie Library](https://github.com/airbnb/lottie-android)
* [Android Chart](https://github.com/PhilJay/MPAndroidChart)
* [Camerax](https://developer.android.com/training/camerax)
* [Firebase-Ml-Object-Detection](https://firebase.google.com/docs/ml-kit/object-detection)
* [Firebase-Image Labeling](https://firebase.google.com/docs/ml-kit/label-images)

# Things we used while making this application
* MVVM-Architecture
* Room Database
* Firebase object Detection and Image Labeling
* Camerax
* Dependency Injection-Hilt
* Navigation Graph
* Android Charts
* Bolt-Iot Temperature sensor

# Tech Stack ✨

* [Android Studio](https://developer.android.com/studio)
* [Kotlin](https://kotlinlang.org/)
* [MVVM-Architecture](https://developer.android.com/jetpack/guide)
* [Github](https://github.com/)


# Dependencies 

    implementation platform('com.google.firebase:firebase-bom:29.0.0')
    implementation 'com.google.firebase:firebase-storage-ktx'


    //navigation
    implementation 'androidx.navigation:navigation-ui-ktx:2.3.5'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.3.5'

    //firebase ml
    //firebase ml image labeling
    implementation 'com.google.firebase:firebase-core:20.0.0'
    implementation 'com.google.firebase:firebase-ml-vision-image-label-model:20.0.2'
    implementation 'com.google.mlkit:image-labeling:17.0.5'

    //firebase ml object detection
    implementation 'com.google.mlkit:object-detection:16.2.7'

    //camerax
    def camerax_version = "1.0.2"
    // CameraX core library using camera2 implementation
    implementation "androidx.camera:camera-camera2:$camerax_version"
    // CameraX Lifecycle Library
    implementation "androidx.camera:camera-lifecycle:$camerax_version"
    // CameraX View class
    implementation "androidx.camera:camera-view:1.0.0-alpha31"

    //gif
    implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.23'
    //Glide
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    //circularImage
    implementation 'de.hdodenhof:circleimageview:3.1.0'

    //chart library
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'

    //coroutine
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"

    //livedata
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0"

    //livdata
    // Lifecycle components
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$rootProject.lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$rootProject.lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$rootProject.lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-common-java8:$rootProject.lifecycle_version"

    // Coroutines
    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:$rootProject.coroutines"

    //lotie library
    def lottieVersion = "3.4.0"
    implementation "com.airbnb.android:lottie:$lottieVersion"

# Clone this Repo To Your System Using Android Studio✨

* Step 1: Open your Android Studio then go to the File > New > Project from Version Control as shown in the below image.
* Step 2: After clicking on the Project from Version Control a pop-up screen will arise like below. In the Version control choose Git from the drop-down menu.
* Step 3: Then at last paste the link in the URL and choose your Directory. Click on the Clone button and you are done.

# Clone this Repo To Your System Using GitBash✨

* Open Git Bash

* If Git is not already installed, it is super simple. Just go to the Git Download Folder and follow the instructions.

* Go to the current directory where you want the cloned directory to be added.

* To do this, input cd and add your folder location. You can add the folder location by dragging the folder to Git bash.

* Go to the page of the repository that you want to clone

* Click on “Clone or download” and copy the URL.

* Use the git clone command along with the copied URL from earlier. $ https://github.com/chekeAditya/Anicaremals.git

* Press Enter. $ git clone https://github.com/chekeAditya/Anicaremals.git Cloning into Git … remote: Counting objects: 13, done. remote: Compressing objects: 100% (13/13), done. remove: Total 13 (delta 1), reused 0 (delta 1) Unpacking objects: 100% (13/13), done.

Congratulations, you have created your first local clone from your remote Github repository.

Open Android Studio. Go to File > New > Project From Version Control. Copy the link of this repositary. Paste the link in Url Box of Android Studio window and click on "Clone" button.
