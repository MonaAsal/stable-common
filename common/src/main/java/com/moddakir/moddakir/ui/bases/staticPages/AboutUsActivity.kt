package com.moddakir.moddakir.ui.bases.staticPages

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.moddakirapps.BuildConfig
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityAboutUsBinding
import com.moddakir.moddakir.App.Companion.ColorPrimary
import com.moddakir.moddakir.App.Companion.SecondColor
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.AboutModel
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.ui.widget.ButtonCalibriBold
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.StaticPagesViewModel

class AboutUsActivity : BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_about_us
    private val viewModel: StaticPagesViewModel by viewModels()
    private lateinit var binding: ActivityAboutUsBinding
    override fun initializeViewModel() {
        viewModel.getAboutUs()

    }

    override fun observeViewModel() {
        observe(viewModel.aboutUsLiveData, ::handleAboutUsResponse)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAppColor()
        binding.toolbar.setTitle(resources.getString(R.string.about_moddakir))
       // binding.tvVersion.text = BuildConfig.VERSION_NAME
    }


    private fun handleAboutUsResponse(resource: Resource<ModdakirResponse<AboutModel>>?) {
        when (resource) {
            is Resource.Loading -> {
            }

            is Resource.Success -> resource.data?.let {
                if (resource.errorCode == 200) {
                    if (resource.data.data!!.html != null) {
                        setupWebView(resource.data.data.html!!)
                    }
                } else {
                    Toast.makeText(this@AboutUsActivity, resource.data.message, Toast.LENGTH_LONG)
                        .show()
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(html: String) {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.allowUniversalAccessFromFileURLs = true
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                request.grant(request.resources)
            }
        }
        binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        binding.webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
    }

    private fun setAppColor() {
        val listTextViewPrimaryColors =
            listOf(binding.versionTxt)
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        setToolbarColor(toolbar, ColorPrimary)
        setPrimaryColor(listTextViewPrimaryColors, ColorPrimary)
    }
}