module.exports = signinRoute

const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");

const accessTokenSecret = "myAccessTokenSecret1234567890";

function signinRoute(app, database) {
    app.post("/signin", function (request, result) {
        const email = request.fields.email;
        const password = request.fields.password;
        database.collection("users").findOne({
            "email": email
        }, function (error, user) {
            if (user == null) {
                result.json({
                    "status": "error",
                    "message": "Email does not exist"
                });
            } else {
                bcrypt.compare(password, user.password, function (error, isVerify) {
                    if (isVerify) {
                        const accessToken = jwt.sign({email: email}, accessTokenSecret);
                        database.collection("users").findOneAndUpdate({
                            "email": email
                        }, {
                            $set: {
                                "accessToken": accessToken
                            }
                        }, function (error, data) {
                            result.json({
                                "status": "success",
                                "message": "Login successfully",
                                "accessToken": accessToken
                            });
                        });
                    } else {
                        result.json({
                            "status": "error",
                            "message": "Password is not correct"
                        });
                    }
                });
            }
        });
    });
}