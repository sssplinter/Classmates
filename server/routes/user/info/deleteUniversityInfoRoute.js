module.exports = deleteUniversityInfo

function deleteUniversityInfo(app, database) {
    app.delete("/deleteUniversityInfo", function (request, result) {
        const accessToken = request.headers.access;
        const id = request.fields.id;

        database.collection("Users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user == null) {
                result.json({
                    "message": "User has been logged out"
                });
            } else {
                if (user.education != null) {
                    for (let i = 0; i < user.education.length; i++) {
                        if (user.education[i]._id.toString() === id) {
                            user.education.splice(i, 1);
                            database.collection("Users").updateOne({
                                    "accessToken": accessToken
                                },
                                {
                                    $set: {
                                        "education": user.education
                                    }
                                }
                            );
                            result.json({
                                "message": "Data have been changed"
                            });
                            return;
                        }
                    }
                    result.json({
                        "message": "Wrong id"
                    });
                }
            }
        });
    });
}