module.exports = updateFullNameRoute

function updateFullNameRoute(app, database) {
    app.put("/updateFullName", function (request, result) {
        const accessToken = request.headers.access;
        const name = request.fields.name;
        const surname = request.fields.surname;

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
                result.status(200).json({
                    "message": "Data have been changed"
                });
            } else {
                result.status(401).json({
                    "message": "User has been logged out"
                });
            }
        });
    });
}