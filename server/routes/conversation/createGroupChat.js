const {ObjectId, ObjectID} = require("mongodb");
module.exports = createGroupChat

function createGroupChat(app, database) {
    app.post("/createGroupChat", function (request, result) {
        const accessToken = request.headers.access;
        const groupName = request.fields.groupName;
        const messageText = request.fields.messageText;
        const usersId = JSON.parse(request.fields.usersId);

        if (accessToken == null || groupName == null || messageText == null || usersId == null) {
            console.log("wrong params")
            result.status(400).json({
                "message": "Wrong params"
            });
        } else {
            database.collection("Users").findOne({
                "accessToken": accessToken
            }, function (error, fromUser) {
                console.log("user  " + fromUser)
                if (fromUser != null) {
                    for (let i = 0; i < usersId.length; i++) {
                        if (fromUser.friends.findIndex(x => x.userId.toString() === usersId[i]) === -1) {
                            console.log("friend")
                            console.log(fromUser.friends[0].userId)
                            console.log(fromUser.friends[0].userId.toString())
                            console.log(usersId)
                            result.status(400).json({
                                "message": "Wrong params"
                            });
                            return
                        }
                    }
                    console.log("completed")

                    let groupChatId = ObjectId()
                    database.collection("GroupChat").insertOne({
                        _id: groupChatId,
                        groupName: groupName,
                        people: usersId,
                        photoUrl: ""
                    });
                    let messageId = ObjectId()
                    let chatId = ObjectId()

                    database.collection("Chat").insertOne({
                        _id: chatId,
                        creationDate: Date.now(),
                        lastMessageId: messageId,
                        isGroup: true,
                        subChatId: groupChatId
                    })

                    database.collection("Messages").insertOne({
                        _id: messageId,
                        chatId: chatId,
                        fromUserId: fromUser._id,
                        fromUserName: fromUser.name + " " + fromUser.surname,
                        messageText: messageText,
                        sendDate: Date.now()
                    });

                    let userMyChatStatId = ObjectId()
                    database.collection("MyChatStat").insertOne({
                        _id: userMyChatStatId,
                        chatId: chatId,
                        unreadMessagesAmount: 1
                    });
                    database.collection("Users").updateOne({
                            _id: fromUser._id
                        },
                        {$push: {"chats": {"chatId": userMyChatStatId}}}
                    );
                    usersId.forEach(userId => {
                        let userChatStatId = ObjectId()
                        database.collection("MyChatStat").insertOne({
                            _id: userChatStatId,
                            chatId: chatId,
                            unreadMessagesAmount: 1
                        });
                        database.collection("Users").updateOne({
                                _id: ObjectID(userId.toString())
                            },
                            {$push: {"chats": {"chatId": userChatStatId}}}
                        );
                    })
                    result.status(200).json({
                        "message": "success"
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