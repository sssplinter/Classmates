const {ObjectID} = require("mongodb");
module.exports = removeFriend

function removeFriend(app, database) {
    app.post("/removeFriend", function (request, result) {
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
                        for (let i = 0; i < user.friends.length; i++) {
                            if (user.friends[i].userId.toString() === userId) {
                                user.friends.splice(i, 1);
                                database.collection("Users").updateOne({
                                        "accessToken": accessToken
                                    },
                                    {
                                        $set: {
                                            "friends": user.friends
                                        }
                                    }
                                );
                                break;
                            }
                        }
                        database.collection("Users").findOne({
                            _id: ObjectID(userId.toString())
                        }, function (error, newUser) {
                            if (newUser != null) {
                                if (newUser.friends.findIndex(x => x.userId === user._id.toString()) !== -1) {
                                    for (let i = 0; i < newUser.friends.length; i++) {
                                        if (newUser.friends[i].userId.toString() === user._id.toString()) {
                                            newUser.friends.splice(i, 1);
                                            database.collection("Users").updateOne({
                                                    _id: ObjectID(userId.toString())
                                                },
                                                {
                                                    $set: {
                                                        "friends": newUser.friends
                                                    }
                                                }
                                            );
                                            result.status(200).json({
                                                "message": "User had been removed"
                                            });
                                            break;
                                        }
                                    }
                                } else {
                                    result.status(202).json({
                                        "message": "User hadn't been your friend"
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
                            "message": "Users isn't your friend"
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