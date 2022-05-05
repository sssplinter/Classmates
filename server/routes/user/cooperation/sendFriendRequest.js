const {ObjectID} = require("mongodb");
module.exports = sendFriendRequest

function sendFriendRequest(app, database) {
    app.post("/sendFriendRequest", function (request, result) {
        const accessToken = request.headers.access;
        const userId = request.fields.userId;

        if (accessToken == null || userId == null) {
            result.status(400).json({
                "message": "Wrong params"
            });
        } else {
            database.collection("Users").findOne({
                "accessToken": accessToken
            }, function (error, user) {
                if (user != null) {
                    if (user.friends.findIndex(x => x.userId === userId) !== -1) {
                        result.status(200).json({
                            "message": "You are already friends"
                        });
                        return;
                    }
                    if (user.subscriptions.findIndex(x => x.userId === userId) === -1) {
                        database.collection("Users").updateOne({
                                "accessToken": accessToken
                            }, {
                                $push: {
                                    "subscriptions": {
                                        "userId": ObjectID(userId.toString())
                                    }
                                }
                            }, function (error, _user) {
                                database.collection("Users").findOne({
                                    _id: ObjectID(userId.toString())
                                }, function (error, newUser) {
                                    if (newUser != null) {
                                        if (newUser.friends.findIndex(x => x._id === user._id.toString()) !== -1) {
                                            result.status(200).json({
                                                "message": "You are already friends"
                                            });
                                            return;
                                        }
                                        if (newUser.subscribers.findIndex(x => x._id === user._id.toString()) === -1) {
                                            database.collection("Users").updateOne({
                                                _id: ObjectID(userId.toString())
                                            }, {
                                                $push: {
                                                    "subscribers": {
                                                        "userId": user._id
                                                    }
                                                }
                                            }, function (error, user) {
                                                result.status(200).json({
                                                    "message": "You subscribed successfully"
                                                });
                                            });
                                        } else {
                                            result.status(202).json({
                                                "message": "User had been subscribed on you"
                                            });
                                        }
                                    } else {
                                        result.status(404).json({
                                            "message": "User not found"
                                        });
                                    }
                                });
                            }
                        )
                    } else {
                        result.status(202).json({
                            "message": "You've already subscribed"
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