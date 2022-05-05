module.exports = profileInfoRoute

function profileInfoRoute(app, database) {
    app.get("/profileInfo", function (request, result) {
        const accessToken = request.headers.access;
        database.collection("Users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                delete user.password;
                delete user.accessToken;
                user["userRole"] = "me";
                result.status(200).json({
                    "message": "Record has been fetched.",
                    "data": user
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