package com.moddakir.moddakir.view.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatRadioButton

class RadioButtonLight : AppCompatRadioButton{
    constructor(context: Context) : super(context){init()}
    constructor(context: Context, attrs: AttributeSet?) :  super(context,attrs){init()}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :  super(context,attrs,defStyleAttr){init()}
    fun init() {
        //  Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font/Calibri_Bold.TTF");
        // setTypeface(font);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
    }
}