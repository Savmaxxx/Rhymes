package com.max.rhymes.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.max.rhymes.myEnum.VowelLetters

class SearchAccentsUtils {
    private var wordListRhymes = mutableListOf<SpannableString>()
    private var arrayVowelsLetters = VowelLetters.VowelLettersRussian.arrayVowelLetters
    private lateinit var letters: CharArray

    fun getPossibleAccents(wordForRhymes: String): MutableList<SpannableString> {
        searchAccent(wordForRhymes)
        wordListRhymes.reverse()
        return wordListRhymes
    }

    fun searchWordWithNumberAccent(wordForRhymes: String, numberAccent: Int): SpannableString {
        searchAccent(wordForRhymes)
        return wordListRhymes[numberAccent - 1]
    }

    private fun searchAccent(wordForRhymes: String) {
        letters = wordForRhymes.toCharArray()
        for (i in letters.indices) {
            for (j in arrayVowelsLetters.indices) {
                if (letters[i] == arrayVowelsLetters[j]) {
                    letters[i] = letters[i].uppercaseChar()
                    val wordWithNewStress = String(letters)
                    val spannableString = SpannableString(wordWithNewStress)
                    spannableString.setSpan(
                        ForegroundColorSpan(Color.RED),
                        i,
                        i + 1,
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    wordListRhymes.add(spannableString)
                    letters[i] = letters[i].lowercaseChar()
                }
            }
        }
    }
}