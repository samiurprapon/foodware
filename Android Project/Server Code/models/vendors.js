'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Vendor = Sequelize.define('vendors', {
    id: {
        type: Datatypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false
    }, 
    owner: {
        type: Datatypes.STRING,
        allowNull: false
    }, 
    phone: {
        type: Datatypes.STRING,
        allowNull: false
    }, 
    bkash: {
        type: Datatypes.STRING, 
        allowNull: false
    },
    photo: {
        type: Datatypes.STRING,
        allowNull: true
    },

});

module.exports = Vendor;
