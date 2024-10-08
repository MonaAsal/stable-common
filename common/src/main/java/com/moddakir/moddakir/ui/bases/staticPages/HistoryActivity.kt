package com.moddakir.moddakir.ui.bases.staticPages

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityHistoryBinding
import com.moddakir.moddakir.network.model.Item
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.viewModel.StaticPagesViewModel

class HistoryActivity : BaseActivity() {
    private var tickets: List<Item> = ArrayList<Item>()
    private val page = 0
    override val layoutId: Int get() = R.layout.activity_history
    private val viewModel: StaticPagesViewModel by viewModels()
    private lateinit var binding: ActivityHistoryBinding
    override fun initializeViewModel() {
    }

    override fun observeViewModel() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitle(resources.getString(R.string.history))
        displayTicketsList()
        getTicketsHistory(page)
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
}