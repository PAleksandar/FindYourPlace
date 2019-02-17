const auth = require('../middleware/auth');
const express = require('express');
const router = express.Router();
const models = require('../config/database');

const sequelize = models.sequelize;
const Place = sequelize.import('../models/place');

router.get('/', (req,res)=>{ 
    const place = Place.findAll().then((p)=>{res.status(200).send(p).json});
});

router.post('/', (req,res) => {
    const place = Place.create({
        'name': req.body.name,
        'tag':req.body.tag,
        'latitude':req.body.latitude,
        'longitude':req.body.longitude,
        'image':req.body.image,
        'description':req.body.description,
        'like':req.body.like
    }).then((p)=>{res.status(200).send(p).json});

});

router.get('/:id', (req,res)=> {
    const place = Place.findById(req.params.id).then((p)=>{res.status(200).send(p).json});

});

router.delete('/:id', (req,res)=>{
    const place = Place.findById(req.params.id).then((task)=>{return task.destroy()}).then(()=>{res.status(200).send();})

});

router.put('/:id', (req,res)=>{
    const mesto = Place.findById(req.params.id).then((place)=>{
    console.log(place.name);
    console.log(req.body.name);
    place.name= req.body.name;
    place.tag=req.body.tag;
    place.latitude=req.body.latitude,
    place.longitude= req.body.longitude,
    place.image=req.body.image;
    place.description=req.body.description;
    place.like=req.body.like;
    place.save().then((p)=>{res.status(200).send(p).json;})

    });
    

});
module.exports=router;