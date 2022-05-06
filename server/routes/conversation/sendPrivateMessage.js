const {ObjectId, ObjectID} = require("mongodb");
module.exports = sendPrivateMessage

function sendPrivateMessage(app, database) {
    app.post("/sendPrivateMessage", function (request, result) {
        const accessToken = request.headers.access;
        const toUserId = request.fields.userId;
        const messageText = request.fields.messageText;

        if (accessToken == null || toUserId == null || messageText == null) {
            result.status(400).json({
                "message": "Wrong params"
            });
        } else {
            database.collection("Users").findOne({
                "accessToken": accessToken
            }, function (error, fromUser) {
                if (fromUser != null) {
                    for (let i = 0; i < fromUser.friends.length; i++) {
                        if (fromUser.friends[i].userId.toString() === toUserId) {
                            database.collection("PrivateChat").findOne({
                                $or: [
                                    {user1: fromUser._id.toString(), user2: toUserId},
                                    {user1: toUserId, user2: fromUser._id.toString()}
                                ]
                            }, function (error, privateChat) {
                                if (privateChat == null) {
                                    let privateChatId = ObjectId()
                                    database.collection("PrivateChat").insertOne({
                                        _id: privateChatId,
                                        user1: fromUser._id.toString(),
                                        user2: toUserId
                                    });
                                    let messageId = ObjectId()
                                    let chatId = ObjectId()

                                    database.collection("Chat").insertOne({
                                        _id: chatId,
                                        creationDate: Date.now(),
                                        lastMessageId: messageId,
                                        isGroup: false,
                                        subChatId: privateChatId
                                    })

                                    database.collection("Messages").insertOne({
                                        _id: messageId,
                                        chatId: chatId,
                                        fromUserName: fromUser.name + " " + fromUser.surname,
                                        fromUserId: fromUser._id,
                                        messageText: messageText,
                                        sendDate: Date.now()
                                    });

                                    let user1ChatStatId = ObjectId()
                                    database.collection("MyChatStat").insertOne({
                                        _id: user1ChatStatId,
                                        chatId: chatId,
                                        unreadMessagesAmount: 1
                                    });

                                    let user2ChatStatId = ObjectId()
                                    database.collection("MyChatStat").insertOne({
                                        _id: user2ChatStatId,
                                        chatId: chatId,
                                        unreadMessagesAmount: 1
                                    });
                                    database.collection("Users").updateOne({
                                            _id: ObjectID(toUserId.toString())
                                        },
                                        {$push: {"chats": {"chatId": user1ChatStatId}}}
                                    );
                                    database.collection("Users").updateOne({
                                            _id: ObjectID(fromUser._id.toString())
                                        },
                                        {$push: {"chats": {"chatId": user2ChatStatId}}}
                                    );
                                } else {
                                    database.collection("Chat").findOne({
                                        subChatId: privateChat._id
                                    }, function (error, chat) {
                                        let messageId = ObjectId()
                                        database.collection("Messages").insertOne({
                                            _id: messageId,
                                            chatId: chat._id,
                                            fromUserName: fromUser.name + " " + fromUser.surname,
                                            fromUserId: fromUser._id,
                                            messageText: messageText,
                                            sendDate: Date.now()
                                        });

                                        database.collection("Chat").updateOne({
                                                _id: chat._id
                                            },
                                            {$set: {lastMessageId: messageId}}
                                        );

                                        database.collection("MyChatStat").updateMany({
                                                chatId: chat._id
                                            },
                                            {$inc: {unreadMessagesAmount: 1}}
                                        );
                                    });
                                }
                                result.status(200).json({
                                    "message": "Message had been send"
                                });
                            });
                            return;
                        }
                    }
                    result.status(403).json({
                        "message": "User isn't your friend"
                    });
                } else {
                    result.status(401).json({
                        "message": "User has been logged out"
                    });
                }
            });
        }
    });
}