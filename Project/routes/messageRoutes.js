const express = require('express');
const router = express.Router();
const models = require('../config/database');
const sequelize = models.sequelize;
const Message = sequelize.import('../models/message');

router.get('/', function (req, res) {
    const message = Message.findAll().then((m)=>{res.send(m).json;});
});

router.get('/:id', function (req, res) {
     const message = Message.findById(req.params.id).then((m)=>{res.send(m).json;});
 });

router.post('/',(req,res)=>{
    var time=Date.now();
    const message = Message.create({'text':req.body.text,'time':time})
    .then((m)=>{res.send(m).json});

});

module.exports = router;