var homeRoute = require("./routes/homeRoute")
var signupRoute = require("./routes/signupRoute")
var express = require("express")
var app = express()

var formidable = require("express-formidable")
app.use(formidable())

var mongodb = require("mongodb")
var mongoClient = mongodb.MongoClient
var objectId = mongodb.ObjectID

var http = require("http").createServer(app)
var bcrypt = require("bcrypt")
var fileSystem = require("fs")

var jwt = require("jsonwebtoken")
var accessTokenSecret = "MyAccessTokenSecret1234567890"

app.use("/public", express.static(__dirname + "/public"))
app.set("view engine", "ejs")

var socketIO = require("socket.io")(http)
var socketID = ""
var users = []

var mainURL = "http://localhost:3000"

socketIO.on("connection", function (socket) {
    console.log("user connected", socket.id)
    socketID = socket.id
})

http.listen(3000, function () {
    console.log("server started")
    mongoClient.connect("mongodb://localhost:27017", function (error, client) {
        const database = client.db("my_social_network");
        console.log("database connected")

        homeRoute(app)
        signupRoute(app, database)
    })
})

