package com.max.rhymes.utils

import android.content.Context
import android.content.res.Resources
import android.text.SpannableString
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.max.rhymes.R
import com.max.rhymes.presenters.SearchRhymesPresenter

class CreateDialogUtils {
    private lateinit var alertDialog: AlertDialog
    fun createDialogAccent(
        context: Context,
        wordListRhymes: MutableList<SpannableString>,
        searchRhymesPresenter: SearchRhymesPresenter,
        editTextRhyme: EditText,
    ): AlertDialog {
        val clickAccent = View.OnClickListener {
            searchRhymesPresenter.onClickTextViewAccent(
                editTextRhyme.text.toString().trim().lowercase(),
                it.tag.toString()
            )
            alertDialog.dismiss()
        }
        val viewDialogLayout = View.inflate(context, R.layout.dialog_change_rhymes, null)
        val textViewAccent1 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent1)
        val textViewAccent2 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent2)
        val textViewAccent3 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent3)
        val textViewAccent4 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent4)
        val textViewAccent5 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent5)
        val textViewAccent6 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent6)
        val textViewAccent7 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent7)
        val textViewAccent8 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent8)
        val textViewAccent9 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent9)
        val textViewAccent10 = viewDialogLayout.findViewById<TextView>(R.id.textViewAccent10)
        val listTextViewAccent = mutableListOf<TextView>(
            textViewAccent1,
            textViewAccent2,
            textViewAccent3,
            textViewAccent4,
            textViewAccent5,
            textViewAccent6,
            textViewAccent7,
            textViewAccent8,
            textViewAccent9,
            textViewAccent10
        )
        for (i in wordListRhymes.indices) {
            listTextViewAccent[i].text = wordListRhymes[i]
            listTextViewAccent[i].tag = i.toString()
            listTextViewAccent[i].setOnClickListener(clickAccent)
        }
        alertDialog =
            AlertDialog.Builder(context, R.style.CustomDialog).setView(viewDialogLayout).create()
        return alertDialog

    }

    fun createDialogInfo(
        context: Context,
        stringResources: Int,
        resources: Resources,
    ): AlertDialog {
        val viewDialog = View.inflate(context, R.layout.dialog_info, null)
        val buttonSaveWord = viewDialog.findViewById<Button>(R.id.buttonOk)
        val textViewInfo = viewDialog.findViewById<TextView>(R.id.textViewInfo)
        textViewInfo.text = resources.getString(stringResources)
        buttonSaveWord.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog =
            AlertDialog.Builder(context, R.style.CustomDialog).setView(viewDialog).create()
        return alertDialog
    }
}