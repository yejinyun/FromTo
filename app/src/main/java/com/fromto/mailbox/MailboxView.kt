package com.fromto.mailbox

import com.fromto.retrofit.MailboxResponse
import com.fromto.retrofit.PosterResponse

interface MailboxView {
    fun onLoading()

    fun onLetterBoxSuccess(response: MailboxResponse)
}
