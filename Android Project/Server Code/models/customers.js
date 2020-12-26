'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Customers = Sequelize.define('customers', {
    id: {
        type: Datatypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false
    }, 
    name: {
        type: Datatypes.STRING,
        allowNull: false
    }, 
    phone: {
        type: Datatypes.STRING,
        allowNull: false
    }, 
    location: {
        type: Datatypes.STRING, 
        allowNull: false
    },
    photo: {
        type: Datatypes.STRING,
        allowNull: true
    },

});

module.exports = Customers;
