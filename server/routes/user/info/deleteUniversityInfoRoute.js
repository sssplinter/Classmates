module.exports = deleteUniversityInfo

function deleteUniversityInfo(app, database) {
    app.delete("/deleteUniversityInfo", function (request, result) {
        const accessToken = request.headers.access;
        const id = request.fields.id;

        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user == null) {
                result.json({
                    "status": "error",
                    "message": "User has been logged out"
                });
            } else {
                if (user.education != null) {
                    for (let i = 0; i < user.education.length; i++) {
                        if (user.education[i]._id.toString() === id) {
                            user.education.splice(i, 1);
                            database.collection("users").updateOne({
                                    "accessToken": accessToken
                                },
                                {
                                    $set: {
                                        "education": user.education
                                    }
                                }
                            );
                            result.json({
                                "status": "success",
                                "message": "Data have been changed"
                            });
                            return;
                        }
                    }
                    result.json({
                        "status": "error",
                        "message": "Wrong id"
                    });
                }
            }
        });
    });
}