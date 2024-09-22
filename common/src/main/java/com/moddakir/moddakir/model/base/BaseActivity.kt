package com.moddakir.moddakir.model.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.lifecycle.LiveData
import com.moddakir.moddakir.App.Companion.context
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.model.response.ErrorResponse
import com.moddakir.moddakir.utils.Event
import com.moddakir.moddakir.ui.bases.Intro.IntroActivity
import com.moddakir.moddakir.ui.bases.authentication.ForgetPasswordActivity
import com.moddakir.moddakir.ui.bases.authentication.JoinUsActivity
import com.moddakir.moddakir.ui.bases.authentication.LoginActivity
import com.moddakir.moddakir.ui.bases.authentication.LoginWithMobileActivity
import com.moddakir.moddakir.ui.bases.authentication.RegisterMobileActivity
import com.moddakir.moddakir.ui.widget.ButtonCalibriBold
import com.moddakir.moddakir.ui.widget.TextViewLateefRegOT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

abstract class BaseActivity: AppCompatActivity() {
    protected lateinit var baseViewModel: BaseViewModel
    abstract val layoutId: Int
    protected abstract fun initializeViewModel()
    abstract fun observeViewModel()
    var forceUpdate = false

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base!!))
        val config = resources.configuration
       context.resources.updateConfiguration(config, resources.displayMetrics)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(layoutId)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
    }

    fun showLoadingView(progress_bar: ProgressBar) {
        progress_bar.visibility= View.VISIBLE
    }

    fun showLoadingView(progress_bar: ProgressBar, show: Boolean) {
        if (show)
            progress_bar.visibility= View.VISIBLE
        else
            progress_bar.visibility= View.GONE
    }
    fun observeToast(event: LiveData<Event<Any>>) {
        if (event.value != null)
            showMessage(event.value.toString())
    }

    fun observeError(event: LiveData<Event<String>>) {

        if (event.value != null)
            showMessage(event.value.toString())
    }

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun showServerErrorMessage(errorResponse: ErrorResponse) {
        if (errorResponse.errors != null && errorResponse.errors?.isNotEmpty()!!)
            showMessage(errorResponse.errors?.get(0)?.error!!)
    }

    fun showSystemDownError() {
        Toast.makeText(context,  "SYSTEM DOWN", Toast.LENGTH_SHORT).show()
    }
    fun navigateToLoginScreen(
        showJoinUsPrograms: Boolean,
        showJoinGeneralProgram: Boolean,
        showEducationMinistryProgram: Boolean,
        showAppLogo: Boolean
    ) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(LoginScreenEntities.joinUsPrograms.toString(), showJoinUsPrograms)
        intent.putExtra(LoginScreenEntities.joinGeneralProgram.toString(), showJoinGeneralProgram)
        intent.putExtra(LoginScreenEntities.educationMinistryProgram.toString(), showEducationMinistryProgram)
        intent.putExtra(LoginScreenEntities.appLogo.toString(), showAppLogo)

        startActivity(intent)
    }
    fun navigateToLoginScreen() {
        startActivity(Intent(context,LoginActivity::class.java))
    }
    fun navigateToRegisterScreen() {
        startActivity(Intent(context,RegisterMobileActivity::class.java))
    }
    fun navigateToLoginMobileScreen(action:String) {
        val intent = Intent(context,LoginWithMobileActivity::class.java)
        intent.putExtra("action",action)
        startActivity(intent);

    }
    fun navigateToLoginIntroScreen() {
        val intent = Intent(context, IntroActivity::class.java)
        intent.putExtra("languageChanged", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    fun navigateToForgetPasswordScreen() {
        startActivity(Intent(context, ForgetPasswordActivity::class.java))
    }
    enum class LoginScreenEntities {
        joinUsPrograms,
        joinGeneralProgram,
        educationMinistryProgram, //Maqratac
        appLogo
    }

    @SuppressLint("ResourceType")
    fun setButtonsColor(
        listButtonPrimaryColors: List<ButtonCalibriBold>,
        color: String
    ) {
        for(i in listButtonPrimaryColors.indices){
            ViewCompat.setBackgroundTintList(  listButtonPrimaryColors[i], ColorStateList.valueOf(
                Color.parseColor(color)))
        }
    }

    fun setPrimaryColor(
        listTextViewPrimaryColors: List<TextViewLateefRegOT>,
        colorPrimary: String
    ) {
        for(element in listTextViewPrimaryColors) {
            element.setTextColor( Color.parseColor(colorPrimary))
        }
    }

    fun navigateToJoinUs() {
        val intent = Intent(context, JoinUsActivity::class.java)
        startActivity(intent)
    }


}