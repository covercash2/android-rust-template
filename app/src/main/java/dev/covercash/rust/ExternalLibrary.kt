package dev.covercash.rust

class ExternalLibrary {
    fun load(): Result<Unit, String> =
        try {
            System.loadLibrary("rust").ok()
        } catch (e: Exception) {
            "error loading system library".err()
        }
}