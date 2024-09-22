package com.moddakir.moddakir.ui.bases.authentication

import android.os.Bundle
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityRegisterMobileBinding
import com.moddakir.moddakir.model.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterMobileActivity() : BaseActivity() {
    private lateinit var bindingRegisterMobile: ActivityRegisterMobileBinding
    override val layoutId: Int get() = R.layout.activity_register_mobile
    override fun initializeViewModel() {
    }

    override fun observeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRegisterMobile = ActivityRegisterMobileBinding.inflate(layoutInflater)
        setContentView(bindingRegisterMobile.root)

    }
}