module.exports = getAllFriends

function getAllFriends(app, database) {
    app.post("/getAllFriends", function (request, result) {
        const accessToken = request.headers.access;

        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                result.status(200).json({
                    "status": "success",
                    "friends": user.friends,
                    "message": "User has been logged out"
                })
            } else {
                result.status(401).json({
                    "status": "error",
                    "friends": null,
                    "message": "User has been logged out"
                });
            }
        });
    });
}