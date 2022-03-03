module.exports = homeRoute

function homeRoute(app) {
    app.get("/", function (request, result) {
        result.send("hell")
    })
}