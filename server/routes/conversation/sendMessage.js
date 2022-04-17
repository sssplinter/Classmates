module.exports = sendMessage

function sendMessage(app, database) {
    app.post("/sendMessage", function (request, result) {
        const accessToken = request.fields.accessToken;
        const chatId = request.fields.chatId;
        const messageText = request.fields.messageText;

        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                database.collection("messages").insertOne({
                    chatId: chatId,
                    fromUserId: user._id,
                    messageText: messageText,
                    sendDate: Date.now()
                }, function (error, data) {
                    result.json({
                        "status": "success",
                        "message": "Message had been send"
                    });
                });
            } else {
                result.json({
                    "status": "error",
                    "message": "User has been logged out"
                });
            }
        });
    });
}