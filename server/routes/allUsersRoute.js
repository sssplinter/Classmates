module.exports = allUsersRoute

function allUsersRoute(app, database) {
    app.get("/getAllUsers", function (request, result) {
        const accessToken = request.headers.access;
        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (accessToken == null || user != null) {
                database.collection("users").find().toArray(function (error, data) {
                    const modifiedData = data.map(userData => {
                        delete userData.password;
                        delete userData.accessToken;
                        if (user == null) {
                            delete userData.education;
                        }
                        return userData;
                    })
                    result.json({
                        "status": "success",
                        "message": "All users",
                        "data": modifiedData
                    });
                });
            } else {
                result.json({
                    "status": "error",
                    "message": "User has been logged out"
                });
            }
        });
    });
}