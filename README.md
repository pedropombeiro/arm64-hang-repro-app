# Minimal repro for arm64 gomobile hang in React Native app

## Abstract

This app is a minimal repro for an issue where a React Native app will hang after calling into a gomobile-built Go library.

## Prerequisites for reproducing issue

- Target the `arm64-v8a` platform and run on a 64-bit Android device;
- Call (at least once) a method in an .aar archive built using gomobile;

The app does not hang if it is built for other platforms (even when running on the same device, e.g. with the `armeabi-v7a` target), or if the Go function is not called.

In order to easily test both scenarios, toggle the value of `enableHang` in `android/app/build.gradle`.

## Prerequisites to build

- Android SDK (make sure the `ANDROID_HOME` environment variable is defined to point to the SDK).

## Running the app

``` shell
react-native run-android --variant=release
```

This will deploy the app to the phone. If `enableHang` is true (so that we're generating a 64-bit only build), then the app should appear as an empty white screen, because the UI thread has locked up. Otherwise you'll see an introductory text screen.

## Building the Go library

I already included a built version of the Go library in this repo for ease-of-use, but you can build it yourself from a few lines of source code below.
In order to build the Go library, you'll also need the Android NDK (I used [r19c](https://dl.google.com/android/repository/android-ndk-r19c-linux-x86_64.zip), but it also happens with r20) with the `ANDROID_NDK_HOME` environment variable pointing to the extracted folder.

Then create a project under `$GOPATH/src` and create a single file in it:

``` golang
package library

import "log"

func DoSomething() {
  log.Println("DoSomething called")
}
```

Once the file's saved, you can build it (while on the `arm64test` project directory):

``` shell
gomobile bind -v -target=android -o ./android/libs/library.aar -ldflags=-s <path-to-go-lib-source>
```

This will output `android/libs/library.aar` and `android/libs/library-sources.jar`.
