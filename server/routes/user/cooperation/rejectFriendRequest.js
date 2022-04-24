const {ObjectID} = require("mongodb");
module.exports = rejectFriendRequest

function rejectFriendRequest(app, database) {
    app.post("/rejectFriendRequest", function (request, result) {
        const accessToken = request.headers.access;
        const userId = request.fields.userId;

        if (accessToken == null || userId == null) {
            result.status(400).json({
                "status": "error",
                "message": "Wrong params"
            });
        } else {
            database.collection("users").findOne({
                "accessToken": accessToken
            }, function (error, userFrom) {
                if (userFrom != null) {
                    if (userFrom.subscribers.findIndex(x => x.userId.toString() === userId) !== -1) {
                        for (let i = 0; i < userFrom.subscribers.length; i++) {
                            if (userFrom.subscribers[i].userId.toString() === userId) {
                                userFrom.subscribers.splice(i, 1);
                                database.collection("users").updateOne({
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

                        database.collection("users").findOne({
                            _id: ObjectID(userId.toString())
                        }, function (error, toUser) {
                            if (toUser != null) {
                                if (toUser.subscription.findIndex(x => x.userId.toString() === userFrom._id.toString()) !== -1) {
                                    for (let i = 0; i < toUser.subscription.length; i++) {
                                        if (toUser.subscription[i].userId.toString() === userFrom._id.toString()) {
                                            toUser.subscription.splice(i, 1);
                                            database.collection("users").updateOne({
                                                    _id: ObjectID(userId.toString())
                                                },
                                                {
                                                    $set: {
                                                        "subscription": toUser.subscription
                                                    }
                                                }
                                            );
                                            result.status(200).json({
                                                "status": "success",
                                                "message": "User had been rejected"
                                            });
                                            break;
                                        }
                                    }
                                } else {
                                    result.status(202).json({
                                        "status": "success",
                                        "message": "User hadn't been subscribed on you"
                                    });
                                }
                            } else {
                                result.status(404).json({
                                    "status": "error",
                                    "message": "User not found"
                                });
                            }
                        });
                    } else {
                        result.status(202).json({
                            "status": "success",
                            "message": "You don't have such subscriber"
                        });
                    }
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