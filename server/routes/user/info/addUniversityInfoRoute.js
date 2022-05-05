const {ObjectId} = require("mongodb");
module.exports = addUniversityInfo

function addUniversityInfo(app, database) {
    app.put("/addUniversityInfo", function (request, result) {
            const accessToken = request.headers.access;
            const university = request.fields.university;
            const groupNumber = request.fields.groupNumber;
            const groupCreationDate = request.fields.groupCreationDate;

            database.collection("Users").findOne({
                    "accessToken": accessToken
                }, function (error, user) {
                    if (user == null) {
                        result.json({
                            "message": "User has been logged out"
                        });
                    } else {
                        database.collection("Users").updateOne({
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
                                database.collection("Users").findOne({
                                    "accessToken": accessToken
                                }, function (error, user) {
                                    result.json({
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