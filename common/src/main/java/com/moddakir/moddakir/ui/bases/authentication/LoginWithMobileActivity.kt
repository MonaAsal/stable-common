package com.moddakir.moddakir.ui.bases.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityLoginWithMobileBinding
import com.example.moddakirapps.databinding.CountryCodePickerBinding
import com.example.moddakirapps.databinding.RecaptchaLayoutBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.recaptcha.RecaptchaAction
import com.moddakir.moddakir.helper.SharedPrefHelper.Companion.setIntoSharedPref
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.RecaptchaImpl.Companion.recaptchaTasksClient
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.utils.AccountPreference
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.AutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginWithMobileActivity() : BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_login_with_mobile
    private lateinit var bindingLoginMobile: ActivityLoginWithMobileBinding
    private lateinit var bindingCountryCodePicker: CountryCodePickerBinding
    private lateinit var bindingRecaptchaLayout: RecaptchaLayoutBinding
    private val loginViewModel: AutViewModel by viewModels()
    private var num: String = ""
    var token: String? = null
    companion object{
        lateinit var phone :String
         var tokenRecaptcha :String?=null
    }

    override fun initializeViewModel() {
        observe(loginViewModel.loginLiveData, ::handleLoginResponse)
    }

    private fun handleLoginResponse(response: Resource<ModdakirResponse<ResponseModel>>?) {
        when (response) {
            is Resource.Loading -> {
                bindingLoginMobile.btnNext.isEnabled = false
            }

            is Resource.Success -> response.data?.let {
                val intent = Intent(this, VerificationMobileActivity::class.java)
                intent.putExtra("verificationData", num)
                intent.putExtra("type", "Login")
                intent.putExtra("token", response.data.data!!.accessToken)
                if (!getIntent().extras!!.getString("action").equals("Activate")) {
                    AccountPreference.setAccessToken(response.data.data.accessToken)
                }
                startActivity(intent)
            }

            is Resource.NetworkError -> {
                response.errorCode?.let {
                }
            }

            is Resource.DataError -> {
                response.errorResponse?.let { showServerErrorMessage(response.errorResponse) }
            }

            else -> {}
        }
    }

    override fun observeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLoginMobile = ActivityLoginWithMobileBinding.inflate(layoutInflater)
        bindingCountryCodePicker = CountryCodePickerBinding.inflate(layoutInflater)
        bindingRecaptchaLayout = RecaptchaLayoutBinding.inflate(layoutInflater)
        bindingCountryCodePicker.countryCodePicker.registerCarrierNumberEditText(bindingCountryCodePicker.etPhone)
        setContentView(bindingLoginMobile.root)
        bindingLoginMobile.btnNext.setOnClickListener {
            num = bindingCountryCodePicker.countryCodePicker.fullNumberWithPlus
            phone= bindingCountryCodePicker.countryCodePicker.fullNumberWithPlus

            if (validatePhone()) {
                if (intent.extras!!.getString("action").equals("Activate")) {
                    if (!num.isEmpty()) {
                        if (recaptchaTasksClient != null) {
                            recaptchaTasksClient!!.executeTask(RecaptchaAction.SIGNUP)
                                .addOnSuccessListener(
                                    OnSuccessListener<String?> { token ->
                                        tokenRecaptcha=token
                                        loginViewModel.sendPhoneVerifyNum(
                                            num,
                                            token
                                        )
                                    }).addOnFailureListener(
                                    this@LoginWithMobileActivity
                                ) {
                                    token = ""
                                }
                        }
                        //                            viewModel.sendPhoneVerifyNum(num,token);
                    } else {
                        Toast.makeText(
                            this@LoginWithMobileActivity,
                            getString(R.string.mobile_number_required),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    if (!num.isEmpty()) {
                        if (recaptchaTasksClient != null) {
                            recaptchaTasksClient!!.executeTask(RecaptchaAction.SIGNUP)
                                .addOnSuccessListener(
                                    OnSuccessListener<String?> { token ->
                                        loginViewModel.loginWithMobile(
                                            num,
                                            token,
                                            false
                                        )
                                    }).addOnFailureListener(
                                    this@LoginWithMobileActivity
                                ) {
                                    token = ""
                                }
                        }
                        else{
                            loginViewModel.loginWithMobile(
                                num,
                                "",
                                false
                            )
                        }

                    } else {
                        Toast.makeText(
                            this@LoginWithMobileActivity,
                            getString(R.string.mobile_number_required),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        bindingCountryCodePicker.countryCodePicker.registerCarrierNumberEditText(bindingCountryCodePicker.etPhone)
            bindingRecaptchaLayout.checkBoxRecaptcha.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if (b) {
                bindingRecaptchaLayout.checkBoxRecaptcha.visibility = View.GONE
                bindingRecaptchaLayout.recaptchaProgressbar.visibility = View.VISIBLE
                bindingRecaptchaLayout.confirmNotRobotTx.visibility = View.GONE
                bindingRecaptchaLayout.recaptchaProgressbar.postDelayed(Runnable {
                    bindingRecaptchaLayout.recaptchaProgressbar.visibility = View.GONE
                    bindingRecaptchaLayout.recaptchaCheckMarkIv.visibility = View.VISIBLE
                }, 2000)
            }
        })
        }
    }
    private fun validatePhone(): Boolean {
        if (bindingCountryCodePicker.countryCodePicker.fullNumberWithPlus.isEmpty()) {
            bindingCountryCodePicker.phoneTile.setError(resources.getString(R.string.mobile_number_required))
            bindingCountryCodePicker.phoneTile.isErrorEnabled = true
            return false
        } else {
            bindingCountryCodePicker.phoneTile.isErrorEnabled = false
            return true
        }
    }
}