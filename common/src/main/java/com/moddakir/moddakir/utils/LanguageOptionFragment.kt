package com.moddakir.moddakir.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moddakir.moddakir.adapter.LangOptionsAdapter
import com.moddakir.moddakir.view.bases.listeners.OptionLanguageClickListener

class LanguageOptionFragment(private var lang: (Language) -> Unit, private var isNeedDefault: Boolean = false)
    : BottomSheetDialogFragment() {
    private val onCallOptionClickListener = OptionLanguageClickListener { callOption: Language? ->
        dismiss()
        when (callOption) {
            Language.arabic -> {
                lang(Language.arabic)

            }
            Language.urdu -> {
                lang(Language.urdu)
            }
            Language.indonesia -> {
                lang(Language.indonesia)
            }
            Language.english -> {
                lang(Language.english)
            }
            Language.french -> {
                lang(Language.french)
            }

            else -> {
                if (isNeedDefault) {
                    lang(Language.empty)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_language_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callOptionsRv = view.findViewById<RecyclerView>(R.id.callOptionsRv)
        view.findViewById<View>(R.id.back).setOnClickListener { v: View? ->
            dismiss()
            if (isNeedDefault) {
                lang(Language.empty)
            }


        }
        callOptionsRv.adapter = LangOptionsAdapter(getListOfLanguages(), onCallOptionClickListener)
    }

    override fun getTheme(): Int {
        return R.style.BaseBottomSheetDialog
    }
}