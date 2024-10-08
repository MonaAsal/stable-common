package com.moddakir.moddakir.ui.bases.authentication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityForgetPasswordBinding
import com.example.moddakirapps.databinding.CountryCodePickerBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.recaptcha.RecaptchaAction
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.network.model.RecaptchaImpl.Companion.recaptchaTasksClient
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.utils.ValidationUtils
import com.moddakir.moddakir.viewModel.AutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordActivity : BaseActivity() {
    override var layoutId: Int = R.layout.activity_forget_password
    private lateinit var bindingForgetPassword: ActivityForgetPasswordBinding
    private lateinit var bindingCountryCodePickerBinding: CountryCodePickerBinding
    var token: String? = null
    private var isEmail = true
    private val authViewModel: AutViewModel by viewModels()
    companion object{
        lateinit var email :String
        lateinit var phone :String
        lateinit var lang :String
        var tokenRecaptcha :String? = null
    }
    override fun initializeViewModel() {}

    override fun observeViewModel() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingForgetPassword = ActivityForgetPasswordBinding.inflate(layoutInflater)
        bindingCountryCodePickerBinding = CountryCodePickerBinding.inflate(layoutInflater)
        setContentView(bindingForgetPassword.root)
        bindingForgetPassword.mobileLogin.setOnClickListener{
            bindingForgetPassword.mailLinear.visibility = View.GONE
            bindingForgetPassword.mobileLinear.visibility = View.VISIBLE
            isEmail = false
        }
        bindingForgetPassword.emailLogin.setOnClickListener {
            bindingForgetPassword.mailLinear.visibility = View.VISIBLE
            bindingForgetPassword.mobileLinear.visibility = View.GONE
            isEmail = true
        }
        bindingForgetPassword.btnNext.setOnClickListener {
            if (isEmail) {
                if (validateEmail(bindingForgetPassword.etEmail.getText().toString().trim())) {
                    if (recaptchaTasksClient != null) {
                        recaptchaTasksClient!!.executeTask(RecaptchaAction.SIGNUP)
                            .addOnSuccessListener(OnSuccessListener<String?> { token ->
                                authViewModel.sendForgetPassword(
                                bindingForgetPassword.etEmail.getText().toString().trim(), null, LocaleHelper.getLocale(this).toString(),token
                                )
                            }).addOnFailureListener(
                                this@ForgetPasswordActivity
                            ) {
                                token = ""
                            }
                    }
                }
            } else {
                if (validatePhone()) {
                    if (recaptchaTasksClient != null) {
                        recaptchaTasksClient!!.executeTask(RecaptchaAction.SIGNUP)
                            .addOnSuccessListener(OnSuccessListener<String?> { token ->
                                authViewModel.sendForgetPassword(
                                   null, bindingCountryCodePickerBinding.countryCodePicker.fullNumberWithPlus, LocaleHelper.getLocale(this).toString(),token
                                )
                            }).addOnFailureListener(
                                this@ForgetPasswordActivity
                            ) {
                                token = ""
                            }
                    }
                }
            }
            lang = LocaleHelper.getLocale(this).toString()
            email=bindingForgetPassword.etEmail.text.toString()
            phone=bindingForgetPassword.codeContainer.etPhone.text.toString()
            tokenRecaptcha=token.toString()
        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun validateEmail(text: String?): Boolean {
    val validation = ValidationUtils()
        if (!validation.isEmailValid(text!!)) {
            bindingForgetPassword.etEmail.error = resources.getString(R.string.email_invalid)
            return false
        } else {
            return true
        }
    }

    private fun validatePhone(): Boolean {
        if (!bindingCountryCodePickerBinding.countryCodePicker.isValidFullNumber) {
            Toast.makeText(
                this,
                resources.getString(R.string.mobile_number_required),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else {
            return true
        }
    }


}