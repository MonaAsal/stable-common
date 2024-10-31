package com.moddakir.moddakir.ui.bases.authentication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.FragmentDependentManagersDialogBinding
import com.moddakir.moddakir.App.Companion.ColorPrimary
import com.moddakir.moddakir.App.Companion.SecondColor
import com.moddakir.moddakir.adapter.DependentManagersAdapter
import com.moddakir.moddakir.network.model.Student
import com.moddakir.moddakir.ui.widget.ButtonCalibriBold
import com.moddakir.moddakir.utils.AccountPreference
import com.moddakir.moddakir.viewModel.AutViewModel

class DependentManagersDialogFragment() : DialogFragment() {
    private var pagenum = 1
    private val students: List<Student> = ArrayList<Student>()
    private var dependentManagersAdapter: DependentManagersAdapter? = null
    private var studentId: String = ""
    private var id = ""
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var binding: FragmentDependentManagersDialogBinding
    private val viewModel: AutViewModel by viewModels()
    constructor(maqraatec: String) : this() {
        this.maqraatec = maqraatec
    }

    private lateinit var maqraatec:String
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentDependentManagersDialogBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppColor()
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvDependentManagers.setLayoutManager(layoutManager)
        view.findViewById<View>(R.id.saveSessionPreferences).setOnClickListener { v: View? ->
            if (id.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    R.string.please_select_one_of_the_managers_to_complete_the_progression_of_the_reading,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                dismiss()
                Intent(context, JoinUsActivity::class.java).putExtra("managerId", id).putExtra("programType", maqraatec)
            }
        }
        view.findViewById<View>(R.id.cancel).setOnClickListener { v: View? ->
            dismiss()
        }
        view.findViewById<View>(R.id.close).setOnClickListener { v: View? ->
            dismiss()
        }
        dependentManagersAdapter = DependentManagersAdapter(
            students,
          planClickListener = {

          },
            -1
        )
        binding.rvDependentManagers.setAdapter(dependentManagersAdapter)
        getData(pagenum)
    }
    private fun getData(pageNum: Int) {
        if (AccountPreference.getUser() != null) {
            studentId = AccountPreference.getUser()!!.id
        }
        viewModel.getDependentManagers(studentId,false,pageNum,20)
    }
    private fun setAppColor() {
        val listButtonPrimaryColors: List<ButtonCalibriBold> = listOf(binding.saveSessionPreferences)
        setButtonsColor(listButtonPrimaryColors, ColorPrimary)
    }
    @SuppressLint("ResourceType")
    fun setButtonsColor(
        listButtonPrimaryColors: List<ButtonCalibriBold>,
        color: String
    ) {
        for(i in listButtonPrimaryColors.indices){
            ViewCompat.setBackgroundTintList(  listButtonPrimaryColors[i], ColorStateList.valueOf(
                Color.parseColor(color)))
        }
    }
}