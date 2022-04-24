module.exports = getAllSubscriptions

function getAllSubscriptions(app, database) {
    app.post("/getAllSubscriptions", function (request, result) {
        const accessToken = request.headers.access;

        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                result.status(200).json({
                    "status": "success",
                    "subscriptions": user.subscriptions,
                    "message": "List of all subscriptions"
                })
            } else {
                result.status(401).json({
                    "status": "error",
                    "subscriptions": null,
                    "message": "User has been logged out"
                });
            }
        });
    });
}