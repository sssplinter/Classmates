module.exports = getMessages

function getMessages(app, database) {
    app.post("/getMessages", function (request, result) {
        const chatId = request.fields.chatId;

        database.collection("messages").find({
            "chatId": chatId
        }).toArray(function (error, messages) {
            if (messages != null) {
                result.json({
                    "status": "success",
                    "chatInfo": messages,
                    "message": "List of messages"
                });
            } else {
                result.json({
                    "status": "error",
                    "message": "Wrong access token"
                });
            }
        });
    });
}