module.exports = getChatsInfo

const {ObjectId, ObjectID} = require("mongodb");

function getChatsInfo(app, database) {
    app.post("/getChatsInfo", function (request, result) {
        const accessToken = request.headers.access;

        database.collection("Users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                let chatsInfo = []
                user.chats.map(function (arrayInfo) {
                    let chatId = arrayInfo.chatId
                    database.collection("MyChatStat").findOne({
                        _id: arrayInfo.chatId
                    }, function (error, myChatStat) {
                        let unreadMessagesAmount = myChatStat.unreadMessagesAmount
                        let lastMessage = "", lastMessageDate = ""
                        database.collection("Chat").findOne({
                            _id: myChatStat.chatId
                        }, function (error, chat) {
                            database.collection("Messages").findOne({
                                _id: chat.lastMessageId
                            }, function (error, message) {
                                lastMessage = message.messageText
                                lastMessageDate = message.sendDate
                            });
                            if (chat.isGroup) {
                                database.collection("GroupChat").findOne({
                                    _id: chat.subChatId
                                }, function (error, groupChat) {
                                    let name = groupChat.groupName
                                    let photoUrl = groupChat.photoUrl
                                    chatsInfo.push({
                                        id: chatId,
                                        name: name,
                                        photoUrl: photoUrl + "",
                                        lastMessage: lastMessage,
                                        lastMessageDate: lastMessageDate,
                                        unreadMessagesAmount: unreadMessagesAmount
                                    })
                                });
                            } else {
                                database.collection("PrivateChat").findOne({
                                    _id: chat.subChatId
                                }, function (error, privateChat) {
                                    if (user._id === privateChat.user2) {
                                        database.collection("Users").findOne({
                                            _id: ObjectID(privateChat.user1)
                                        }, function (error, user) {
                                            let name = user.name + " " + user.surname
                                            let photoUrl = user.profileImageUrl
                                            chatsInfo.push({
                                                id: chatId,
                                                name: name,
                                                photoUrl: photoUrl + "",
                                                lastMessage: lastMessage,
                                                lastMessageDate: lastMessageDate,
                                                unreadMessagesAmount: unreadMessagesAmount
                                            })
                                        });
                                    } else {
                                        database.collection("Users").findOne({
                                            _id: ObjectID(privateChat.user2)
                                        }, function (error, user) {
                                            let name = user.name + " " + user.surname
                                            let photoUrl = user.profileImageUrl
                                            chatsInfo.push({
                                                id: chatId,
                                                name: name,
                                                photoUrl: photoUrl + "",
                                                lastMessage: lastMessage,
                                                lastMessageDate: lastMessageDate,
                                                unreadMessagesAmount: unreadMessagesAmount
                                            })
                                        });
                                    }
                                });
                            }
                        });
                    });
                })
                setTimeout(function () {
                    result.status(200).json({
                        "data": chatsInfo,
                        "message": "Chat exist"
                    });
                }, 1500);
            } else {
                result.status(401).json({
                    "data": null,
                    "message": "Wrong access token"
                });
            }
        });
    });
}