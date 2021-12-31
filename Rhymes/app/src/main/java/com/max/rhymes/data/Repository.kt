package com.max.rhymes.data

import android.text.SpannableString
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.max.rhymes.Response
import com.max.rhymes.utils.NetworkUtils
import com.max.rhymes.utils.SearchAccentsUtils


class Repository {

    fun getResponse(wordForRhymes: String, accent: String): Response {
        return NetworkUtils().getResponse("$wordForRhymes$accent")
    }

    fun getWordWithAccent(wordForRhymes: String, numberAccent: Int): SpannableString {
        return SearchAccentsUtils().searchWordWithNumberAccent(wordForRhymes, numberAccent)
    }

    fun getUsedTextViewAccept(wordForRhymes: String): MutableList<SpannableString> {
        return SearchAccentsUtils().getPossibleAccents(wordForRhymes)
    }

}