const Restaurant = require('../models/restaurants');

const create = (req, res) => {
    let user = res.locals.user ;

    Restaurant.upsert({
        name: req.body.name,
        email: user.email,
        ownerName: req.body.ownerName,
        location: req.body.location,
        phone: req.body.phone, 
        bkash: req.body.bkash, 
        status: req.body.status,
        openingAt: req.body.opening,
        closingAt: req.body.closing, 
    })
    .then(restaurant => {
        res.status(201);
        res.send({
            'message': 'Created restaurant!', 
            'restaurant' : restaurant
        });
    })
    .catch(err => {
        res.status(403);
        res.send({
            'message': 'failed'
        });
    });

};

const logoChange = (req, res) => {
    let user = res.locals.user ;

    Restaurant.upsert({
        logo: 'https://265e64f7b8ba.ngrok.io/'+req.file.path
    }, {
        where: {
            email: user.email
        }
    }).then(restaurant => {
        res.status(201);
        res.send({
            'message': 'Image uploaded successfully!'
        })
    }).catch(err => {
        res.status(400);
        res.send({
            'message': 'invaild image uploaded!'
        })
    });
}

const update = (req, res) => {
    let user = res.locals.user ;

    Restaurant.update({
        opening: req.body.opening,
        closing: req.body.closing,
    }, {
        where: {
            email: user.email
        }
    })
    .then(restaurant => {
        res.status(201);
        res.send(restaurant);
    })
    .catch(err => {
        res.status(400);
        res.send({
            'error' : true,
            'message': 'failed to update'
        });
    });
}

const isCompleted = (req, res) => {
    let user = res.locals.user ;

    console.log(user.email);

    Restaurant.findOne({
        where: {
            email: user.email
        }
    }).then(restaurant => {
        if(restaurant!= null) {
            res.status(202);
            res.send({
                'isCompleted': true, 
                'message': 'Profile verification is completed!'
            })
        } else {
            res.status(204);
            res.send({
                'isCompleted': false, 
                'message': 'Profile verification is completed!'
            })
        }
    }).catch(err => {
        res.status(204);
        res.send({
            'isCompleted': false, 
            'message': 'Profile verification is completed!'
        })
    })

}

const changeStatus = (req, res) => {
    let user = res.locals.user ;

    Restaurant.upsert({
        status: req.body.status,
    }, {
        where: {
            email: user.email
        }
    }).then(result => {
        res.status(200);
        res.send({
            'message': 'Restaurant opening status updated!'
        });
    }).catch(err => {
        res.status(204);
        res.send({
            'message': 'Restaurant opening status can not be updated!'
        });
    });
}

module.exports = {
    create, 
    update,
    isCompleted,
    logoChange, 
    changeStatus
}