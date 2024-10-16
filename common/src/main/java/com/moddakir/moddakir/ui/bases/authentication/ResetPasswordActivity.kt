package com.moddakir.moddakir.ui.bases.authentication

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityResetPasswordBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.utils.ValidationUtils
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.AutViewModel
import io.reactivex.Observable

class ResetPasswordActivity : BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_reset_password
    private lateinit var binding: ActivityResetPasswordBinding
    private val validation :ValidationUtils= ValidationUtils()
    private var observable: Observable<Boolean>? = null
    private val authViewModel: AutViewModel by viewModels()
    companion object{
        lateinit var password :String
    }
    override fun initializeViewModel() {}
    override fun observeViewModel() {
        observe(authViewModel.resetPasswordLiveData, ::handleResetPasswordResponse)

    }

    private fun handleResetPasswordResponse(response: Resource<ModdakirResponse<ResponseModel>>?) {
        when (response) {
            is Resource.Loading -> {
                binding.btnReset.isEnabled = false
            }

            is Resource.Success -> response.data?.let {
               showMessage(response.data.message!!)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        observeChanges()
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnReset.setOnClickListener{

            if (validation.MatchText(
                    binding.etPassword.getText().toString(),
                    binding.etPasswordConfirm.getText().toString()
                )
            ){
                password= binding.etPassword.getText().toString()
                authViewModel.resetPassword(binding.etPassword.getText().toString())
            }
        }
    }
    private fun observeChanges() {
        val passwordObservable: Observable<String> = RxTextView.textChanges(binding.etPassword).skip(1).map(
            kotlin.CharSequence::toString
        )
        val confirmPasswordObserver: Observable<String> =
            RxTextView.textChanges(binding.etPasswordConfirm).skip(1).map(
                kotlin.CharSequence::toString
            )
        observable = Observable.combineLatest<String, String, Boolean>(
            passwordObservable, confirmPasswordObserver
        ) { s: String?, s2: String? ->
            val isValidConfirmPassword: Boolean = validation.MatchText(s!!, s2!!)
            binding.TIPassword.isErrorEnabled = false
            if (!isValidConfirmPassword) {
                binding.TIConfirmPass.setError(resources.getString(R.string.password_not_match))
                binding.TIPassword.setError(resources.getString(R.string.password_not_match))
                binding.TIConfirmPass.isErrorEnabled = true
                binding.TIPassword.isErrorEnabled = true
            } else {
                binding.TIConfirmPass.isErrorEnabled = false
                binding.TIPassword.isErrorEnabled = false
            }
            isValidConfirmPassword
        }
    }
}