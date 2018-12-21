const express = require('express');
const router = express.Router();
const models = require('../config/database');
const sequelize = models.sequelize;
const notification = sequelize.import('../models/notification');

router.get('/', ( req, res) => {
  const notif = notification.findAll().then((n) => {res.send(n).json;});
});

router.get('/:id', ( req, res) => {
  const notif = notification.findById(req.params.id).then((n)=>{res.send(n).json});
});

router.put('/:id', ( req, res) => {
  notification.update({
    'text': req.body.text,
    'date': new Date(req.body.date),
    'userId': req.body.userId,
    'eventId':req.body.eventId
  }, { where: { id: req.params.id } })
  .then((n) => { res.send(n).json; });
});

router.post('/', ( req, res) => {
  const notif = notification.create({
    'text': req.body.text, 
    'date': new Date(req.body.date),
    'userId': req.body.userId,
    'eventId':req.body.eventId
  }).then((n) => { res.send(n).json; });
});

router.delete('/:id', ( req, res) => {
  const notif = notification.findById(req.params.id).then((n)=>{res.send(n).json});
  notification.destroy({ where: {id: req.params.id} });
});

module.exports = router;