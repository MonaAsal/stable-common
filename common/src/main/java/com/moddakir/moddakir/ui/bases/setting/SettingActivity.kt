package com.moddakir.moddakir.ui.bases.setting

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivitySettingBinding
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.User
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.utils.AccountPreference
import com.moddakir.moddakir.utils.Language
import com.moddakir.moddakir.utils.LanguageOptionFragment
import com.moddakir.moddakir.utils.getLanguage
import com.moddakir.moddakir.utils.getLanguageCode
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.SettingsViewModel
import java.util.Locale

class SettingActivity : BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_setting
    private var savedLang = ""
    private var deviceLocales: Locale? = null
    private val selectedLang: String? = null
    private var language: Language? = null
    private var user: User? = null
    private var toggleChanged = false
    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: ActivitySettingBinding
    override fun initializeViewModel() {}
    override fun observeViewModel() {
        observe(viewModel.callSettingsLiveData, ::handleChangeCallSettingsResponse)
    }

    private fun handleChangeCallSettingsResponse(resource: Resource<ModdakirResponse<ResponseModel>>?) {
        when (resource) {
            is Resource.Loading -> {
                binding.saveChangesBut.isEnabled = false
            }

            is Resource.Success -> resource.data?.let {
                user!!.enableVideoRecording=(binding.enableVideoRecording.isChecked)
                user!!.enableVoiceRecording=(binding.enableVoiceRecording.isChecked)
                if (user != null) {
                    AccountPreference.registerData(user)
                }
                changeLanguageAction(language)
            }

            is Resource.NetworkError -> {
                resource.errorCode?.let {
                }
            }

            is Resource.DataError -> {
                resource.errorResponse?.let { showServerErrorMessage(resource.errorResponse) }
            }

            else -> {}
        }
    }

    private fun changeLanguageAction(language: Language?) {
        LocaleHelper.setLocale(this, getLanguageCode(language!!))
        navigateToHomeScreen()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLanguageAtFirstTime()
       // user = AccountPreference.getUser()
        binding.toolbar.setTitle(resources.getString(R.string.settings))
        if (user != null) {
            binding.enableVoiceRecording.setChecked(user!!.enableVoiceRecording)
            binding.enableVideoRecording.setChecked(user!!.enableVideoRecording)
        }
        binding.enableVideoRecording.setOnCheckedChangeListener { _: CompoundButton?, _: Boolean ->
            toggleChanged = true
        }
        binding.enableVoiceRecording.setOnCheckedChangeListener { _: CompoundButton?, _: Boolean ->
            toggleChanged = true
        }
        selectedLanguage()
        binding.saveChangesBut.setOnClickListener{
            viewModel.changeSettings(binding.enableVoiceRecording.isChecked.toString(),binding.enableVideoRecording.isChecked.toString())
        }

    }
    private fun selectedLanguage() {
        when (getLanguage(selectedLang!!)) {
            Language.english -> {
                language = Language.english
                binding.langNameTv.text = Language.english.code
            }

            Language.french -> {
                language = Language.french

                binding.langNameTv.text =Language.french.code
            }

            Language.arabic -> {
                language = Language.arabic
                binding.langNameTv.text =Language.arabic.code
            }

            Language.indonesia -> {
                language = Language.indonesia
                binding.langNameTv.text =Language.indonesia.code
            }

            Language.urdu -> {
                language = Language.urdu
                binding.langNameTv.text =Language.urdu.code
            }

            else -> {
                language = Language.arabic
                binding.langNameTv.text =Language.arabic.code
            }
        }
        binding.langNameTv.setOnClickListener{
            val languageOptionFragment: LanguageOptionFragment = LanguageOptionFragment({ it ->
                when (it) {
                    Language.english -> {
                        language = Language.english
                        binding.langNameTv.text = Language.english.code
                    }

                    Language.french -> {
                        language = Language.french

                        binding.langNameTv.text = Language.french.code
                    }

                    Language.arabic -> {
                        language = Language.arabic
                        binding.langNameTv.text = Language.arabic.code
                    }

                    Language.indonesia -> {
                        language = Language.indonesia

                        binding.langNameTv.text = Language.indonesia.code
                    }

                    Language.urdu -> {
                        language = Language.urdu
                        binding.langNameTv.text = Language.urdu.code
                    }

                    else -> {}
                }
                null
            }, true)
            languageOptionFragment.show(supportFragmentManager, languageOptionFragment.getTag())
        }
    }

    private fun setLanguageAtFirstTime() {
        val langFromPrefs: String =
            LocaleHelper.getLocale(this).toString()
        if (langFromPrefs!!.isEmpty() || langFromPrefs == null || langFromPrefs == "") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                deviceLocales = Resources.getSystem().configuration.locales[0]
                savedLang = Resources.getSystem().configuration.locales[0].language
                checkLanguageCode(savedLang)
            } else {
                val deviceLocale = Locale.forLanguageTag(resources.configuration.locale.language)
                savedLang = deviceLocale.language
                checkLanguageCode(savedLang)
            }
        } else {
            savedLang = langFromPrefs
            checkLanguageCode(savedLang)
        }

    }

    private fun checkLanguageCode(savedLang: String) {
        var languageName = savedLang
        when (languageName) {
            "en" -> {
                languageName = resources.getString(R.string.english)
                binding.langNameTv.text = languageName
            }

            "ar" -> {
                languageName = resources.getString(R.string.arabic)
                binding.langNameTv.text = languageName
            }

            "fr" -> {
                languageName = resources.getString(R.string.french)
                binding.langNameTv.text = languageName
            }

            "in" -> {
                languageName = resources.getString(R.string.indonesia)
                binding.langNameTv.text = languageName
            }

            "ur" -> {
                languageName = resources.getString(R.string.urdu)
                binding.langNameTv.text = languageName
            }

            else -> {
                languageName = resources.getString(R.string.arabic)
                binding.langNameTv.text = languageName
            }
        }
    }


}