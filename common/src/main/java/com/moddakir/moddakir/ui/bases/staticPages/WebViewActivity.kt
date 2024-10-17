package com.moddakir.moddakir.ui.bases.staticPages

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityWebViewBinding
import com.moddakir.moddakir.App.Companion.ColorPrimary
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.network.model.base.BaseActivity

class WebViewActivity : BaseActivity() {
    var name =""
    private var url =""
    override val layoutId: Int get() = R.layout.activity_web_view
    override fun initializeViewModel() {}
    override fun observeViewModel() {}

    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAppColor()
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setCollapseContentDescription(intent.getStringExtra("TITLE"))
        name = intent.getStringExtra("NAME")!!
        url = intent.getStringExtra("URL")!!
        supportActionBar!!.title = intent.getStringExtra("TITLE")
        displayWebViewData()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun displayWebViewData() {
        binding.webView.settings.userAgentString = LocaleHelper.getLocale(this).toString()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.allowFileAccessFromFileURLs = true
        binding.webView.settings.allowUniversalAccessFromFileURLs = true
        binding.webView.settings.setSupportZoom(true)
        // Set a web view client and a chrome client
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        binding.webView.loadUrl(url)
        (this as BaseActivity).showAlertDialog()

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun getDefaultVideoPoster(): Bitmap {
                return Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
            }

            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress == 100) {
                    this@WebViewActivity.hideAlertDialog()
                }
            }
        }
    }
    private fun setAppColor() {
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        setToolbarColor(toolbar, ColorPrimary)
    }
}