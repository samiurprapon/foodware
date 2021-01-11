'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const User = require('./users');

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
    trade: {
        type: Datatypes.STRING,
        allowNull: false
    },
    TIN: {
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
    }

});

User.hasMany(Vendor);
Vendor.belongsTo(User);

module.exports = Vendor;
