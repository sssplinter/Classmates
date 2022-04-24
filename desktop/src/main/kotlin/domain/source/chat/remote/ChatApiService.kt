package domain.source.chat.remote

import domain.entities.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApiService {
    @POST("/sendMessage")
    suspend fun sendMessage(@Body body: ChatBody): Response<AuthResponse>

    suspend fun deleteMessage(@Body body: ChatBody): Response<AuthResponse>

    suspend fun getChatMessages(@Body body: ChatBody): Response<AuthResponse>

    suspend fun getAllMessages(@Body body: ChatBody): Response<AuthResponse>

    data class ChatBody(
        val accessToken: String = "",
        val messageText: String = "",
        val chatId: Int = -1,
        val messageId: Int = -1,
    )
}