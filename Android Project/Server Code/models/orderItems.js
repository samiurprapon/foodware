'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;


const Orderitem = Sequelize.define('orderItems', {
    id: {
        type: Datatypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false
    }, 
    quantity: {
        type: Datatypes.INTEGER, 
        allowNull: false
    }, 
    date: {
        type: Datatypes.TIME, 
        defaultValue: new Date().toISOString().replace(/T/, ' '). replace(/\..+/, '')     
    }
       
}, { 
    timestamps: false 
});


module.exports = Orderitem;