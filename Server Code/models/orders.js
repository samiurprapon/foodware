'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Coupon = require('./coupons');
const Customer = require('./customers');
const Item = require('./items');
const OrderItem = require('./orderItems');
const OrderStatus = require('./orderStatuses');
const Restaurant = require('./restaurants');
const Rider = require('./riders');

const Transaction = require('./transactions');


const Order = Sequelize.define('oders', {
    id: {
        type: Datatypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false
    },
    amount: {
        type: Datatypes.DOUBLE,
        allowNull: false
    },
    processingCost: {
        type: Datatypes.DOUBLE,
        allowNull: false
    },
    deliveryCharge: {
        type: Datatypes.INTEGER,
        defaultValue: 30
    }, 
    date: {
        type: Datatypes.TIME, 
        defaultValue: new Date().toISOString().replace(/T/, ' '). replace(/\..+/, '')     
    },
    trxID: {
        type: Datatypes.STRING,
        allowNull:true,
        references: {
            model: Transaction,
            key: 'trxID'
        },
        onUpdate: 'CASCADE',
        onDelete: 'CASCADE'
    }
},{ 
    timestamps: false 
});

Order.belongsTo(Restaurant);
Order.belongsTo(Customer);
Order.belongsTo(Rider);

Order.hasOne(OrderStatus);
OrderStatus.belongsTo(Order);

Item.belongsToMany(Order, { through: OrderItem });
Order.belongsToMany(Item, { through: OrderItem });

Coupon.hasMany(Order, {
    allowNull: true
});

module.exports = Order;