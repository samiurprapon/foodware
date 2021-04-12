'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Restaurant = Sequelize.define('restaurants', {
    id: {
        type: Datatypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false
    }, 
    email: {
        type: Datatypes.STRING,
        allowNull: false
    },
    name: {
        type: Datatypes.STRING,
        unique: true,
        allowNull: false
    },
    ownerName: {
        type: Datatypes.STRING,
        allowNull: false
    },
    logo: {
        type: Datatypes.STRING,
        allowNull: true
    }, 
    // longitude, latitude
    location: {
        type: Datatypes.STRING,
        allowNull: false
    },
    phone: {
        type: Datatypes.STRING,
        allowNull: false
    },
    bKash: {
        type: Datatypes.STRING,
        allowNull: false
    },
    status: {
        type: Datatypes.BOOLEAN,
        defaultValue: true
    },
    openingAt: {
        type: Datatypes.TIME, 
        allowNull: false
    },
    closingAt: {
        type: Datatypes.TIME, 
        allowNull: false
    }
}, { 
    timestamps: false 
});

module.exports = Restaurant;