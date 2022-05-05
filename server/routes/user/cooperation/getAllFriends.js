module.exports = getAllFriends

function getAllFriends(app, database) {
    app.post("/getAllFriends", function (request, result) {
        const accessToken = request.headers.access;

        database.collection("Users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                database.collection("Users").find().toArray(function (error, data) {
                    const modifiedData = data
                        .filter(userData => {
                            return user.friends.findIndex(x => x.userId.toString() === userData._id.toString()) !== -1
                        })
                        .map(userData => {
                            delete userData.password;
                            delete userData.accessToken;
                            delete userData.university;
                            delete userData.chats;
                            delete userData.groupChats;
                            delete userData.friends;
                            delete userData.subscribers;
                            delete userData.subscriptions;
                            userData["userRole"] = "friend";
                            return userData;
                        })
                    result.status(200).json({
                        "message": "All friends",
                        "data": modifiedData
                    });
                });
            } else {
                result.status(401).json({
                    "message": "User has been logged out",
                    "data": null
                });
            }
        });
    });
}