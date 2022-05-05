const {ObjectID} = require("mongodb");
module.exports = rejectFriendRequest

function rejectFriendRequest(app, database) {
    app.post("/rejectFriendRequest", function (request, result) {
        const accessToken = request.headers.access;
        const userId = request.fields.userId;

        if (accessToken == null || userId == null) {
            result.status(400).json({
                "message": "Wrong params"
            });
        } else {
            database.collection("Users").findOne({
                "accessToken": accessToken
            }, function (error, userFrom) {
                if (userFrom != null) {
                    if (userFrom.subscribers.findIndex(x => x.userId.toString() === userId) !== -1) {
                        for (let i = 0; i < userFrom.subscribers.length; i++) {
                            if (userFrom.subscribers[i].userId.toString() === userId) {
                                userFrom.subscribers.splice(i, 1);
                                database.collection("Users").updateOne({
                                        "accessToken": accessToken
                                    },
                                    {
                                        $set: {
                                            "subscribers": userFrom.subscribers
                                        }
                                    }
                                );
                                break;
                            }
                        }

                        database.collection("Users").findOne({
                            _id: ObjectID(userId.toString())
                        }, function (error, toUser) {
                            if (toUser != null) {
                                if (toUser.subscriptions.findIndex(x => x.userId.toString() === userFrom._id.toString()) !== -1) {
                                    for (let i = 0; i < toUser.subscriptions.length; i++) {
                                        if (toUser.subscriptions[i].userId.toString() === userFrom._id.toString()) {
                                            toUser.subscriptions.splice(i, 1);
                                            database.collection("Users").updateOne({
                                                    _id: ObjectID(userId.toString())
                                                },
                                                {
                                                    $set: {
                                                        "subscriptions": toUser.subscriptions
                                                    }
                                                }
                                            );
                                            result.status(200).json({
                                                "message": "User had been rejected"
                                            });
                                            break;
                                        }
                                    }
                                } else {
                                    result.status(202).json({
                                        "message": "User hadn't been subscribed on you"
                                    });
                                }
                            } else {
                                result.status(404).json({
                                    "message": "User not found"
                                });
                            }
                        });
                    } else {
                        result.status(202).json({
                            "message": "You don't have such subscriber"
                        });
                    }
                } else {
                    result.status(401).json({
                        "message": "User has been logged out"
                    });
                }
            });
        }
    });
}