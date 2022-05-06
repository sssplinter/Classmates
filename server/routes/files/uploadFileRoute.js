const fs = require('fs');
module.exports = uploadFile

function uploadFile(app, database) {
    app.post("/uploadFile", function (request, result) {
        const accessToken = request.headers.access;
        const file = request.files.file

        fs.rename(file.path, 'public/' + file.name, function (err) {});

        result.status(200).json({
            "message": "success"
        });
    });
}