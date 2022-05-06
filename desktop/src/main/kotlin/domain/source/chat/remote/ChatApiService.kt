package domain.source.chat.remote

import domain.entities.data.CurrentUser
import domain.entities.response.AuthResponse
import domain.entities.response.ChatInfoResponse
import domain.entities.response.ChatMessagesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatApiService {
    @POST("/sendMessage")
    suspend fun sendMessage(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: ChatBody,
    ): Response<AuthResponse>

    @POST("/getChatsInfo")
    suspend fun getChatsInfo(
        @Header("Access") token: String = CurrentUser.token,
    ): Response<ChatInfoResponse>

    @POST("/getMessages")
    suspend fun getChatMessages(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: ChatBody,
    ): Response<ChatMessagesResponse>

    @POST("/sendPrivateMessage")
    suspend fun sendPrivateMessage(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: ChatBody,
    ): Response<ChatMessagesResponse>

    @POST("/createGroupChat")
    suspend fun createGroupChat(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: GroupBody,
    ): Response<Void>

    data class ChatBody(
        val messageText: String = "",
        val chatId: String = "",
        val userId: String = "",
    )

    data class GroupBody(
        val groupName: String,
        val messageText: String,
        val usersId: String,
    )
}