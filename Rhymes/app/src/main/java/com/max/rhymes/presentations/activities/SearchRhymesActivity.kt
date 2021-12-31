package com.max.rhymes.presentations.activities

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.max.rhymes.R
import com.max.rhymes.presentations.views.SearchRhymesView
import com.max.rhymes.presenters.SearchRhymesPresenter
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_search_rhymes.*
import android.text.Editable
import android.text.TextWatcher
import java.util.*
import android.view.MotionEvent
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import com.max.rhymes.animation.MyAnimation
import com.max.rhymes.utils.CreateDialogUtils
import kotlinx.android.synthetic.main.dialog_change_rhymes.*

class SearchRhymesActivity : AppCompatActivity(), SearchRhymesView {
    private lateinit var sound: MediaPlayer
    private var createDialogUtils = CreateDialogUtils()
    private var myAnimation = MyAnimation()
    private lateinit var alertDialog: AlertDialog
    private lateinit var searchRhymesPresenter: SearchRhymesPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Rhymes)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_rhymes)
        sound = MediaPlayer.create(this, R.raw.sound_pen)
        volumeControlStream = AudioManager.STREAM_MUSIC
        searchRhymesPresenter = SearchRhymesPresenter(this)
        setListener()
    }

    private fun setListener() {
        buttonSearchRhymes.setOnClickListener {
            searchRhymesPresenter.onClickButtonSearchRhymes(
                editTextRhyme.text.toString().trim().lowercase()
            )
        }
        buttonChangeAccent.setOnTouchListener(OnTouchListener { v, event ->
            v.performClick()
            if (event.action == MotionEvent.ACTION_DOWN) {
                searchRhymesPresenter.actionDownButtonChangeAccent()
                return@OnTouchListener true
            } else if (event.action == MotionEvent.ACTION_UP) {
                searchRhymesPresenter.actionUpButtonChangeAccent(
                    editTextRhyme.text.toString().trim().lowercase()
                )
                return@OnTouchListener true
            }
            false
        })

        editTextRhyme.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchRhymesPresenter.editTextRhymeChanged()
            }
        })
    }

    override fun setRhymes(rhymes: String) {
        myAnimation.cancelAnimatorPreloader(imageViewPreloader)
        textViewRhymes.text = rhymes
    }

    override fun showDialogChangeAccent(wordListRhymes: MutableList<SpannableString>) {
        alertDialog = createDialogUtils.createDialogAccent(
            this,
            wordListRhymes,
            searchRhymesPresenter,
            editTextRhyme,
        )
        alertDialog.show()
    }

    override fun showDialogInfo(stringResources: Int) {
        myAnimation.cancelAnimatorPreloader(imageViewPreloader)
        alertDialog =
            createDialogUtils.createDialogInfo(this, stringResources, resources)
        alertDialog.show()
    }

    override fun setVisibilityButtonChangeAccent(isVisible: Int) {
        buttonChangeAccent.visibility = isVisible
        buttonChangeAccent.isEnabled = buttonChangeAccent.isVisible
    }

    override fun startSoundAndAnimationEditTextChanged() {
        sound.start()
        myAnimation.setRotateAnimationSheet(constraintLayoutSheet)
    }

    override fun startAnimationSearchRhymes() {
        myAnimation.setRotateAnimation(buttonSearchRhymes, imageViewPreloader)
    }

    override fun startAnimationSearchRhymesNewAccent() {
        myAnimation.startAnimatorPreloader(imageViewPreloader)
    }

    override fun clearTextViewRhymes() {
        textViewRhymes.text = ""
    }

    override fun reduceScaleButtonChangeAccent() {
        buttonChangeAccent.scaleX = buttonChangeAccent.scaleX - 0.06f
        buttonChangeAccent.scaleY = buttonChangeAccent.scaleY - 0.06f
    }

    override fun increaseScaleButtonChangeAccent() {
        buttonChangeAccent.scaleX = buttonChangeAccent.scaleX + 0.06f
        buttonChangeAccent.scaleY = buttonChangeAccent.scaleY + 0.06f
    }

    override fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            buttonSearchRhymes.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    override fun setWordWithAccentToEditText(wordWithAccent: SpannableString) {
        editTextRhyme.setText(wordWithAccent)
        editTextRhyme.setSelection(editTextRhyme.length())
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}