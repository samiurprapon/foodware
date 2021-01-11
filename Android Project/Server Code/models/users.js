'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const User = Sequelize.define('users', {
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
    type: {
        type: Datatypes.STRING, 
        allowNull: false
    }

});

module.exports = User;
