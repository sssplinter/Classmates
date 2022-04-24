const {ObjectId} = require("mongodb");
module.exports = sendMessage

function sendMessage(app, database) {
    app.post("/sendMessage", function (request, result) {
        const accessToken =  request.headers.access;
        const chatId = request.fields.chatId;
        const messageText = request.fields.messageText;
        if (accessToken == null || chatId == null || messageText == null) {
            result.status(400).json({
                "status": "error",
                "message": "Wrong params"
            });
        } else {
            database.collection("users").findOne({
                "accessToken": accessToken
            }, function (error, user) {
                if (user != null) {
                    database.collection("messages").insertOne({
                        _id: ObjectId(),
                        chatId: chatId,
                        fromUserId: user._id,
                        messageText: messageText,
                        sendDate: Date.now()
                    }, function (error, data) {
                        result.status(200).json({
                            "status": "success",
                            "message": "Message had been send"
                        });
                    });
                } else {
                    result.status(401).json({
                        "status": "error",
                        "message": "User has been logged out"
                    });
                }
            });
        }
    });
}