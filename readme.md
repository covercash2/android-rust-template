# prerequisite

## install Android Studio
install Android Studio from https://developer.android.com/studio

### Android NDK
the NDK is necessary to provide an LLVM toolchain for compiling native binaries for Android devices. the NDK can be downloaded from the SDK manager within Android Studio. 

Note the installed version and install location.

#### Android `jniLibs`
https://developer.android.com/studio/projects/gradle-external-native-builds#jniLibs
   
static library objects need to be copied to the Android project at:
   
   ```sh 
	<app_root>/src/main/jniLibs
   ```
   
##### known Android ABIs
	- x86
	- x86_64
	- armeabi
	- armeabi-v7a
	- arm64-v8a
    
## install `rustup`
this project requires the `rustup` and `cargo` tools to be installed. they can be installed from https://www.rust-lang.org/tools/install i.e.:
   ```
   curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
   ```

## Rust Android ABI compilers
   
``` bash
rustup target add aarch64-linux-android armv7-linux-androideabi
```

## configure cargo
   create a cargo config file at `~/.cargo/config`:

   - `android_sdk` is the install location of the Android SDK
   - `host-os` is the architecture of the compiler on the host system. it should be the only one installed by default
   - `ndk_version` is the version of the installed NDK from the SDK manager
   - other ABIs will need to be added if you want to support those architectures
   
   ``` toml
   [target.aarch64-linux-android]
   linker = "<android_sdk>/ndk/<ndk_version>/toolchains/llvm/prebuilt/<host-os>/bin/aarch64-linux-android30-clang++"
   
[target.armv7-linux-androideabi]
linker = "<android_sdk>/ndk/<ndk_version>/toolchains/llvm/prebuilt/<host-os>/bin/armv7a-linux-androideabi30-clang++"
   ```
   
# run the app

building and copying of the Rust binaries is automated by gradle. tasks were added that build, copy, and cleanup libraries for supported ABIs. these tasks are executed automatically for build and clean tasks.

building the app including the static object binaries is as easy as clicking run in Android Studio or simply running `./gradlew build`.
