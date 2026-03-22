# #25 Fixed: Proguard Rules for sXamTop

# 1. Keep your Data Models (prevents GSON from failing to parse News items)
-keep class com.sxam.sxamtop.data.model.** { *; }
-keep class com.sxam.sxamtop.data.remote.** { *; }
-keep class com.sxam.sxamtop.data.local.** { *; }

# 2. Room Database Rules
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
-dontwarn androidx.room.paging.LimitOffsetDataSource

# 3. Retrofit & OkHttp Rules (Networking)
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault
-dontwarn okio.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# 4. Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.android.HandlerContext {
    val handler;
}

# 5. Coil (Image Loading)
-keep class coil.** { *; }
-dontwarn coil.**