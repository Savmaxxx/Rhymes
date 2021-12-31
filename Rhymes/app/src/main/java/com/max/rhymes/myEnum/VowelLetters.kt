package com.max.rhymes.myEnum

enum class VowelLetters(val arrayVowelLetters: Array<Char>) {
    VowelLettersRussian(arrayOf('а', 'е', 'и', 'о', 'у', 'э', 'ю', 'я', 'ы', 'ё')),
    VowelLettersEnglish(arrayOf('a', 'e', 'i', 'o', 'u', 'y'));
}