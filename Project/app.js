const express = require('express');
const exphbs = require('express-handlebars');
const bodyParser = require('body-parser');
const path = require('path');
const models = require('./config/database');
const placeRouter = require('./routes/placeRoute');
const eventRouter = require('./routes/eventRoute');

// models 
var messageRouter = require('./routes/messageRoutes');
var userRouter = require('./routes/userRoutes');
var notificationRouter = require('./routes/notificationRoutes');
var commentRouter = require('./routes/commentRoutes');

const sequelize = models.sequelize;
//models.sequelize.sync({force: true});
sequelize.sync();

const app =  express();

const Notification = sequelize.import('./models/notification');

const PORT = process.env.PORT || 5000;

app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json());
app.use("/user",userRouter);
app.use("/notification",notificationRouter);
app.use("/comment",commentRouter);
app.use("/message",messageRouter);
app.use("/place", placeRouter);
app.use("/event",eventRouter);

app.listen(PORT,console.log(`Server started on port izmena ${PORT}.`));