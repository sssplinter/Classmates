module.exports = getChat

const {ObjectId} = require("mongodb");

function getChat(app, database) {
    app.post("/getChat", function (request, result) {
        const accessToken = request.fields.accessToken;
        const toUser = request.fields.toUser;

        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                let fromUser = user._id;
                database.collection("chats").findOne({
                    $or: [
                        {fromUser: fromUser, toUser: toUser},
                        {fromUser: toUser, toUser: fromUser}
                    ]
                }, function (error, chatInfo) {
                    if (chatInfo == null) {
                        let insertionData = {
                            _id: ObjectId(),
                            fromUser: fromUser,
                            toUser: toUser,
                            creationDate: Date.now()
                        };
                        database.collection("chats").insertOne(insertionData, function (error, data) {
                            result.json({
                                "status": "success",
                                "chatInfo": insertionData,
                                "message": "Chat had been created"
                            });
                        });
                    } else {
                        result.json({
                            "status": "success",
                            "chatInfo": chatInfo,
                            "message": "Chat exist"
                        });
                    }
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