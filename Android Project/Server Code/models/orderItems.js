'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Item = require('./items');
const Order = require('./orders');
const Coupon = require('./coupons');

const Orderitem = Sequelize.define('offerings', {
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

Orderitem.hasMany(Order);
Orderitem.hasMany(Item);
Orderitem.hasOne(Coupon);


module.exports = Orderitem;