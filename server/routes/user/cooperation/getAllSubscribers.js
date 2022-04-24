module.exports = getAllSubscribers

function getAllSubscribers(app, database) {
    app.post("/getAllSubscribers", function (request, result) {
        const accessToken = request.headers.access;

        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                result.status(200).json({
                    "status": "success",
                    "subscribers": user.subscribers,
                    "message": "List of all subscribers"
                })
            } else {
                result.status(401).json({
                    "status": "error",
                    "subscribers": null,
                    "message": "User has been logged out"
                });
            }
        });
    });
}