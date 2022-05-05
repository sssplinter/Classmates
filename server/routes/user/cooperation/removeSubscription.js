const {ObjectID} = require("mongodb");
module.exports = removeSubscription

function removeSubscription(app, database) {
    app.post("/removeSubscription", function (request, result) {
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
                    if (userFrom.subscriptions.findIndex(x => x.userId.toString() === userId) !== -1) {
                        for (let i = 0; i < userFrom.subscriptions.length; i++) {
                            if (userFrom.subscriptions[i].userId.toString() === userId) {
                                userFrom.subscriptions.splice(i, 1);
                                database.collection("Users").updateOne({
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

                        database.collection("Users").findOne({
                            _id: ObjectID(userId.toString())
                        }, function (error, toUser) {
                            if (toUser != null) {
                                if (toUser.subscribers.findIndex(x => x.userId.toString() === userFrom._id.toString()) !== -1) {
                                    for (let i = 0; i < toUser.subscribers.length; i++) {
                                        if (toUser.subscribers[i].userId.toString() === userFrom._id.toString()) {
                                            toUser.subscribers.splice(i, 1);
                                            database.collection("Users").updateOne({
                                                    _id: ObjectID(userId.toString())
                                                },
                                                {
                                                    $set: {
                                                        "subscribers": toUser.subscribers
                                                    }
                                                }
                                            );
                                            result.status(200).json({
                                                "message": "Subscription was removed"
                                            });
                                            break;
                                        }
                                    }
                                } else {
                                    result.status(202).json({
                                        "message": "You didn't subscribe on that user"
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
                            "message": "You don't have subscription"
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