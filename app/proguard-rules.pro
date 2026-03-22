# #10 FIX: Full Proguard Rules for sXamTop

# Data Models (Gson)
-keep class com.sxam.sxamtop.data.model.** { *; }
-keep class com.sxam.sxamtop.data.remote.** { *; }
-keep class com.sxam.sxamtop.data.local.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
-dontwarn androidx.room.paging.LimitOffsetDataSource

# Retrofit & OkHttp
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault
-dontwarn okio.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.android.HandlerContext {
    val handler;
}

# Coil Image Loading
-keep class coil.** { *; }
-dontwarn coil.**