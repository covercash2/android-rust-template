ANDROID_ABIS = arm64-v8a armeabi-v7a
TARGET_PARENT = app/src/main/jniLibs
TARGET_DIRS = $(addprefix $(TARGET_PARENT)/, $(ANDROID_ABIS))
CARGO_OUTPUT_DIR = rust/target/

CARGO_ABIS = aarch64-linux-android armv7-linux-androideabi

OBJS = $(addprefix $(CARGO_OUTPUT_DIR)/, $(CARGO_ABIS)/release/librust.so)

build_native_libs: $(OBJS) $(TARGET_DIRS)

$(OBJS): $(CARGO_ABIS)

$(CARGO_ABIS):
	cargo build --manifest-path=rust/Cargo.toml --release --target $@

$(TARGET_DIRS): $(TARGET_PARENT)
	mkdir $@

$(TARGET_PARENT):
	mkdir $(TARGET_PARENT)
