package dev.covercash.rust

class ExternalLibrary {

    external fun addInts(first: Int, second: Int): Int

    fun load(): Result<Unit, String> =
        try {
            System.loadLibrary("rust").ok()
        } catch (e: Exception) {
            "error loading system library".err()
        }
}