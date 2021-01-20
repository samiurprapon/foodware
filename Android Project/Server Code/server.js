// import modules
const express = require('express');
const Sequelize = require('./models/index').Sequelize;
const bodyParser = require('body-parser');
const cors = require('cors');

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
    force: true
})
.then( () => {
    server.listen(port, () => {
        console.log('Server started on :'+port);
    });
})
.catch(error => {
    console.log(error);
});