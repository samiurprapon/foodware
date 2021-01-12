'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Rider = Sequelize.define('riders', {
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
    avatar: {
        type: Datatypes.STRING,
        allowNull: true
    },

    // 0 = all day
    // 1 = 7 AM - 3 PM (Daty shift)
    // 2 = 3PM - 11 PM (night shift)
    shift: {
        type: Datatypes.INTEGER, 
        defaultValue: 0
    },
    area: {
        type: Datatypes.STRING, 
        allowNull: true
    },
    status: {
        type: Datatypes.BOOLEAN, 
        allowNull: false, 
        defaultValue: false
    }
});

module.exports = Rider;
