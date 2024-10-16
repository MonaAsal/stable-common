package com.moddakir.moddakir.ui.bases.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityJoinUsBinding
import com.google.android.material.textfield.TextInputLayout
import com.moddakir.moddakir.App
import com.moddakir.moddakir.adapter.MySpinnerProfileAdapter
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.User
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.ui.bases.HomeActivity
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.AutViewModel
import timber.log.Timber

class JoinUsActivity : BaseActivity() {
    private var managerId: String = ""
    private lateinit var universityDegList: ArrayList<com.moddakir.moddakir.network.model.FilterModel>
    lateinit var otherSchoolTil: TextInputLayout
    private var programType: String = ""
    override val layoutId: Int get() = R.layout.activity_join_us
    private lateinit var binding: ActivityJoinUsBinding
    private val viewModel: AutViewModel by viewModels()
    private var username:String=""

    override fun initializeViewModel() {
    }

    override fun observeViewModel() {
        observe(viewModel.submitJoinUsLiveData, ::handleSubmitJoinUsResponse)

    }

    private fun handleSubmitJoinUsResponse(resource: Resource<ModdakirResponse<ResponseModel>>?) {
        when (resource) {
            is Resource.Loading -> {
                binding.btnSubmit.isEnabled = false
            }

            is Resource.Success -> resource.data?.let {
                val user: User = resource.data.data!!.student
                if (user != null) {
                    viewModel.handleLoggedUser(resource.data.data)
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("isNew", resource.data.data.isNewUser)
                    intent.putExtra("freeMin", resource.data.data.freeMinutes)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else {
                    navigateToLoginScreen()
                }
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

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityJoinUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        managerId = intent.getStringExtra("managerId")!!
        programType = intent.getStringExtra("programType")!!
        universityDegList = java.util.ArrayList()
        setupEducationLevelSpinner()
        if (programType == LoginActivity.ProgramType.General.value || programType == LoginActivity.ProgramType.Ministry.value) {
            binding.btnSubmit.setText(R.string.register)
        }
        username = if (binding.emailTil.editText!!.text.toString().isNotEmpty()) {
            binding.emailTil.editText!!.text.toString()
        } else {
            binding.mobileNumber.phoneTile.editText!!.text.toString()
        }
        binding.btnSubmit.setOnClickListener { v ->
            val education = com.moddakir.moddakir.network.model.Education(
                null,
                universityDegList[binding.educationLevelSpinner.selectedItemPosition].id,
                null
            )
                viewModel.submitJoinUs(
                    binding.firstNameTil.editText!!.text.toString(),managerId,programType(),username,
                    binding.emailTil.editText!!.text.toString(),binding.mobileNumber.countryCodePicker.fullNumberWithPlus,
                    binding.mobileNumber.countryCodePicker.selectedCountryNameCode,
                    universityDegList[binding.educationLevelSpinner.selectedItemPosition].id,
                    Settings.Secure.getString(App.context.contentResolver, Settings.Secure.ANDROID_ID),
                    viewModel.getGenderType(binding.genderLayout.typeRg.checkedRadioButtonId)!!,education, binding.passwordTil.editText!!.text.toString())
        }

    }

    private fun setupEducationLevelSpinner() {
        val universityDegreesIds = resources.getStringArray(R.array.university_degrees_ids_s)
        val universityDegrees = resources.getStringArray(R.array.university_degrees_s)

        for (i in 0 until universityDegreesIds.size + 1) {
            lateinit var filterModel: com.moddakir.moddakir.network.model.FilterModel
            if (i == 0) {
                filterModel =
                    com.moddakir.moddakir.network.model.FilterModel(
                        "",
                        getString(R.string.select_education_level),
                        null,
                        false
                    )
            } else {
                filterModel = com.moddakir.moddakir.network.model.FilterModel(
                    universityDegreesIds[i - 1],
                    universityDegrees[i - 1],
                    null,
                    false
                )
            }

            universityDegList.add(filterModel)
        }
        val mySpinnerAdapter = MySpinnerProfileAdapter(this, R.layout.spinner_item, universityDegList)
        binding.educationLevelSpinner.adapter = mySpinnerAdapter
        binding.educationLevelSpinner.setSelection(0)
        binding.educationLevelSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ) {
                if (position != 0) {
                    otherSchoolTil.editText!!.setText("")
                    otherSchoolTil.visibility = View.GONE
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
    }
    private fun programType(): String {
        val general: String = LoginActivity.ProgramType.General.value
        val ministry: String = LoginActivity.ProgramType.Ministry.value
        val maqraatec: String = LoginActivity.ProgramType.Maqraatec.value
        Timber.d("programType: %s", programType)
        if (programType == general) {
            managerId = ""
            return general
        } else if (programType == ministry) {
            managerId = ""
            return ministry
        } else if (programType == maqraatec) {
            return maqraatec
        } else return ""
    }
}