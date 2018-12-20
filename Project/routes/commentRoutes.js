const express = require('express');
const router = express.Router();
const models = require('../config/database');
const sequelize = models.sequelize;
const Comment = sequelize.import('../models/comment');

router.get('/', ( req, res) => {
  const comment = Comment.findAll().then((c) => {res.send(c).json;});
});

router.get('/:id', ( req, res) => {
  const comment = Comment.findById(req.params.id).then((c)=>{res.send(c).json});
});

router.put('/:id', ( req, res) => {
  Comment.update({
    'text': req.body.text, 
    'date': new Date(req.body.date),
    'like': parseInt(req.body.like)
  }, { where: { id: req.params.id } })
  .then((c) => { res.send(c).json; });
});

router.post('/', ( req, res) => {
  const comment = Comment.create({
    'text': req.body.text, 
    'date': new Date(req.body.date),
    'like': parseInt(req.body.like)
  }).then((c) => { res.send(c).json; });
});

router.delete('/:id', ( req, res) => {
  const comment = Comment.findById(req.params.id).then((u)=>{res.send(u).json});
  Comment.destroy({ where: {id: req.params.id} });
});

module.exports = router;