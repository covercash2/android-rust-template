ABIS = ARMV7 ARMV8

TARGET_ARMV7 = aremeabi-v7a
TARGET_ARMV8 = arm64-v8a
ANDROID_ABIS = $(TARGET_ARMV7) $(TARGET_ARMV8)

TARGET_PARENT = app/src/main/jniLibs/
TARGET_DIR_ARMV7 = $(TARGET_PARENT)$(TARGET_ARMV7)
TARGET_DIR_ARMV8 = $(TARGET_PARENT)$(TARGET_ARMV8)
TARGET_DIRS = $(addprefix $(TARGET_PARENT), $(ANDROID_ABIS))
CARGO_OUTPUT_DIR = rust/target/

CARGO_ABI_ARMV7 = armv7-linux-androideabi
CARGO_ABI_ARMV8 = aarch64-linux-android
CARGO_ABIS = $(CARGO_ARMV7) $(CARGO_ARMV8)

OBJS = $(foreach abi,$(CARGO_ABIS), $(CARGO_OUTPUT_DIR)$(abi)/release/librust.so)

CARGO = cargo --manifest-path=rust/Cargo.toml
CARGO_BUILD = cargo build --manifest-path=rust/Cargo.toml --release

build: $(ABIS)

$(ABIS): $(TARGET_DIRS)
	$(CARGO_BUILD) --target $(CARGO_ABI_$@)
	cp $(CARGO_OUTPUT_DIR)$(CARGO_ABI_$@)/release/librust.so $(TARGET_DIR_$@)


$(TARGET_DIRS): $(TARGET_PARENT)
	mkdir $@

$(TARGET_PARENT):
	mkdir $(TARGET_PARENT)
	
clean:
	cargo clean --manifest-path=rust/Cargo.toml
	rm -rf $(TARGET_PARENT)
