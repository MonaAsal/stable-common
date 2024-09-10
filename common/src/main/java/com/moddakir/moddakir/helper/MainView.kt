package com.moddakir.moddakir.helper

interface MainView {
    fun showToastMessage(message: String?)

    fun showAlertDialog()

    fun hideAlertDialog()

    fun showMessageDialog(
        title: String?,
        message: String?,
        isCancelOutside: Boolean,
        func: Runnable?
    )

}