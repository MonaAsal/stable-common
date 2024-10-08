package com.moddakir.moddakir.ui.bases.staticPages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityContactUsBinding
import com.moddakir.moddakir.adapter.ContactUsDataAdapter
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.ResponseModel
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.StaticPagesViewModel

class ContactUsActivity : BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_contact_us
    private lateinit var binding: ActivityContactUsBinding
    private val viewModel: StaticPagesViewModel by viewModels()
    private lateinit var adapter: ContactUsDataAdapter
    override fun initializeViewModel() {
        viewModel.getAboutUs()
    }

    override fun observeViewModel() {
        observe(viewModel.aboutUsLiveData, ::handleSocialMediaDataResponse)
        observe(viewModel.contactUsLiveData, ::handleContactResponse)

    }

    private fun handleContactResponse(resource: Resource<ModdakirResponse<ResponseModel>>?) {
        when (resource) {
            is Resource.Loading -> {
                binding.btnSend.isEnabled = false
            }

            is Resource.Success -> resource.data?.let {
                showMessage(getString(R.string.scu_message_contact))
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        binding.toolbar.setTitle(resources.getString(R.string.contact_us))
        binding.teacherJoinBut.setOnClickListener { v ->
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse("https://moddakir.com/join-teacher/"))
            startActivity(i)
        }
        binding.btnSend.setOnClickListener {
            if (binding.message.getText().toString().trim().isEmpty()) {
                binding.message.error = resources.getString(R.string.requierd)
                return@setOnClickListener
            }
            binding.btnSend.isEnabled = false
            viewModel.contactUsForm(
                binding.message.getText().toString(),
                binding.ticketTitle.getText().toString()
            )
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contact_us_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.history) {
            navigateToHistoryActivity()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun handleSocialMediaDataResponse(resource: Resource<ModdakirResponse<AboutModel>>?) {

    }
}