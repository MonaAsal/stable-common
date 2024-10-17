package com.moddakir.moddakir.ui.bases.staticPages

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityTicketsHistoryDetailsBinding
import com.moddakir.moddakir.App.Companion.ColorPrimary
import com.moddakir.moddakir.App.Companion.timeZoneOffset
import com.moddakir.moddakir.adapter.TicketRepliesAdapter
import com.moddakir.moddakir.network.Resource
import com.moddakir.moddakir.network.model.Item
import com.moddakir.moddakir.network.model.Reply
import com.moddakir.moddakir.network.model.TicketReplyResponse
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.network.model.response.ModdakirResponse
import com.moddakir.moddakir.network.model.response.TicketResponse
import com.moddakir.moddakir.network.model.response.TicketsRepliesResponse
import com.moddakir.moddakir.ui.widget.ButtonCalibriBold
import com.moddakir.moddakir.utils.Utils
import com.moddakir.moddakir.utils.observe
import com.moddakir.moddakir.viewModel.StaticPagesViewModel

class TicketsHistoryDetailsActivity : BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_tickets_history_details
    private lateinit var repliesAdapter:TicketRepliesAdapter
    private var replies: ArrayList<Reply> = ArrayList<Reply>()
    private var pageNum=0
    val utils: Utils = Utils()
    private lateinit var ticketId:String
    private val viewModel: StaticPagesViewModel by viewModels()
    private lateinit var binding: ActivityTicketsHistoryDetailsBinding
    override fun initializeViewModel() {
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun observeViewModel() {
        observe(viewModel.ticketsReplaysLiveData, ::handleTicketsReplaysResponse)
        observe(viewModel.sendReplayLiveData, ::handleSendReplayResponse)
        observe(viewModel.ticketByIdLiveData, ::handleTicketByIdResponse)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleTicketByIdResponse(resource: Resource<ModdakirResponse<TicketResponse>>?) {
        when (resource) {
            is Resource.Success -> resource.data?.let {
                ticketId = resource.data.data!!.item.id
                viewModel.getTicketReplies(pageNum,ticketId)
                displayDetails(resource.data.data.item)
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

    private fun handleSendReplayResponse(resource: Resource<ModdakirResponse<TicketReplyResponse>>?) {
        when (resource) {
            is Resource.Loading -> {
                binding.send.isEnabled = false
            }
            is Resource.Success -> resource.data?.let {
                replies.add(0, resource.data.data!!.reply)
                repliesAdapter.notifyDataSetChanged()
                binding.message.setText("")
                binding.send.isEnabled = true
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

    private fun handleTicketsReplaysResponse(resource: Resource<ModdakirResponse<TicketsRepliesResponse>>?) {
        when (resource) {
            is Resource.Success -> resource.data?.let {
                replies.addAll(resource.data.data!!.items)
                displayReplies()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTicketsHistoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAppColor()
        binding.toolbar.setTitle(resources.getString(R.string.details))
        binding.send.setOnClickListener {
            if (binding.message.getText().toString().trim().isEmpty()) {
                binding.message.error = resources.getString(R.string.requierd)
            } else {
                viewModel.sendReplay(binding.message.getText().toString(),ticketId)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            binding.toolbar.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.colorWhite))
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        getTicketID()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTicketID() {
        val ticket: Item? = intent.getParcelableExtra("ITEM_TAG")
        val id = intent.getStringExtra("ticketId")
        if (ticket != null) {
            ticketId= ticket.id
            if (ticketId != null)
                viewModel.getTicketReplies(pageNum,ticketId)
            displayDetails(ticket)
        } else if (!id.isNullOrEmpty()) {
            ticketId = id
            viewModel.getTicketById(ticketId)
        } else {
            onBackPressed()
        }
    }

    private fun displayReplies() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.detailsReplyRv.setLayoutManager(layoutManager)
        repliesAdapter = TicketRepliesAdapter(this, replies)
        binding.detailsReplyRv.setAdapter(repliesAdapter)
        binding.detailsReplyRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    pageNum++
                    viewModel.getTicketReplies(pageNum,ticketId)
                }
            }

        })
        repliesAdapter.notifyDataSetChanged()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayDetails(ticket: Item) {
        binding.ticketTitle.text = ticket.title
        binding.ticketNo.text = ticket.number
        binding.time.text = utils.getDateFromStart(this, ticket.date, timeZoneOffset)
        binding.content.text = ticket.content
    }
    private fun setAppColor() {
        val listTextViewPrimaryColors = listOf(binding.ticketNoLb,binding.titleLb,binding.contentLb)
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        setToolbarColor(toolbar, ColorPrimary)
        setPrimaryColor(listTextViewPrimaryColors, ColorPrimary)
    }
}