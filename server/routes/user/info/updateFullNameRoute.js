module.exports = updateFullNameRoute

function updateFullNameRoute(app, database) {
    app.put("/updateFullName", function (request, result) {
        const accessToken = request.headers.access;
        const name = request.fields.name;
        const surname = request.fields.surname;
        const patronim = request.fields.patronim;

        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user == null) {
                result.json({
                    "status": "error",
                    "message": "User has been logged out"
                });
            } else {
                database.collection("users").updateOne({
                        "accessToken": accessToken
                    },
                    {
                        $set: {
                            "name": name,
                            "surname": surname,
                            "patronim": patronim,
                        }
                    }
                )
                result.json({
                    "status": "success",
                    "message": "Data have been changed"
                });
            }
        });
    });
}