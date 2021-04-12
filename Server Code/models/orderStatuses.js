'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const OrderStatus = Sequelize.define('orderStatuses', {
    id: {
        type: Datatypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false
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
},{ 
    timestamps: false 
});


module.exports = OrderStatus;