'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Coupon = Sequelize.define('coupons', {
    id: {
        type: Datatypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false
    }, 
    code: {
        type: Datatypes.STRING,
        allowNull: false
    },
    rate: {        
        type: Datatypes.DOUBLE,
        allowNull: false
    },
    status: {
        type: Datatypes.BOOLEAN,
        defaultValue: true
    }    
}, { 
    timestamps: false 
});



module.exports = Coupon;