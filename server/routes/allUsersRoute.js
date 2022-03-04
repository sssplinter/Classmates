module.exports = allUsersRoute

function allUsersRoute(app, database) {
    app.get("/getAllUsers", function (request, result) {
        database.collection("users").find().toArray(function (error, data) {
            result.json({
                "status": "success",
                "message": "All users",
                "data": data
            });
        });
    });
}