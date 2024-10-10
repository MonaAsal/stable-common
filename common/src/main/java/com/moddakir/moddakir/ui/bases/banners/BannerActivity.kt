package com.moddakir.moddakir.ui.bases.banners

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityBannerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.moddakir.moddakir.App
import com.moddakir.moddakir.adapter.BannerAdapter
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.BannerModel
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.BannerResponseModel
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.ui.bases.listeners.BannerClickListener
import com.moddakir.moddakir.utils.Utils
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.BannerViewModel
import java.util.Timer
import kotlin.concurrent.schedule

class BannerActivity : BaseActivity(), BannerClickListener {
    override val layoutId: Int get() = R.layout.activity_banner
    val utils: Utils = Utils()
    var sliderPosition: Int = 0
    private val viewModel: BannerViewModel by viewModels()
    private lateinit var binding: ActivityBannerBinding
    override fun initializeViewModel() {
        viewModel.getBanners()
    }

    override fun observeViewModel() {
        observe(viewModel.bannerLiveData, ::handleBannerResponse)
    }

    private fun handleBannerResponse(resource: Resource<ModdakirResponse<BannerResponseModel>>?) {
        if (resource!!.data!= null && resource.data!!.data != null && resource.data.data!!.data!= null) {
            if ( LocaleHelper.getLocale(this).toString() == "ar")
                binding.bannerViewPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL)
            val bannerSliderAdapter = BannerAdapter(
                resource.data.data.data!!,
                App.context, this)
            binding.bannerViewPager.setAdapter(bannerSliderAdapter)
            binding.bannerViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderPosition= position
                }
            })
            val tabLayoutMediator = TabLayoutMediator(
                binding.adsDots, binding.bannerViewPager
            ) { tab: TabLayout.Tab?, position: Int -> }
            tabLayoutMediator.attach()
            startBannerCounter(resource.data.data.data!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onBannerClicked(position: Int, bannerModel: BannerModel?) {
        if (bannerModel!!.type ==="video") {
            val  videoActivity= VideoActivity()
            videoActivity.start(
                App.context,
                bannerModel!!.url,
                getString(R.string.app_name_student)
            )
        }
        else handleAdClicks(bannerModel!!)
    }
    private fun handleAdClicks(bannerModel: BannerModel) {
        if (bannerModel.url.contains("moddakir.page.link")) {
           // handleDynamicLinkClick(bannerModel.url)
        } else {
            if (utils.isValidate(bannerModel.url)) {
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse(bannerModel.url))
                startActivity(i)
            }
        }
    }
    private fun startBannerCounter(bannerData: ArrayList<BannerModel>) {
        Timer().schedule(5000) {
            sliderPosition =
                (sliderPosition + 1) % bannerData.size
        }
    }
}