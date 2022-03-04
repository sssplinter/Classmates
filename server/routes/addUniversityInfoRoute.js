const {ObjectId} = require("mongodb");
module.exports = addUniversityInfo

function addUniversityInfo(app, database) {
    app.put("/addUniversityInfo", function (request, result) {
            const accessToken = request.headers.access;
            const university = request.fields.university;
            const groupNumber = request.fields.groupNumber;
            const groupCreationDate = request.fields.groupCreationDate;

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
                                $push: {
                                    "education": {
                                        _id: ObjectId(),
                                        "university": university,
                                        "groupNumber": groupNumber,
                                        "groupCreationDate": groupCreationDate
                                    }
                                }
                            },
                            () => {
                                database.collection("users").findOne({
                                    "accessToken": accessToken
                                }, function (error, user) {
                                    result.json({
                                        "status": "success",
                                        "message": "Data have been changed",
                                        "education": user.education
                                    });
                                });
                            }
                        )
                    }
                }
            );
        }
    );
}