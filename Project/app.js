const express = require('express');
const exphbs = require('express-handlebars');
const bodyParser = require('body-parser');
const path = require('path');
const models = require('./config/database.js');
const placeRouter = require('./routes/placeRoute');
const eventRouter = require('./routes/eventRoute');

// models 
//const models = require('./config/database'); 
var messageRouter = require('./routes/messageRoutes');
var conversationRouter = require('./routes/conversationRoutes');

//models.sequelize.sync({force: true});
models.sequelize.sync();

const app =  express();

//const sequelize = models.sequelize;

//const Notification = sequelize.import('./models/notification');

//const Comment = sequelize.import('./models/comment');

//console.log(Notification.get('text'));

const PORT = process.env.PORT || 5000;

app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json());
app.use("/message",messageRouter);
app.use("/conversation",conversationRouter);
app.use("/place", placeRouter);
app.use("/event",eventRouter);

app.listen(PORT,console.log(`Server started on port izmena ${PORT}.`));