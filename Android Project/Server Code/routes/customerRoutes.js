const router = require('express').Router();

const customerController = require('../controllers/customerController');
const authMiddleware = require('../controllers/authMiddleware');

// vendor access
router.post('/create', authMiddleware.validation, customerController.create);
router.post('/update', authMiddleware.validation, customerController.update);

module.exports = {
    router
};