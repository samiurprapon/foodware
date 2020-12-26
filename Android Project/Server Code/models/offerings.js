'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Restaurant = require('./restaurants');
const Item = require('./items');

const Offering = Sequelize.define('offerings', {
    date: {
        type: Datatypes.DATEONLY,
        allowNull: false
    }    
}, { 
    timestamps: false 
});

Item.hasMany(Offering);
Restaurant.hasMany(Offering);


module.exports = Offering;