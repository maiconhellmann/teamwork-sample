# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/vinicius/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Kotlin
-dontwarn kotlin.**
-keepattributes EnclosingMethod
-keepattributes Signature
-keepattributes Annotation

# Retrofit rules
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8

# OkHttp rules
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# RxJava rules
# RxAndroid will soon ship with rules so this may not be needed in the future
# https://github.com/ReactiveX/RxAndroid/issues/219
-dontwarn sun.misc.Unsafe
-keep class rx.internal.util.unsafe.** { *; }
-dontwarn rx.**

# Gson rules
-keep class sun.misc.Unsafe { *; }
# Keep non static or private fields of models so Gson can find their names
-keepclassmembers class com.teamwork.sample.maicon.data.model** { *; }
# Some models used by gson are inner classes inside the retrofit service
-keepclassmembers class com.teamwork.sample.maicon.data.remote.service.SampleService$** {
    !static !private <fields>;
}

-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl

# Paperparcel
-dontwarn org.jetbrains.annotations.**
-keepclassmembers class nz.bradcampbell.paperparcel.PaperParcelMapping {
  static ** FROM_ORIGINAL;
  static ** FROM_PARCELABLE;
}

# https://code.google.com/p/android/issues/detail?id=194513
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

# Produces useful obfuscated stack traces
# http://proguard.sourceforge.net/manual/examples.html#stacktrace
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
