package com.moddakir.moddakir.network.model.base

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
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.moddakir.moddakir.App
import com.moddakir.moddakir.App.Companion.context
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.network.model.response.ErrorResponse
import com.moddakir.moddakir.ui.bases.MainActivity
import com.moddakir.moddakir.ui.bases.authentication.ForgetPasswordActivity
import com.moddakir.moddakir.ui.bases.authentication.JoinUsActivity
import com.moddakir.moddakir.ui.bases.authentication.LoginActivity
import com.moddakir.moddakir.ui.bases.authentication.LoginWithMobileActivity
import com.moddakir.moddakir.ui.bases.authentication.RegisterMobileActivity
import com.moddakir.moddakir.ui.bases.authentication.VerificationMobileActivity
import com.moddakir.moddakir.ui.bases.holyQuran.QuranInstance.updateLanguage
import com.moddakir.moddakir.ui.bases.staticPages.HistoryActivity
import com.moddakir.moddakir.ui.widget.ButtonCalibriBold
import com.moddakir.moddakir.ui.widget.ProgressDialog
import com.moddakir.moddakir.ui.widget.TextViewLateefRegOT
import com.moddakir.moddakir.utils.Event
import dagger.hilt.android.AndroidEntryPoint
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngineCache

@AndroidEntryPoint

abstract class BaseActivity: LocalizationActivity() {
    protected lateinit var baseViewModel: BaseViewModel
    abstract val layoutId: Int
    private var progressDialog:ProgressDialog= ProgressDialog()
    protected abstract fun initializeViewModel()
    abstract fun observeViewModel()
    var forceUpdate = false

    override fun attachBaseContext(newBase: Context) {
        LocaleHelper.onAttach(newBase)?.let { super.attachBaseContext(it) }
        val config = resources.configuration
        context.resources.updateConfiguration(config, resources.displayMetrics)
        super.attachBaseContext(newBase)
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
        startActivity(intent)
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

    @SuppressLint("ResourceType")
    fun setToolbarColor(
        toolbar: androidx.appcompat.widget.Toolbar,
        color: String
    ) {
        ViewCompat.setBackgroundTintList( toolbar, ColorStateList.valueOf(Color.parseColor(color)))

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

    fun showAlertDialog() {
        hideAlertDialog()
        progressDialog.getInstance()!!.show(this)
    }

    fun hideAlertDialog() {
        progressDialog.getInstance()!!.dismiss()
    }
    fun navigateToHomeScreen() {
        startActivity(Intent(context,MainActivity::class.java))
    }
    fun navigateToHistoryActivity() {
        startActivity(Intent(context, HistoryActivity::class.java))
    }
    fun navigateToVerificationActivity() {
        startActivity(Intent(context, VerificationMobileActivity::class.java))
    }

    fun navigateToQuran(){
        val intent = Intent(  FlutterActivity
            .withCachedEngine("my_engine_id")
            .build(context))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)
        updateLanguage()
    }

}