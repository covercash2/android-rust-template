package dev.covercash.rust

class ExternalLibrary {
    fun load(): Result<Unit, String> =
        try {
            System.loadLibrary("rust.so").ok()
        } catch (e: Exception) {
            "error loading system library".err()
        }
}