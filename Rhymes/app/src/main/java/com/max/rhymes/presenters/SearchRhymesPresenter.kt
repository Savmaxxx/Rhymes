package com.max.rhymes.presenters

import com.max.rhymes.R
import com.max.rhymes.presentations.views.SearchRhymesView
import com.max.rhymes.data.Repository
import com.max.rhymes.myEnum.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchRhymesPresenter(private var searchRhymesView: SearchRhymesView) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var repository = Repository()
    private val noMatches = -1
    fun onClickButtonSearchRhymes(wordForRhyme: String) {
        requestToServer(wordForRhyme)
        searchRhymesView.clearTextViewRhymes()
        searchRhymesView.startAnimationSearchRhymes()
        searchRhymesView.hideKeyboard()
    }

    fun onClickTextViewAccent(wordForRhyme: String, accent: String) {
        val requestParameter = "/$accent"
        searchRhymesView.startAnimationSearchRhymesNewAccent()
        searchRhymesView.clearTextViewRhymes()
        requestToServer(wordForRhyme, requestParameter)
    }

    fun editTextRhymeChanged() {
        searchRhymesView.startSoundAndAnimationEditTextChanged()
        searchRhymesView.setVisibilityButtonChangeAccent(0x00000004)//INVISIBLE=0x00000004
    }

    fun actionDownButtonChangeAccent() {
        searchRhymesView.reduceScaleButtonChangeAccent()
    }

    fun actionUpButtonChangeAccent(wordForRhymes: String) {
        searchRhymesView.increaseScaleButtonChangeAccent()
        searchRhymesView.showDialogChangeAccent(repository.getUsedTextViewAccept(wordForRhymes))
    }

    private fun requestToServer(wordForRhyme: String, requestParameter: String = "") {
        coroutineScope.launch {
            val response = repository.getResponse(wordForRhyme, requestParameter)
            val rhymes: String = response.rhymes
            val numberAccent: Int = response.numberAccent
            withContext(Dispatchers.Main) {
                when (rhymes) {
                    NetworkResponse.EMPTY_WORD.response -> searchRhymesView.showDialogInfo(R.string.empty_word)
                    NetworkResponse.NOT_INTERNET.response -> searchRhymesView.showDialogInfo(R.string.not_internet)
                    NetworkResponse.RHYMES_NOT_FIND.response -> searchRhymesView.showDialogInfo(R.string.rhymes_not)
                    else -> {
                        when (numberAccent) {
                            noMatches -> searchRhymesView.setRhymes(rhymes)
                            else -> {
                                searchRhymesView.setWordWithAccentToEditText(
                                    repository.getWordWithAccent(
                                        wordForRhyme,
                                        numberAccent
                                    )
                                )
                                searchRhymesView.setRhymes(rhymes)
                                searchRhymesView.setVisibilityButtonChangeAccent(0x00000000)//VISIBLE=0x00000000
                            }
                        }
                    }
                }
            }
        }
    }
}
