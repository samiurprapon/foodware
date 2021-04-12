'use strict';

const Sequelize = require('./index').Sequelize;
const Datatypes = require('./index').DataTypes;

const Transaction = Sequelize.define('transactions', {
    id: {
        type: Datatypes.INTEGER,
        autoIncrement: true,
        allowNull: false,
        primaryKey: true
    },
    trxID: {
        type: Datatypes.STRING,
        allowNull: true,
        unique: true
    },
    transactionType: {
        type: Datatypes.STRING,
        defaultValue: 'Bkash',
        allowNull: true
    },
    amount: {
        type: Datatypes.STRING,
        allowNull: false
    },
    currency: {
        type: Datatypes.STRING,
        allowNull: false
    },
    msisdn: {
        type: Datatypes.STRING,
        allowNull: true
    },
    transactionReference: {
        type: Datatypes.STRING,
        allowNull: true
    },
    transactionStatus: {
        type: Datatypes.STRING,
        allowNull: true
    }, 
    isEquipped: {
        type: Datatypes.BOOLEAN,
        defaultValue: false
    }
}, { 
    timestamps: false 
});

module.exports = Transaction;