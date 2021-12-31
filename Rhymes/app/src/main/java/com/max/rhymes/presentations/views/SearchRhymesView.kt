package com.max.rhymes.presentations.views

import android.text.SpannableString

interface SearchRhymesView {
    fun setRhymes(rhymes: String)
    fun showDialogChangeAccent(wordListRhymes: MutableList<SpannableString>)
    fun showDialogInfo(stringResources: Int)
    fun setVisibilityButtonChangeAccent(isVisible: Int)
    fun startSoundAndAnimationEditTextChanged()
    fun startAnimationSearchRhymes()
    fun startAnimationSearchRhymesNewAccent()
    fun clearTextViewRhymes()
    fun reduceScaleButtonChangeAccent()
    fun increaseScaleButtonChangeAccent()
    fun hideKeyboard()
    fun setWordWithAccentToEditText(wordWithAccent: SpannableString)
}