'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Customer = require('./customers');
const Restaurant = require('./restaurants');
const Rider = require('./riders');


const Order = Sequelize.define('oders', {
    id: {
        type: Datatypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false
    }, 
    deliveryCharge: {
        type: Datatypes.INTEGER,
        defaultValue: 30
    },  
    isAccepted: {
        type: Datatypes.BOOLEAN, 
        defaultValue: false
    },
    acceptedAt: {
        type: Datatypes.TIME, 
        allowNull: true
    },
    isPicked: {
        type: Datatypes.BOOLEAN, 
        defaultValue: false
    },
    pickedAt: {
        type: Datatypes.TIME, 
        allowNull: true
    },
    isDelivered: {
        type: Datatypes.BOOLEAN, 
        defaultValue: false
    },
    deliveredAt: {
        type: Datatypes.TIME, 
        allowNull: true
    },
});

Order.belongsTo(Restaurant);
Order.belongsTo(Customer);
Order.belongsTo(Rider);


module.exports = Order;