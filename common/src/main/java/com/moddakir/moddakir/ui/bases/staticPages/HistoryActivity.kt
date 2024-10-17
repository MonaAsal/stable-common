package com.moddakir.moddakir.ui.bases.staticPages

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityHistoryBinding
import com.moddakir.moddakir.App.Companion.ColorPrimary
import com.moddakir.moddakir.adapter.TicketsHistoryAdapter
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.Item
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.TicketsResponse
import com.moddakir.moddakir.ui.bases.listeners.OnHistoryClickListener
import com.moddakir.moddakir.ui.widget.ButtonCalibriBold
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.StaticPagesViewModel

class HistoryActivity : BaseActivity(), OnHistoryClickListener {
    private var tickets: ArrayList<Item> = ArrayList<Item>()
    private val page = 0
    override val layoutId: Int get() = R.layout.activity_history
    private val viewModel: StaticPagesViewModel by viewModels()
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyPackageAdapter:TicketsHistoryAdapter
    override fun initializeViewModel() {
        viewModel.getListContactUs(page)
    }

    override fun observeViewModel() {
        observe(viewModel.historyLiveData, ::handleContactUsHistoryResponse)
    }

    private fun handleContactUsHistoryResponse(resource: Resource<ModdakirResponse<TicketsResponse>>?) {

        when (resource) {
            is Resource.Success -> resource.data?.let {
                if (resource.data.data!!.items!!.isNotEmpty()) {
                    tickets.addAll(resource.data.data.items!!)
                    historyPackageAdapter.notifyDataSetChanged()
                }
                if (tickets.isEmpty()) {
                    binding.noItemsMessage.visibility = View.VISIBLE
                    binding.historyRv.visibility = View.GONE
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAppColor()
        binding.toolbar.setTitle(resources.getString(R.string.history))
        displayTicketsList()
    }

    private fun getTicketsHistory(page: Int) {
        viewModel.getListContactUs(page)
    }

    private fun displayTicketsList() {
        binding.noItemsMessage.visibility = View.GONE
        binding.historyRv.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.historyRv.setLayoutManager(layoutManager)
        historyPackageAdapter = TicketsHistoryAdapter(this, this, tickets)
        binding.historyRv.setAdapter(historyPackageAdapter)
        binding.historyRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    var page = page
                    page++
                    getTicketsHistory(page)
                }
            }

        })
    }

    override fun onTicketClicked(item: Item?) {
        val bundle = Bundle()
        val intent = Intent(
            this@HistoryActivity,
            TicketsHistoryDetailsActivity::class.java
        )
        intent.putExtras(bundle)
        startActivity(intent)
    }
    private fun setAppColor() {
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        setToolbarColor(toolbar, ColorPrimary)
    }
}