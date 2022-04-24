const {ObjectID} = require("mongodb");
module.exports = removeSubscription

function removeSubscription(app, database) {
    app.post("/removeSubscription", function (request, result) {
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
                    if (userFrom.subscriptions.findIndex(x => x.userId.toString() === userId) !== -1) {
                        for (let i = 0; i < userFrom.subscriptions.length; i++) {
                            if (userFrom.subscriptions[i].userId.toString() === userId) {
                                userFrom.subscriptions.splice(i, 1);
                                database.collection("users").updateOne({
                                        "accessToken": accessToken
                                    },
                                    {
                                        $set: {
                                            "subscriptions": userFrom.subscriptions
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
                                if (toUser.subscribers.findIndex(x => x.userId.toString() === userFrom._id.toString()) !== -1) {
                                    for (let i = 0; i < toUser.subscribers.length; i++) {
                                        if (toUser.subscribers[i].userId.toString() === userFrom._id.toString()) {
                                            toUser.subscribers.splice(i, 1);
                                            database.collection("users").updateOne({
                                                    _id: ObjectID(userId.toString())
                                                },
                                                {
                                                    $set: {
                                                        "subscribers": toUser.subscribers
                                                    }
                                                }
                                            );
                                            result.status(200).json({
                                                "status": "success",
                                                "message": "Subscription was removed"
                                            });
                                            break;
                                        }
                                    }
                                } else {
                                    result.status(202).json({
                                        "status": "success",
                                        "message": "You didn't subscribe on that user"
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
                            "message": "You don't have subscription"
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