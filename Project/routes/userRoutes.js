const express = require('express');
const router = express.Router();
const models = require('../config/database');
const sequelize = models.sequelize;
const User = sequelize.import('../models/user');
const Event = sequelize.import('../models/event');

router.get('/', ( req, res) => {
  const users = User.findAll().then((u) => {res.send(u).json;});
});

router.get('/:id', ( req, res) => {
  const user = User.findById(req.params.id).then((u)=>{res.send(u).json});
});

router.put('/:id', ( req, res) => {
  User.update({
    'email': req.body.email, 
    'password': req.body.password,
    'firstName': req.body.firstName,
    'lastName': req.body.lastName,
    'isActive': req.body.isActive,
    'profileImage': req.body.profileImage,
    'birthday': new Date(req.body.birthday),
  }, { where: { id: req.params.id } })
  .then((u) => { res.send(u).json; });
});

router.post('/', ( req, res) => {
  const user = User.create({
    'email': req.body.email, 
    'password': req.body.password,
    'firstName': req.body.firstName,
    'lastName': req.body.lastName,
    'isActive': req.body.isActive,
    'profileImage': req.body.profileImage,
    'birthday': new Date(req.body.birthday)
  }).then((u) => { res.send(u).json; });
});

router.delete('/:id', ( req, res) => {
  const user = User.findById(req.params.id).then((u)=>{res.send(u).json});
  User.destroy({ where: {id: req.params.id} });
});

//////////////////dodaj korisnika na event

router.post('/:idUser/event/:idEvent',(req,res)=>{
  const user = User.findById(req.params.idUser).then((u)=>{
    const event = Event.findById(req.params.idEvent).then((e)=>{
      e.addUser(u).then(()=>{res.status(200).send(e).json});
      //u.addEvent(e).then(()=>{console.log("Dodat event");});
    });
  });
});
////////////// svi eventi za jednog korisnika
router.get('/:idUser/event', (req,res)=>{
  const user = User.findById(req.params.idUser).then((u)=>{
    u.getEvents().then((events)=>{res.status(200).send(events).json;});

  });
});
module.exports = router;