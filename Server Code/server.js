// import modules
const express = require('express');
const Sequelize = require('./models/index').Sequelize;
const bodyParser = require('body-parser');
const cors = require('cors');
const multer = require("multer");

// file uploading api
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'public/uploads');
    },
    filename: (req, file, cb) => {
        cb(null, file.originalname);
    }
});

//config
const config = require('./config/secrets.json');

// routes
const authRoutes = require('./routes/authRoutes');
const restaurantRoutes = require('./routes/restaurantRoutes');
const itemRoutes = require('./routes/itemRoutes');
const offeringRoutes = require('./routes/offeringRoutes');
const orderRoutes = require('./routes/orderRoutes');

const server = express();
const port = process.env.PORT || config.PORT;

// file uploading type and storage location
server.use('/public/uploads', express.static('public/uploads'));
server.use(multer({ storage: storage }).single('photo'));

server.use(bodyParser.urlencoded({extended: true}));
server.use(bodyParser.json());
server.use(cors());

// routing
server.use('/api/auth', authRoutes.router);

server.use('/api/restaurant', restaurantRoutes.router);
server.use('/api/restaurant/item', itemRoutes.router);
server.use('/api/restaurant/offering', offeringRoutes.router);
server.use('/api/order', orderRoutes.router);

Sequelize.sync({
    force: false
})
.then( () => {
    server.listen(port, () => {
        console.log('Server started on :'+port);
    });
})
.catch(error => {
    console.log(error);
});