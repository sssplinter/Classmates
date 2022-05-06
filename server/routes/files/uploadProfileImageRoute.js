const fs = require('fs');
const {ObjectID} = require("mongodb");
module.exports = uploadProfileImage

function uploadProfileImage(app, database) {
    app.post("/uploadProfileImage", function (request, result) {
        const accessToken = request.headers.access;
        const file = request.files.image

        database.collection("Users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            if (user != null) {
                const ext = file.name.substring(file.name.lastIndexOf('.'))
                const path = 'public/' + user._id + ext
                fs.rename(file.path, path, function (err) {});

                database.collection("Users").updateOne({
                        _id: user._id
                    },
                    {$set: {"profileImageUrl": path}}
                );
                result.status(200).json({
                    "message": "success"
                });
            } else {
                result.status(401).json({
                    "message": "unauthorized"
                });
            }
        });
    });
}