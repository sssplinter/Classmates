const {ObjectId, ObjectID} = require("mongodb");
module.exports = sendMessage

function sendMessage(app, database) {
    app.post("/sendMessage", function (request, result) {
        const accessToken = request.headers.access;
        const chatId = request.fields.chatId;
        const messageText = request.fields.messageText;
        if (accessToken == null || chatId == null || messageText == null) {
            result.status(400).json({
                "message": "Wrong params"
            });
        } else {
            database.collection("Users").findOne({
                "accessToken": accessToken
            }, function (error, user) {
                if (user != null) {
                    database.collection("MyChatStat").findOne({
                        _id: ObjectID(chatId.toString()),
                    }, function (error, chat) {
                        let messageId = ObjectId()
                        database.collection("Messages").insertOne({
                            _id: messageId,
                            chatId: chat.chatId,
                            fromUserName: user.name + " " + user.surname,
                            fromUserId: user._id,
                            messageText: messageText,
                            sendDate: Date.now()
                        });

                        database.collection("Chat").updateOne({
                                _id: chat.chatId,
                            },
                            {$set: {lastMessageId: messageId}}
                        );

                        database.collection("MyChatStat").updateMany({
                                chatId: chat.chatId,
                            },
                            {$inc: {unreadMessagesAmount: 1}}
                        );

                        result.status(200).json({
                            "message": "Message had been send"
                        });
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