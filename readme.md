# prerequisite
## install `rustup`
   this project requires the `rustup` and `cargo` tools to be installed. they can be installed from https://www.rust-lang.org/tools/install i.e.:
   ```
   curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
   ```
   
## install Android Studio
   install Android Studio from https://developer.android.com/studio

### Android NDK
	the NDK is necessary to provide an LLVM toolchain for compiling native binaries for Android devices. the NDK can be downloaded from the SDK manager within Android Studio. 

	Note the installed version and install location.

# configure Android `jniLibs`
   https://developer.android.com/studio/projects/gradle-external-native-builds#jniLibs
   
   static library objects need to be copied to the Android project at:
   
   ```sh 
	<app_root>/src/main/jniLibs
   ```
   
## known Android ABIs
	- x86
	- x86_64
	- armeabi
	- armeabi-v7a
	- arm64-v8a
    
# Rust Android ABI compilers
   
``` bash
rustup target add aarch64-linux-android armv7-linux-androideabi
```

## configure cargo
   create a cargo config file at `~/.cargo/config`:
   
   ``` toml
   [target.aarch64-linux-android]
   linker = "/Users/c0o02bc/Library/Android/sdk/ndk/21.4.7075529/toolchains/llvm/prebuilt/darwin-x86_64/bin/aarch64-linux-android30-clang++"
   
[target.armv7-linux-androideabi]
   linker = "/Users/c0o02bc/Library/Android/sdk/ndk/21.4.7075529/toolchains/llvm/prebuilt/darwin-x86_64/bin/armv7a-linux-androideabi30-clang++"
   ```
