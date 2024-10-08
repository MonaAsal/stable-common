package com.moddakir.moddakir.ui.bases.authentication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityRegisterMobileBinding
import com.example.moddakirapps.databinding.CountryCodePickerBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.recaptcha.RecaptchaAction
import com.moddakir.moddakir.App
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.network.model.RecaptchaImpl.Companion.recaptchaTasksClient
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.AutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterMobileActivity() : BaseActivity() {
    private lateinit var binding: ActivityRegisterMobileBinding
    private lateinit var bindingCountryCodePickerBinding: CountryCodePickerBinding
    override val layoutId: Int get() = R.layout.activity_register_mobile
    lateinit var lang: String
    private var  isUsedPhone:Boolean= false
    private var userphone: String = ""
    private var type = "Register"
    private var token:String=""
    private val authViewModel: AutViewModel by viewModels()
    override fun initializeViewModel() {
        observe(authViewModel.loginLiveData, ::handleRegisterResponse)
    }

    override fun observeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterMobileBinding.inflate(layoutInflater)
        App.binding=binding
        bindingCountryCodePickerBinding = CountryCodePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        lang = LocaleHelper.getLocale(this).toString()
        binding.tvLogin.setOnClickListener { onBackPressed() }
        binding.recaptcha.checkBoxRecaptcha.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.recaptcha.checkBoxRecaptcha.visibility = View.GONE
                binding.recaptcha.recaptchaProgressbar.visibility = View.VISIBLE
                binding.recaptcha.confirmNotRobotTx.visibility = View.GONE
                binding.recaptcha.recaptchaProgressbar.postDelayed(Runnable {
                    binding.recaptcha.recaptchaProgressbar.visibility = View.GONE
                    binding.recaptcha.recaptchaCheckMarkIv.visibility = View.VISIBLE
                }, 2000)
            }
        }
        binding.btnNext.setOnClickListener { view ->
            binding.btnNext.isEnabled = false
            userphone = bindingCountryCodePickerBinding.countryCodePicker.fullNumberWithPlus
            if (userphone.isNotEmpty()) {
                if (bindingCountryCodePickerBinding.countryCodePicker.isValidFullNumber) {
                    if (recaptchaTasksClient != null) {
                        recaptchaTasksClient!!.executeTask(RecaptchaAction.SIGNUP)
                            .addOnSuccessListener(OnSuccessListener<String?> { token ->
                                isUsedPhone = true
                                authViewModel.loginWithMobile(userphone, token, false)
                            }).addOnFailureListener(
                                this@RegisterMobileActivity
                            ) { e ->
                                token = ""
                                binding.btnNext.isEnabled = true
                            }
                    } else binding.btnNext.isEnabled = true
                } else {
                    binding.btnNext.isEnabled = true
                    Toast.makeText(
                        this@RegisterMobileActivity,
                        getString(R.string.valid_mobile_num),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                binding.btnNext.isEnabled = true
                Toast.makeText(
                    this@RegisterMobileActivity,
                    getString(R.string.mobile_number_required),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    private fun handleRegisterResponse(response: Resource<ModdakirResponse<ResponseModel>>?) {

        when (response) {
            is Resource.Loading -> {
                binding.btnNext.isEnabled = false
            }

            is Resource.Success -> response.data?.let {
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

}

