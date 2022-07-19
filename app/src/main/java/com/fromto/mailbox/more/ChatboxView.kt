package com.fromto.mailbox.more

import com.fromto.retrofit.ChatboxResponse

interface ChatboxView {
    fun onLoading()

    fun onChatBoxSuccess(response: ChatboxResponse)
}
