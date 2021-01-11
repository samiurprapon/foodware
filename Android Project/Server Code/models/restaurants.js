'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Vendor = require('./vendors');


const Restaurant = Sequelize.define('restaurants', {
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
    logo: {
        type: Datatypes.STRING,
        allowNull: true
    }, 
    // longitude, latitude
    location: {
        type: Datatypes.STRING,
        allowNull: false
    },
    status: {
        type: Datatypes.BOOLEAN,
        defaultValue: true
    },
    opening: {
        type: Datatypes.TIME, 
        allowNull: false
    },
    closing: {
        type: Datatypes.TIME, 
        allowNull: false
    },
    vendorId: {
        type: Datatypes.INTEGER,
        references: {
			model: Vendor,
			key: 'id'
        },
        onDelete: 'CASCADE', 
		onUpdate: 'CASCADE',
    }
});

Vendor.hasMany(Restaurant);
Restaurant.belongsTo(Vendor);

module.exports = Restaurant;