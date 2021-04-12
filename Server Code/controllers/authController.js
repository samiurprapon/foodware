const jwt = require("jsonwebtoken");

const Credential = require('../models/credentials');
const config = require('../config/secrets.json');

const register = (req, res) => {
    let user = {};

    user.email = req.body.email;
    user.password = req.body.password;
    user.type = req.body.type;
    
    Credential.create({ 
        email: req.body.email, 
        password: req.body.password,
        type: req.body.type,
    })
    .then(result => {
        res.status(201);
        res.send({
            'message': 'Signed up successfully!'
        });
    })
    .catch(err => {
        res.status(400);
        res.send({
            "message": "This email already in use!"
        });
    });
};

const login = (req, res) => {
    let details = {};

    Credential.findOne({
        where: {
            email: req.body.email 
        }
    })
    .then(credential => {

        if(!credential) {
            res.status(400);
            res.send({
                'message': 'invalid credentials!', 
            });

        } else if (credential.validPassword(req.body.password)) {
            details.email = credential.dataValues.email;
            details.password = credential.dataValues.password;
            details.type = credential.dataValues.type;

            // generate access token
            // generate refresh token        
            let token = generateTokens(details);

            // update refresh token on `creadentials` table
            credential.update({
                token: token.refreshToken
            }, {
                where: {
                    email: credential.email, 
                    password: credential.password, 
                    type: credential.type
                }
            })
            .then(_result => {
                res.status(200);
                res.send({
                    'message': 'Login successfully!', 
                    'type': credential.type,
                    'accessToken': token.accessToken,
                    'refreshToken': token.refreshToken
                });
            })
            .catch(err => {
                res.status(403);
                res.send({
                    'messasge': 'Server failed!'
                });
            });
           
        } else {
             // wrong passwrord
             res.status(401);
             res.send({
                 'message': 'invalid credentials!', 
             });
        }
        
    })
    .catch(err => {
        res.status(401);
        res.send({
            'message': 'Login failed!', 
        });
    });
};

const refresh = (req, res) => {
    let token = {};

    jwt.verify(res.locals.refreshToken, process.env.REFRESH_TOKEN_SECRET || config.REFRESH_TOKEN_SECRET, (err, user) => {
        if(err) {
        console.log(err);
        return null;
        } 

        delete user['password'];
        delete user['iat'];
        delete user['exp'];

        token.accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET || config.ACCESS_TOKEN_SECRET, {
        expiresIn: "45m",
        });
    });

    if(token) {
        res.status(200);
        res.send({
            'message': 'Token refreshed Successfully!', 
            'accessToken': 'Bearer '+token.accessToken
        });
    } else {
        res.status(403);
        res.send({
            'message': 'Unauthozized access!'
        });
    }

};

const deAuth = (req, res) => {
    let user = res.locals.user ;

    Credential.update({
        token: null
    }, {
        where: {
            email: user.email
        }
    })
    .then(_result => {
        res.status(200);
        res.send({
            'message': 'Logout successfully!'
        });
    })
    .catch(err => {
        res.status(403);
        res.send({
            'message': 'Logout failed!'
        });
    });
}

function generateTokens (user) {
    let token = {};
    
    let refreshToken = jwt.sign(user, process.env.REFRESH_TOKEN_SECRET || config.REFRESH_TOKEN_SECRET, {
      expiresIn: "30d",
    });
  
    delete user['password'];
  
    let accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET || config.ACCESS_TOKEN_SECRET, {
      expiresIn: "45m",
    });
  
  
    token.accessToken = 'Bearer '+accessToken;
    token.refreshToken = 'Bearer ' +refreshToken;
  
    return token;
  }

module.exports = {
    register, 
    login, 
    refresh, 
    deAuth
};