const auth = require('../middleware/auth');
const express = require('express');
const router = express.Router();
const models = require('../config/database');

//models.sequelize.sync();

const sequelize = models.sequelize;
const Message = sequelize.import('../models/message');

router.get('/', auth, function (req, res) {
    const message = Message.findAll().then((m)=>{res.send(m).json;});
});

router.get('/:id', auth, function (req, res) {
     const message = Message.findById(req.params.id).then((m)=>{res.send(m).json;});
 });

router.get('/conversation/:id', auth, function (req, res) {
    const message = Message.findAll({ where: { convers: req.params.id } }).then((m)=>{res.send(m).json;});

});

router.delete('/:id', auth, function (req, res) {
    const message = Message.findById(req.params.id).then((task)=>{return task.destroy()}).then(()=>{res.status(200).send();})

    
});

router.post('/', auth, (req,res)=>{
    var time=Date.now();
    const message = Message.create(
        {   'text':req.body.text,
            'time':time, 
            'sender':req.body.sender,
            'receiver':req.body.receiver, 
            'convers': req.body.convers
        })
    .then((m)=>{res.send(m).json});

});

module.exports = router;