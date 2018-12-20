const express = require('express');
const router = express.Router();
const models = require('../config/database');
models.sequelize.sync();
const sequelize = models.sequelize;
const Message = sequelize.import('../models/message');

router.get('/messages', function (req, res) {
    const message = Message.findAll().then((m)=>{res.send(m).json;});
});

router.get('/message/:id', function (req, res) {
     const message = Message.findById(req.params.id).then((m)=>{res.send(m).json;});
 });

router.post('/message',(req,res)=>{
    var time=Date.now();
    const message = Message.create({'text':req.body.text,'time':time})
    .then((m)=>{res.send(m).json});

});

module.exports = router;