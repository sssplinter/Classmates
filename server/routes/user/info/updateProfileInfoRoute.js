module.exports = updateProfileInfoRoute

function updateProfileInfoRoute(app, database) {
    app.put("/updateProfileInfo", function (request, result) {
        const accessToken = request.headers.access;
        const name = request.fields.name;
        const surname = request.fields.surname;
        const bio = request.fields.bio;

        if (accessToken == null || name == null || surname == null || bio == null) {
            result.status(400).json({
                "message": "Wrong params"
            });
        } else {
            database.collection("Users").findOne({
                "accessToken": accessToken
            }, function (error, user) {
                if (user != null) {
                    database.collection("Users").updateOne({
                            "accessToken": accessToken
                        },
                        {
                            $set: {
                                "name": name,
                                "surname": surname,
                                "bio": bio,
                            }
                        }
                    )
                    result.status(200).json({
                        "message": "Data have been changed"
                    });
                } else {
                    result.status(401).json({
                        "message": "User has been logged out"
                    });
                }
            });
        }
    });
}