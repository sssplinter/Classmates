module.exports = allUsersRoute

function allUsersRoute(app, database) {
    app.get("/getAllUsers", function (request, result) {
        const accessToken = request.headers.access;
        database.collection("users").findOne({
            "accessToken": accessToken
        }, function (error, user) {
            database.collection("users").find().toArray(function (error, data) {
                const modifiedData = data.map(userData => {
                    let data;
                    if (user == null) {
                        data = {
                            "email": userData.email,
                            "name": userData.name,
                            "surname": userData.surname,
                            "patronim": userData.patronim
                        }
                    } else {
                        data = {
                            "email": userData.email,
                            "name": userData.name,
                            "surname": userData.surname,
                            "patronim": userData.patronim,
                            "education": userData.education
                        }
                    }
                    return data;
                })
                result.json({
                    "status": "success",
                    "message": "All users",
                    "data": modifiedData
                });
            });
        });
    });
}