module.exports = signinRoute

const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");

const accessTokenSecret = "myAccessTokenSecret1234567890";

function signinRoute(app, database) {
    app.post("/signin", function (request, result) {
        const email = request.fields.email;
        const password = request.fields.password;
        if (email == null || password == null) {
            result.status(400).json({
                "accessToken": "",
                "isConfirmed": false,
                "message": "Wrong params"
            });
        } else {
            database.collection("Users").findOne({
                "email": email
            }, function (error, user) {
                if (user == null) {
                    result.status(400).json({
                        "accessToken": "",
                        "isConfirmed": false,
                        "message": "Email does not exist"
                    });
                } else {
                    bcrypt.compare(password, user.password, function (error, isVerify) {
                        if (isVerify) {
                            const accessToken = jwt.sign({email: email}, accessTokenSecret);
                            database.collection("Users").findOneAndUpdate({
                                "email": email
                            }, {
                                $set: {
                                    "accessToken": accessToken
                                }
                            }, function (error, data) {
                                result.status(200).json({
                                    "accessToken": accessToken,
                                    "isConfirmed": data.value.name !== "" && data.value.name !== "",
                                    "message": "Login successfully"
                                });
                            });
                        } else {
                            result.status(400).json({
                                "accessToken": "",
                                "isConfirmed": false,
                                "message": "Password is not correct"
                            });
                        }
                    });
                }
            });
        }
    });
}