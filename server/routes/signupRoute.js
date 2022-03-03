module.exports = signupRoute

const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");

const accessTokenSecret = "myAccessTokenSecret1234567890";
const salt = 10

function signupRoute(app, database) {
    app.post("/signup", function (request, result) {
        const email = request.fields.email;
        const password = request.fields.password;

        database.collection("users").findOne({
            "email": email
        }, function (error, user) {
            if (user == null) {
                bcrypt.hash(password, salt, function (error, hash) {
                    const accessToken = jwt.sign({email: email}, accessTokenSecret);
                    database.collection("users").insertOne({
                        "email": email,
                        "password": hash
                    }, function (error, data) {
                        result.json({
                            "status": "success",
                            "accessToken": accessToken,
                            "message": "Signed up successfully"
                        });
                    });
                });
            } else {
                result.json({
                    "status": "error",
                    "message": "Email or username already exist"
                });
            }
        });
    });
}