const {ObjectID} = require("mongodb");
module.exports = removeMessage

function removeMessage(app, database) {
    app.post("/removeMessage", function (request, result) {
        const accessToken = request.headers.access;
        const chatId = request.fields.chatId;
        const messageId = request.fields.messageId;
        if (accessToken == null || chatId == null || messageId == null) {
            result.status(400).json({
                "status": "error",
                "message": "Wrong params"
            });
        } else {
            database.collection("users").findOne({
                "accessToken": accessToken
            }, function (error, user) {
                if (user != null) {
                    database.collection("messages").findOne({
                        _id: ObjectID(messageId.toString()),
                        chatId: chatId
                    }, function (error, message) {
                        if (message.fromUserId.toString() === user._id.toString()) {
                            database.collection("messages").remove({
                                _id: ObjectID(messageId.toString()),
                                chatId: chatId
                            }, function (error, message) {
                                if (!error) {
                                    result.status(200).json({
                                        "status": "success",
                                        "message": "Message had been removed"
                                    });
                                } else {
                                    result.status(500).json({
                                        "status": "error",
                                        "message": "Something went wrong"
                                    });
                                }
                            });
                        } else {
                            result.status(403).json({
                                "status": "error",
                                "message": "Access denied"
                            });
                        }
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
