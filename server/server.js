const homeRoute = require("./routes/homeRoute")
const signupRoute = require("./routes/user/auth/signupRoute")
const signinRoute = require("./routes/user/auth/signinRoute")
const allUsersRoute = require("./routes/common/allUsersRoute")
const profileInfoRoute = require("./routes/common/profileInfoRoute")
const getChatsInfo = require("./routes/conversation/getChatsInfo")
const sendMessage = require("./routes/conversation/sendMessage")
const removeMessage = require("./routes/conversation/removeMessage")
const getChatMessages = require("./routes/conversation/getChatMessages")
const sendPrivateMessage = require("./routes/conversation/sendPrivateMessage")
const getAllFriends = require("./routes/user/cooperation/getAllFriends")
const getAllSubscribers = require("./routes/user/cooperation/getAllSubscribers")
const getAllSubscriptions = require("./routes/user/cooperation/getAllSubscriptions")
const sendFriendRequest = require("./routes/user/cooperation/sendFriendRequest")
const removeFriend = require("./routes/user/cooperation/removeFriend")
const removeSubscription = require("./routes/user/cooperation/removeSubscription")
const rejectFriendRequest = require("./routes/user/cooperation/rejectFriendRequest")
const approveFriendRequest = require("./routes/user/cooperation/approveFriendRequest")
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
        getChatsInfo(app, database)
        sendMessage(app, database)
        removeMessage(app, database)
        getAllFriends(app, database)
        getAllSubscriptions(app, database)
        getAllSubscribers(app, database)
        getChatMessages(app, database)
        sendPrivateMessage(app, database)
        sendFriendRequest(app, database)
        removeFriend(app, database)
        removeSubscription(app, database)
        rejectFriendRequest(app, database)
        approveFriendRequest(app, database)
        updateFullNameRoute(app, database)
        addUniversityInfoRoute(app, database)
        deleteUniversityInfoRoute(app, database)
    })
})

