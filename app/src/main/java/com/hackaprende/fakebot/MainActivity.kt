package com.hackaprende.fakebot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackaprende.fakebot.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private val chatMessageList = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter
    private lateinit var binding: ActivityMainBinding
    private val responses = arrayOf("Si", "Pregunta de nuevo", "No", "Es muy probable", "No lo creo",
        "Tal vez", "No se 😓")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatRecycler.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(this)
        binding.chatRecycler.adapter = adapter

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
                chatMessageList.add(chatMessage)
                adapter.submitList(chatMessageList)
                binding.messageEdit.setText("")
                createMessageResponse()
            }
        }
    }

    private fun createMessageResponse() {
        val random = Random().nextInt(responses.size)

        val response = responses[random]
        val chatMessage = ChatMessage(System.currentTimeMillis(), response, false)
        chatMessageList.add(chatMessage)
        adapter.submitList(chatMessageList)
        binding.chatRecycler.scrollToPosition(chatMessageList.size - 1)
    }
}