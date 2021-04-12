'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Item = require('./items');

const Offering = Sequelize.define('offerings', {
    date: {
        type: Datatypes.DATEONLY,
        allowNull: false
    },
    status: {
        type: Datatypes.BOOLEAN,
        defaultValue: true
    }    
}, { 
    timestamps: false 
});

Item.hasMany(Offering);


module.exports = Offering;