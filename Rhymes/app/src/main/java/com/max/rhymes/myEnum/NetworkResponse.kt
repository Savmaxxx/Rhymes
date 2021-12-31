package com.max.rhymes.myEnum

enum class NetworkResponse(val response: String) {
    RHYMES_NOT_FIND("rhymes not find"),
    NOT_INTERNET("not internet"),
    EMPTY_WORD("");
}