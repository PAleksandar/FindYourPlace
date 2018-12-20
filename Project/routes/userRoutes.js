const express = require('express');
const router = express.Router();
const models = require('../config/database');
const sequelize = models.sequelize;
const User = sequelize.import('../models/user');

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

module.exports = router;