const homeRoute = require("./routes/homeRoute")
const signupRoute = require("./routes/user/auth/signupRoute")
const signinRoute = require("./routes/user/auth/signinRoute")
const allUsersRoute = require("./routes/common/allUsersRoute")
const profileInfoRoute = require("./routes/common/profileInfoRoute")
const getChat = require("./routes/conversation/getChat")
const sendMessage = require("./routes/conversation/sendMessage")
const getMessages = require("./routes/conversation/getMessages")
const updateFullNameRoute = require("./routes/user/info/updateFullNameRoute")
const addUniversityInfoRoute = require("./routes/user/info/addUniversityInfoRoute")
const deleteUniversityInfoRoute = require("./routes/user/info/deleteUniversityInfoRoute")

const express = require("express")
const formidable = require("express-formidable")
const mongodb = require("mongodb")
const app = express()
const mongoClient = mongodb.MongoClient
const http = require("http").createServer(app)

app.use(formidable())
app.use("/public", express.static(__dirname + "/public"))
app.set("view engine", "ejs")

http.listen(3000, function () {
    console.log("server started")
    mongoClient.connect("mongodb://localhost:27017", function (error, client) {
        const database = client.db("my_social_network");
        console.log("database connected")

        homeRoute(app)
        signupRoute(app, database)
        signinRoute(app, database)
        allUsersRoute(app, database)
        profileInfoRoute(app, database)
        getChat(app, database)
        sendMessage(app, database)
        getMessages(app, database)
        updateFullNameRoute(app, database)
        addUniversityInfoRoute(app, database)
        deleteUniversityInfoRoute(app, database)
    })
})

