const express = require('express');
const router = express.Router();
const models = require('../config/database');

const sequelize = models.sequelize;
const Event = sequelize.import('../models/event');
const Place = sequelize.import('../models/place');

router.get('/', (req,res)=>{ 
    const event = Event.findAll().then((p)=>{res.status(200).send(p).json});
});
////////////argument id je mesto
router.post('/:id', (req,res) => {
    const place = Place.findById(req.params.id).then((p)=>{
        const event = Event.create({
            'name': req.body.name,
            'tag':req.body.tag,
            'image':req.body.image,
            'description':req.body.description,
            'like':req.body.like,
            'date':req.body.date
        }).then((e)=>{e.setPlace(p).then((p)=>{res.status(200).send(p).json;})});

    });
    

});

router.get('/:id',(req,res)=> {
    const event = Event.findById(req.params.id).then((p)=>{res.status(200).send(p).json});

});

router.put('/:id', (req,res)=>{
    const dogadjaj = Event.findById(req.params.id).then((event)=>{
    event.name= req.body.name;
    event.tag=req.body.tag;
    event.image=req.body.image;
    event.description=req.body.description;
    event.like=req.body.like;
    event.date=req.body.date;
    event.save().then((p)=>{res.status(200).send(p).json;});

    });
    

});


module.exports=router;