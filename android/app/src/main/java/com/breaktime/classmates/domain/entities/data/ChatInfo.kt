package com.breaktime.classmates.domain.entities.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatInfo(
    val id: String,
    val name: String,
    val photoUrl: String,
    val lastMessage: String,
    val lastMessageDate: Long,
    val unreadMessagesAmount: Int,
): Parcelable