package com.moddakir.moddakir.view.bases.authentication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityLoginStudentBinding
import com.example.moddakirapps.databinding.ActivityLoginWithMobileBinding
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