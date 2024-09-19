package com.moddakir.moddakir.view.bases.authentication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityJoinUsBinding
import com.google.android.material.textfield.TextInputLayout
import com.moddakir.moddakir.adapter.MySpinnerProfileAdapter
import com.moddakir.moddakir.model.FilterModel
import com.moddakir.moddakir.model.base.BaseActivity

class JoinUsActivity : BaseActivity() {
    private var managerId: String = ""
    private lateinit var universityDegList: ArrayList<FilterModel>
    lateinit var otherSchoolTil: TextInputLayout
    private var programType: String = ""
    override val layoutId: Int get() = R.layout.activity_join_us

    private lateinit var binding: ActivityJoinUsBinding
    override fun initializeViewModel() {
    }

    override fun observeViewModel() {
    }

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
        binding.btnSubmit.setOnClickListener { v ->
          /*  if (isValidInputs()) {
                viewModel.submitJoinUs(getInputsMap())
            }*/
        }

    }

    private fun setupEducationLevelSpinner() {
        val universityDegreesIds = resources.getStringArray(R.array.university_degrees_ids_s)
        val universityDegrees = resources.getStringArray(R.array.university_degrees_s)

        for (i in 0 until universityDegreesIds.size + 1) {
            lateinit var filterModel: FilterModel
            if (i == 0) {
                filterModel =
                    FilterModel("", getString(R.string.select_education_level), null, false)
            } else {
                filterModel = FilterModel(
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

}