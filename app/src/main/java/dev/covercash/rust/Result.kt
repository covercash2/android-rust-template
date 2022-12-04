package dev.covercash.rust

sealed interface Result<T, E>

data class Ok<T, E>(val value: T): Result<T, E>

data class Err<T, E>(val error: E): Result<T, E>

fun <T, E> T.ok(): Result<T, E> = Ok(this)

fun <T, E> E.err(): Result<T, E> = Err(this)
