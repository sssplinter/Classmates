const {ObjectID} = require("mongodb");
module.exports = sendFriendRequest

function sendFriendRequest(app, database) {
    app.post("/sendFriendRequest", function (request, result) {
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
            }, function (error, user) {
                if (user != null) {
                    if (user.friends.findIndex(x => x.userId === userId) !== -1) {
                        result.status(200).json({
                            "status": "success",
                            "message": "You are already friends"
                        });
                        return;
                    }
                    if (user.subscription.findIndex(x => x.userId === userId) === -1) {
                        database.collection("users").updateOne({
                                "accessToken": accessToken
                            }, {
                                $push: {
                                    "subscription": {
                                        "userId": userId
                                    }
                                }
                            }, function (error, _user) {
                                database.collection("users").findOne({
                                    _id: ObjectID(userId.toString())
                                }, function (error, newUser) {
                                    if (newUser != null) {
                                        if (newUser.friends.findIndex(x => x._id === user._id.toString()) !== -1) {
                                            result.status(200).json({
                                                "status": "success",
                                                "message": "You are already friends"
                                            });
                                            return;
                                        }
                                        if (newUser.subscribers.findIndex(x => x._id === user._id.toString()) === -1) {
                                            database.collection("users").updateOne({
                                                _id: ObjectID(userId.toString())
                                            }, {
                                                $push: {
                                                    "subscribers": {
                                                        "userId": user._id
                                                    }
                                                }
                                            }, function (error, user) {
                                                result.status(200).json({
                                                    "status": "success",
                                                    "message": "You subscribed successfully"
                                                });
                                            });
                                        } else {
                                            result.status(202).json({
                                                "status": "success",
                                                "message": "User had been subscribed on you"
                                            });
                                        }
                                    } else {
                                        result.status(404).json({
                                            "status": "error",
                                            "message": "User not found"
                                        });
                                    }
                                });
                            }
                        )
                    } else {
                        result.status(202).json({
                            "status": "success",
                            "message": "You've already subscribed"
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