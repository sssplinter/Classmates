const {ObjectID} = require("mongodb");
module.exports = userInfoRoute

function userInfoRoute(app, database) {
    app.get("/userInfo", function (request, result) {
        const accessToken = request.headers.access;
        const userId = request.fields.userId;

        if (accessToken == null || userId == null) {
            result.status(400).json({
                "message": "Wrong params",
                "data": null
            });
        } else {
            database.collection("Users").findOne({
                "accessToken": accessToken
            }, function (error, user) {
                if (user != null) {
                    database.collection("Users").findOne({
                        _id: ObjectID(userId.toString())
                    }, function (error, searchUser) {
                        if (searchUser != null) {
                            delete searchUser.password;
                            delete searchUser.accessToken;
                            delete searchUser.university;
                            delete searchUser.chats;
                            delete searchUser.groupChats;
                            delete searchUser.friends;
                            delete searchUser.subscribers;
                            delete searchUser.subscriptions;
                            searchUser["userRole"] = "default";
                            if (user.friends.findIndex(x => x.userId === searchUser._id.toString()) !== -1) {
                                searchUser["userRole"] = "friend";
                            }
                            if (user.subscriptions.findIndex(x => x.userId === searchUser._id.toString()) !== -1) {
                                searchUser["userRole"] = "subscription";
                            }
                            if (user.subscribers.findIndex(x => x.userId === searchUser._id.toString()) !== -1) {
                                searchUser["userRole"] = "subscriber";
                            }
                            result.status(200).json({
                                "message": "Record has been fetched.",
                                "data": searchUser
                            });
                        } else {
                            result.status(400).json({
                                "message": "User not found",
                                "data": null
                            });
                        }
                    });
                } else {
                    result.status(401).json({
                        "message": "User has been logged out",
                        "data": null
                    });
                }
            });
        }
    });
}