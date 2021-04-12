'use strict'

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Restaurant = require('./restaurants');

const Item = Sequelize.define('items', {
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
    image: {
        type: Datatypes.STRING,
        allowNull: true
    }, 
    category: {
        type: Datatypes.STRING,
        allowNull: false
    },
    price: {
        type: Datatypes.DOUBLE,
        allowNull: false
    },
    status: {
        type: Datatypes.BOOLEAN, 
        defaultValue: false
    },
    discount: {
        type: Datatypes.DOUBLE,
        defaultValue: 0, 
        allowNull: true
    }
}, { 
    timestamps: false 
});

Restaurant.hasMany(Item);
Item.belongsTo(Restaurant);

module.exports = Item;