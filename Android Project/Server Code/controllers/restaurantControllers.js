const Restaurant = require('../models/restaurants');

const create = (req, res) => {
    Restaurant.create({
        name: req.body.name,
        logo: req.body.logo,
        location: req.body.location,
        opening: req.body.opening,
        closing: req.body.closing,
    })
    .then(restaurant => {
        res.status(201);
        res.send(restaurant);
    })
    .catch(err => {
        res.status(403);
        res.send({
            'error' : true,
            'message': 'failed'
        });
    });

};

const update = (req, res) => {
    Restaurant.update({
        logo: req.body.logo,
        opening: req.body.opening,
        closing: req.body.closing,
    }, {
        where: {
            id: req.body.id
        }
    })
    .then(restaurant => {
        res.status(201);
        res.send(restaurant);
    })
    .catch(err => {
        res.status(403);
        res.send({
            'error' : true,
            'message': 'failed'
        });
    });
}

module.exports = {
    create, 
    update
}