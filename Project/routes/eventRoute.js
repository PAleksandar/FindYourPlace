const auth = require('../middleware/auth');
const express = require('express');
const router = express.Router();
const models = require('../config/database');

const sequelize = models.sequelize;
const Event = sequelize.import('../models/event');
const Place = sequelize.import('../models/place');

router.get('/user/:id', auth, (req,res)=>{ 
    const event = Event.findAll({
        where: {
            ownerUserId: req.params.id,
            
          }
    }).then((p)=>{res.status(200).send(p).json});
});

router.get('/', auth, (req,res)=>{ 
    const event = Event.findAll().then((p)=>{res.status(200).send(p).json});
});
////////////id-za place, id2 za Usera
router.post('/:id', auth, (req,res) => {
    const place = Place.findById(req.params.id).then((pl)=>{
        const event = Event.create({
            'name': req.body.name,
            'tag':req.body.tag,
            'image':req.body.image,
            'description':req.body.description,
            'like':req.body.like,
            'date':req.body.date,
            'ownerUserId':req.body.ownerUserId
        }).then((e)=>{e.setPlace(pl).then((p)=>{res.status(200).send(p).json;})});

    });
    

});

router.get('/:id', auth, (req,res)=> {
    const event = Event.findById(req.params.id).then((p)=>{res.status(200).send(p).json});

});

router.put('/:id', auth, (req,res)=>{
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

////////svi korisnici jednog eventa
router.get('/:id/user', auth, (req,res)=>{
    const event= Event.findById(req.params.id).then((event)=>{
        event.getUsers().then((users)=>{res.status(200).send(users).json});

    });
});


router.delete('/:id', auth, function (req, res) {
    
    const event = Event.findById(req.params.id).then((task)=>{return task.destroy()}).then(()=>{res.status(200).send();})
        
});
module.exports=router;