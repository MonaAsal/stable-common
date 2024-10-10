package com.moddakir.moddakir.ui.bases.listeners

import com.moddakir.moddakir.network.model.BannerModel

interface BannerClickListener {
    fun onBannerClicked(position: Int, bannerModel: BannerModel?)
}