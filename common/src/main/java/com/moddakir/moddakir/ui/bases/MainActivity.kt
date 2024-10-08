package com.moddakir.moddakir.ui.bases

import android.os.Bundle
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityMainBinding
import com.google.android.recaptcha.RecaptchaAction
import com.moddakir.moddakir.App
import com.moddakir.moddakir.App.Companion.AppName
import com.moddakir.moddakir.App.Companion.ApplicationVersion
import com.moddakir.moddakir.App.Companion.ColorPrimary
import com.moddakir.moddakir.network.model.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recaptchaImpl: com.moddakir.moddakir.network.model.RecaptchaImpl
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initializeViewModel() {}

    override fun observeViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recaptchaImpl= com.moddakir.moddakir.network.model.RecaptchaImpl()
        recaptchaImpl.getRecaptchaTasksClient(application, this, RecaptchaAction.SIGNUP)

        binding.StartApp.setOnClickListener{
            AppName="Student"
            ApplicationVersion= App.AppVersion.Version1.toString()
            ColorPrimary="#40BFB4"
            navigateToLoginScreen()
         }


       /* AppName="Maqratac"
        ApplicationVersion= App.AppVersion.Version3.toString()
        ColorPrimary="#5189BA"
        SecondColor="#6F7D8B"
        WhatsAppNum="https://wa.me/966538124002"
        navigateToLoginScreen(
                showJoinUsPrograms = true,
                showJoinGeneralProgram = true,
                showEducationMinistryProgram = true,
                showAppLogo = true
            )
        */

        /* AppName="Tallam"
       ApplicationVersion= App.AppVersion.Version3.toString()
       ColorPrimary="#035E63"
        SecondColor="#D6B98B"
       WhatsAppNum="https://wa.me/966530664046"
      navigateToLoginScreen(
                showJoinUsPrograms = true,
                showJoinGeneralProgram = true,
                showEducationMinistryProgram = false,
                showAppLogo = true
            )
       */

        /* AppName="Rattel"
      ApplicationVersion= App.AppVersion.Version3.toString()
      ColorPrimary="#12723B"
      SecondColor="#12723B"
      WhatsAppNum="https://wa.me/966554774590"
      navigateToLoginScreen(
                showJoinUsPrograms = true,
                showJoinGeneralProgram = false,
                showEducationMinistryProgram = false,
                showAppLogo = false
            )
      */

    }



}