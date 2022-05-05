const {ObjectID} = require("mongodb");
module.exports = getChatMessages

function getChatMessages(app, database) {
    app.post("/getMessages", function (request, result) {
        const accessToken = request.headers.access;
        const chatId = request.fields.chatId;

        if (accessToken == null || chatId == null) {
            result.status(400).json({
                "data": null,
                "message": "Wrong params"
            });
        } else {
            database.collection("Users").findOne({
                "accessToken": accessToken
            }, function (error, user) {
                if (user != null) {
                    if (user.chats.findIndex(x => x.chatId.toString() === chatId) === -1) {
                        result.status(403).json({
                            "data": null,
                            "message": "You don't have permission"
                        });
                        return
                    }
                    database.collection("MyChatStat").findOne({
                        _id: ObjectID(chatId.toString()),
                    }, function (error, chat) {
                        database.collection("Messages").find({
                            "chatId": chat.chatId
                        }).toArray(function (error, messages) {
                            if (messages != null) {
                                result.status(200).json({
                                    "data": messages,
                                    "message": "List of messages"
                                });
                            } else {
                                result.status(400).json({
                                    "data": null,
                                    "message": "Wrong chat info"
                                });
                            }
                        });
                    });
                } else {
                    result.status(401).json({
                        "data": null,
                        "message": "User has been logged out"
                    });
                }
            });
        }
    });
}