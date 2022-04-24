module.exports = signupRoute

const {ObjectId} = require("mongodb");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");

const accessTokenSecret = "myAccessTokenSecret1234567890";
const salt = 10

function signupRoute(app, database) {
    app.post("/signup", function (request, result) {
        const email = request.fields.email;
        const password = request.fields.password;

        if (email == null || password == null) {
            result.status(400).json({
                "status": "error",
                "accessToken": "",
                "message": "Wrong params"
            });
        } else {
            database.collection("users").findOne({
                "email": email
            }, function (error, user) {
                if (user == null) {
                    bcrypt.hash(password, salt, function (error, hash) {
                        const accessToken = jwt.sign({email: email}, accessTokenSecret);
                        database.collection("users").insertOne({
                            _id: ObjectId(),
                            "email": email,
                            "password": hash,
                            "accessToken": accessToken,
                            "name": "",
                            "surname": "",
                            "patronim": "",
                            "university": [],
                            "chats": [],
                            "groupChats": [],
                            "friends": [],
                            "subscribers": [],
                            "subscription": []
                        }, function (error, data) {
                            result.status(200).json({
                                "status": "success",
                                "accessToken": accessToken,
                                "message": "Signed up successfully"
                            });
                        });
                    });
                } else {
                    result.status(400).json({
                        "status": "error",
                        "accessToken": "",
                        "message": "Email or username already exist"
                    });
                }
            });
        }
    });
}