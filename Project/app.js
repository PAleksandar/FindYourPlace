const express = require('express');
const exphbs = require('express-handlebars');
const bodyParser = require('body-parser');
const path = require('path');

const models = require('./config/database'); 
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
app.use("/",userRouter);
app.use("/",messageRouter);
app.use("/",notificationRouter);
app.use("/",commentRouter);

app.listen(PORT,console.log(`Server started on port izmena ${PORT}.`));