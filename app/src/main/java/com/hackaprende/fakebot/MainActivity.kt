package com.hackaprende.fakebot

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackaprende.fakebot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ChatAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.chatRecycler.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(this)
        binding.chatRecycler.adapter = adapter

        viewModel.chatMessageListLiveData.observe(this, Observer {
            chatMessageList ->
            adapter.submitList(chatMessageList)
            binding.chatRecycler.scrollToPosition(chatMessageList.size - 1)
            binding.chatEmptyView.visibility = if (chatMessageList.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        setupSendMessageLayout(binding)
    }

    private fun setupSendMessageLayout(binding: ActivityMainBinding) {
        binding.sendMessageButton.setOnClickListener {
            val message = binding.messageEdit.text.toString()
            if (message.isEmpty()) {
                Toast.makeText(this, getString(R.string.message_must_not_be_empty),
                    Toast.LENGTH_SHORT).show()
            } else {
                val chatMessage = ChatMessage(System.currentTimeMillis(), message, true)
                viewModel.addMessage(chatMessage)
                viewModel.createResponse()
                binding.messageEdit.setText("")
            }
        }
    }

}