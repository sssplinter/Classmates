module.exports = getChatsInfo

const {ObjectId, ObjectID} = require("mongodb");

function getChatsInfo(app, database) {
    app.post("/getChatsInfo", function (request, result) {
        const accessToken = request.headers.access;

        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                let chatsInfo = []
                let id = "", name = "", photoUrl = "", lastMessage = "", lastMessageData = "", unreadMessagesAmount = ""
                user.chats.map(function (arrayInfo) {
                    database.collection("MyChatStat").findOne({
                        _id: arrayInfo.chatId
                    }, function (error, myChatStat) {
                        id = myChatStat.chatId
                        unreadMessagesAmount = myChatStat.unreadMessagesAmount

                        database.collection("Chat").findOne({
                            _id: myChatStat.chatId
                        }, function (error, chat) {
                            database.collection("messages").findOne({
                                _id: chat.lastMessageId
                            }, function (error, message) {
                                lastMessage = message.messageText
                                lastMessageData = message.sendDate
                            });
                            if (chat.isGroup) {
                                database.collection("GroupChat").findOne({
                                    _id: chat.subChatId
                                }, function (error, groupChat) {
                                    name = groupChat.name
                                    photoUrl = groupChat.photoUrl
                                });
                            } else {
                                database.collection("PrivateChat").findOne({
                                    _id: chat.subChatId
                                }, function (error, privateChat) {
                                    if (user._id === privateChat.user2) {
                                        database.collection("users").findOne({
                                            _id: ObjectID(privateChat.user1)
                                        }, function (error, user) {
                                            name = user.name + " " + user.surname
                                            photoUrl = user.photoUrl
                                        });
                                    } else {
                                        database.collection("users").findOne({
                                            _id: ObjectID(privateChat.user2)
                                        }, function (error, user) {
                                            name = user.name + " " + user.surname
                                            photoUrl = user.photoUrl
                                        });
                                    }
                                    chatsInfo.push({
                                        id: id,
                                        name: name,
                                        photoUrl: photoUrl,
                                        lastMessage: lastMessage,
                                        lastMessageData: lastMessageData,
                                        unreadMessagesAmount: unreadMessagesAmount
                                    })
                                });
                            }
                        });
                    });
                })
                setTimeout(function () {
                    result.json({
                        "status": "success",
                        "chatInfo": chatsInfo,
                        "message": "Chat exist"
                    });
                }, 1000);
            } else {
                result.json({
                    "status": "error",
                    "chatInfo": null,
                    "message": "Wrong access token"
                });
            }
        });
    });
}