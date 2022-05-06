module.exports = updateFullNameRoute

function updateFullNameRoute(app, database) {
    app.put("/updateFullName", function (request, result) {
        const accessToken = request.headers.access;
        const name = request.fields.name;
        const surname = request.fields.surname;

        if (accessToken == null || name == null || surname == null) {
            console.log("400 1")
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
                            }
                        }
                    )
                    console.log("200")
                    result.status(200).json({
                        "message": "Data have been changed"
                    });
                } else {
                    console.log("401")
                    result.status(401).json({
                        "message": "User has been logged out"
                    });
                }
            });
        }
    });
}