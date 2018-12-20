const express = require('express');
const exphbs = require('express-handlebars');
const bodyParser = require('body-parser');
const path = require('path');

// models 
const models = require('./config/database');

//models.sequelize.sync({force: true});
models.sequelize.sync();

const app =  express();

const sequelize = models.sequelize;

const User = sequelize.import('./models/user');

const Notification = sequelize.import('./models/notification');

const Comment = sequelize.import('./models/comment');

//console.log(Notification.get('text'));

const PORT = process.env.PORT || 5000;

app.listen(PORT,console.log(`Server started on port izmena ${PORT}.`));