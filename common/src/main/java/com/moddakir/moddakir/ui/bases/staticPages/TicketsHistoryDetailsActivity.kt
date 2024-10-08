package com.moddakir.moddakir.ui.bases.staticPages

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityTicketsHistoryDetailsBinding
import com.moddakir.moddakir.App.Companion.timeZoneOffset
import com.moddakir.moddakir.adapter.TicketRepliesAdapter
import com.moddakir.moddakir.network.model.Item
import com.moddakir.moddakir.network.model.Reply
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.utils.Utils
import com.moddakir.moddakir.viewModel.StaticPagesViewModel

class TicketsHistoryDetailsActivity : BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_tickets_history_details
    private lateinit var repliesAdapter:TicketRepliesAdapter
    private var replies: List<Reply> = ArrayList<Reply>()
    private var pageNum=0
    val utils: Utils = Utils()
    private lateinit var ticketId:String
    private val viewModel: StaticPagesViewModel by viewModels()
    private lateinit var binding: ActivityTicketsHistoryDetailsBinding
    override fun initializeViewModel() {}

    override fun observeViewModel() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTicketsHistoryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        displayReplies()
        getTicketID()

    }

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

    }
    private fun displayDetails(ticket: Item) {
        binding.ticketTitle.text = ticket.title
        binding.ticketNo.text = ticket.number
        binding.time.text = utils.getDateFromStart(this, ticket.date, timeZoneOffset)
        binding.content.text = ticket.content
    }
}