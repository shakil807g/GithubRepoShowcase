package com.shakil.githubreposhowcase.util

interface Mapper<T, U> {
    suspend fun map(input: T): U
}

fun failMissingField(name: String): Nothing {
    throw IllegalArgumentException("${name ?: ""} is missing form server response" )
}