# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.rigil.mogcorelib.** {*;}
-dontwarn com.rigil.mogcorelib.**
-dontnote com.rigil.mogcorelib.**

# Retrofit, OkHttp, Gson
-keepattributes *Annotation*
-keepattributes Signature
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

-keep class okio.** { *; }
-keep interface okio.** { *; }
-dontwarn okio.**

-keep class org.reactivestreams.** { *; }
-keep interface org.reactivestreams.** { *; }
-dontwarn org.reactivestreams.**

-keep class kotlin.jvm.** { *; }
-dontwarn kotlin.jvm.**

-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

-keep class android.arch.persistence.** { *; }
-keep interface kotlin.Unit.** { *; }
-keep class kotlin.Unit.** { *; }
-keep class kotlin.Function.** { *; }
-keep interface kotlin.Function.** { *; }

-keep class com.bumptech.glide.** { *; }
-keep class kotlin.reflect.** { *; }
-keep class kotlin.internal.** { *; }
-keep interface kotlin.internal.** { *;}

-dontwarn rx.**
-keep class rx.** { *; }
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep, includedescriptorclasses class retrofit2.Response.** { *; }
-keep interface retrofit2.Response.** { *; }
-dontnote okhttp3.**, okio.**, retrofit2.**, kotlin.Unit, kotlin.Funtion, kotlin.reflect.**
-dontnote kotlin.internal.**, javax.annotation.concurrent.GuardedBy, com.bumptech.glide.GeneratedAppGlideModuleImpl
-dontnote sun.misc.Unsafe, javax.annotation.Nullable

-keep class io.reactivex.** { *; }
-dontwarn io.reactivex.**
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-keep class javax.annotation.Nullable
-keep class sun.misc.Unsafe
-keep class javax.annotation.concurrent.GuardedBy
-dontwarn javax.annotation.**

-keep class com.squareup.javapoet.* { *; }
-dontwarn com.squareup.javapoet.**

-keep class org.antlr.v4.** { *; }
-dontwarn org.antlr.v4.**
-dontnote org.antlr.v4.**

-keep class org.antlr.runtime.** { *; }
-dontwarn org.antlr.runtime.**
-dontnote org.antlr.runtime.**

-keep class org.sqlite.** { *; }
-dontwarn org.sqlite.**
-dontnote org.sqlite.**

-keep class me.eugeniomarletti.** { *; }
-dontwarn me.eugeniomarletti.**
-dontnote me.eugeniomarletti.**

-keep class com.google.common.** { *; }
-dontwarn com.google.common.**
-dontnote com.google.common.**

-keep class org.xmlpull.v1.** { *; }
-dontwarn org.xmlpull.v1.**
-dontnote org.xmlpull.v1.**

-keep class javax.swing.* { *; }
-dontwarn javax.swing.**
-dontnote javax.swing.**

-keep class org.jetbrains.** { *; }
-dontnote org.jetbrains.**
-dontwarn org.jetbrains.**

-keep class java.awt.** { *; }
-dontnote java.awt.**
-dontwarn java.awt.**

-keep class org.stringtemplate.** { *; }
-dontwarn org.stringtemplate.**
-dontnote org.stringtemplate.**

-keep class javax.lang.model.** { *; }
-dontnote javax.lang.model.**
-dontwarn javax.lang.model.**

-keep class javax.tools.** { *; }
-dontwarn javax.tools.**
-dontnote javax.tools.**

-keep class java.lang.Object.** { *; }
-dontnote java.lang.Object.**
-dontwarn java.lang.Object.**

-keep class android.arch.persistence.** { *; }
-dontnote android.arch.persistence.**
-dontwarn android.arch.persistence.**

-dontnote javax.annotation.processing.**

-keep class com.google.auto.** { *; }
-dontnote com.google.auto.**
-dontwarn com.google.auto.**

-dontnote  kotlin.coroutines.**
-dontnote com.google.android.**

-keep class android.os.WorkSource$WorkChain.** { *; }
-dontwarn android.os.WorkSource$WorkChain.**
-dontnote android.os.WorkSource$WorkChain.**