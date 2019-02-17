const auth = require('../middleware/auth');
const express = require('express');
const router = express.Router();
const models = require('../config/database');
//models.sequelize.sync();
const sequelize = models.sequelize;
const Conversation = sequelize.import('../models/conversation');

router.get('/', auth, function (req, res) {
    const conversation = Conversation.findAll().then((m)=>{res.send(m).json;});
});

router.get('/:id', auth, function (req, res) {
     const conversation = Conversation.findById(req.params.id).then((m)=>{res.send(m).json;});
 });



router.get('/user/:id', function (req, res) {
    const conversation = Conversation.findAll({
        where:{
            $or: [ { user1: req.params.id}, { user2: req.params.id} ]
        }
    }).then((m)=>{res.send(m).json;});
});

router.get('/user/:id1/:id2', function (req, res) {
    const conversation = Conversation.findOne({
        where:{
            $or: [ { user1: req.params.id1, user2: req.params.id2}, { user1: req.params.id2, user2: req.params.id1} ]
        }
    }).then((m)=>{res.send(m).json;});
});


router.delete('/:id', auth, function (req, res) {
    
    const conversation = Conversation.findById(req.params.id).then((task)=>{return task.destroy()}).then(()=>{res.status(200).send();})
        
});

router.post('/', (req,res)=>{
    var time=Date.now();
    const conversation = Conversation.create(
        {   'user1':req.body.user1,
            'user2':req.body.user2
        })
    .then((m)=>{res.send(m).json});

});


module.exports = router;