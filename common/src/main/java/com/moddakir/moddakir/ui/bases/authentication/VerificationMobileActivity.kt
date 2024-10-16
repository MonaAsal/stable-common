package com.moddakir.moddakir.ui.bases.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityVerificationMobileBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.recaptcha.RecaptchaAction
import com.moddakir.moddakir.network.model.RecaptchaImpl.Companion.recaptchaTasksClient
import com.moddakir.moddakir.network.model.base.BaseActivity

import com.moddakir.moddakir.viewModel.AutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationMobileActivity : BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_verification_mobile
    private val loginViewModel: AutViewModel by viewModels()
    override fun initializeViewModel() {}
    override fun observeViewModel() {}

    private lateinit var binding: ActivityVerificationMobileBinding

    companion object {
        lateinit var code: String
        var providerType: String? = null
        var providerUserId: String? = null
        var isSocialMediaAccountPending: Boolean? = false
         var verificationData: String = "verificationData"
         var verificationType: String = "verificationType"
         var verificationSource: String = "verificationSource"
    }

    fun start(
        context: Context,
        data: String?,
        type: VerificationType?,
        source: VerificationSource?,
        SocialMediaAccountPending: Boolean?,
        providerUserId: String?,
        providerType: String?,
        isSkippedVerification: Boolean?
    ) {
        val intent = Intent(context, VerificationMobileActivity::class.java)
        intent.putExtra(verificationData, data)
        intent.putExtra(verificationSource, source)
        intent.putExtra(verificationType, type)
        intent.putExtra("SocialMediaAccountPending", SocialMediaAccountPending)
        intent.putExtra("isSkippedVerification", isSkippedVerification)
        intent.putExtra("providerUserId", providerUserId)
        intent.putExtra("providerType", providerType)
        context.startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnSubmit.setOnClickListener { v ->
            if (binding.etOtp.getText().toString().length !== 0) {
                code = binding.etOtp.getText().toString()
                if (intent.getStringExtra("type") == "Validate")
                    loginViewModel.validatePhoneNumber(
                        binding.etOtp.getText().toString(),
                        null,
                        null,
                        getDeviceUDID(),
                        null
                    )
                else
                    if (intent.getStringExtra("type") == "RegisterSocial") {
                        loginViewModel.validateOTP(
                            binding.etOtp.getText().toString(),
                            intent.getStringExtra("providerType"),
                            intent.getStringExtra("providerUserId"),
                            getDeviceUDID(),
                            intent.getBooleanExtra("SocialMediaAccountPending", true)
                        )
                        providerType = intent.getStringExtra("providerType")
                        providerUserId = intent.getStringExtra("providerUserId")
                        isSocialMediaAccountPending = intent.getBooleanExtra("SocialMediaAccountPending", true)
                    } else {
                        loginViewModel.validateOTP(
                            binding.etOtp.getText().toString(),
                            null,
                            null,
                            getDeviceUDID(),
                            null
                        )

                    }

            }
        }
        binding.tvResend.setOnClickListener {
            sendCode()
        }
        binding.tvSkip.setOnClickListener {
            SkipOtp()
        }
    }

    private fun sendCode() {
        if (recaptchaTasksClient != null) {
            recaptchaTasksClient!!.executeTask(RecaptchaAction.SIGNUP).addOnSuccessListener(
                OnSuccessListener<String?> { token ->
                    loginViewModel.loginWithMobile(
                        intent.getStringExtra(verificationData)!!,
                        token, false
                    )
                }).addOnFailureListener(
                this@VerificationMobileActivity
            ) {
            }
        }
    }

    private fun SkipOtp() {
        loginViewModel.validateOTP(
            "0000",
            intent.getStringExtra("providerType"),
            intent.getStringExtra("providerUserId"),
            getDeviceUDID(),
            intent.getBooleanExtra("SocialMediaAccountPending", true)
        )

    }

    enum class VerificationType {
        PHONE, EMAIL
    }

    enum class VerificationSource {
        FORGET_PASSWORD, VERIFY
    }

    fun getDeviceUDID(): String {
        val deviceId =
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        return deviceId
    }
}